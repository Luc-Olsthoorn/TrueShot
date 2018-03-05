#3D Audio -- A Break Down

So, I've been looking at what we actually need for creating 3D sounds. It looks like we "technically"
don't need anything from OpenAL. 

Lets look at the MatLab file that does some 3D signal processing.

###HRTF.mat Files
```Matlab
    %use an HRTF (HRIR) to make 3D audio
    HRTFToUse = uigetfile(pwd, 'Please select the HRTF database you would like to use');
    load(HRTFToUse);
``` 
Loads a HRTF file into our program:
> CIPIC_58_HRTF.mat 

The file contains a structure like one we would see in `C`, but it is already populated.
In our case this file contains 6 data values:
```
    hrtf_l (25x50x200) double
    hrtf_r (25x50x200) double
    OnR (25x50) double 
    OnL (25x50) double
    ITD (25x50) double
    name string
```

Ok, so this seems fine, we can export this structure containing all of the data into a *.csv* file.
We will parse it and read it into a class.

* Note the first two data values are 3D arrays, this is something we will have to deal with later.

###Azimuth and Elevation
```matlab
    %25 locations
    azimuths = [-80 -65 -55 -45:5:45 55 65 80]
    
    %50 locations
    elevations = -45 + 5.625*(0:49);
    
    aIndex = randi(25, 1, 1);
    eIndex = randi(50, 1, 1); 
    
```

Essentially, these are our 25 azimuths and 50 elevations that we have access to. We can place sounds in any combination
of the two.

We go ahead and choose two random indices.

#####File Reading

```
    FileReader = dsp.AudioFileReader('Life.wav', 'SamplesPerFrame', 5512, ...
        'PlayCount', 1); 
    
    FilePlayer = dsp.AudioPlayer('QueueDuration', 1, 'BufferSizeSource', ...
       'Property', 'BufferSize', 5512, 'SampleRate', FileReader.SampleRate);
```
This is fairly straight forward, we are creating an audio reader for our sound file, which separates it into 
frames of 5512. We will apply our filter to this *slice* of audio which generates the 3D sound.

###Grabbing The Correct HRTF Data
```
    while(~isDone(FileReader))
        wav_left = [];
        wav_right = [];
        soundToPlay = []'
       
        lft = squeeze(hrir_l(aIndex, eIndex, :));
        rgt = squeeze(hrir_r(aIndex, eIndex, :));
        delay = ITD(aIndex, eIndex);
```  

This begins the meat of our program. We will be reading in all frames from the sound file.
First, we create some empty arrays for our convolved data.

Starting from the inside function of the lft statement, we see a function called 
`hrir_l(aIndex,eIndex,:)`.
This is nothing more than a lookup for hrtf_l which was imported from our *CIPIC_58_HRTF.mat* file.
If you remember, the dimension of this value was `25x50x200`. The `25x50` corresponds to our azimuth and elevation.

We go to that row and column and grab all 200 values in the `Zed` direction.

Because we are grabbing the values in the `Zed` direction, we don't want an array that is indexed that way. We
call squeeze() on this data to create an 200 row by *1 column* array.

Finally, we imported the value `ITD` from our file *CIPIC_58_HRTF.mat*. We can retrieve the ITD with respect
to our azimuth and elevation.


###Adding ITD Delay
```
        if(aIndex < 13)
        lft = [lft' zeros(size(1:abs(delay)))];
        rgt = [zeros(size(1:abs(delay))) rgt'];
        else 
        lft = [zeros(size(1:abs(delay))) lft'];
        rgt = [rgt' zeros(size(1:abs(delay)))];
        end
```

If you remeber from earlier, we defined some values for azimuth:
    
    azimuths = [-80 -65 -55 -45:5:45 55 65 80]
With a total of 25 azimuth values, we will have 12 values that are less than 0, and 12 greater.

If the sound is coming from the left *(azimuth value < 0)*, it has to pass from left to right through our head, meaning that
we must add delay on the right channel.

If the sound is coming from the right *(azimuth value > 0)*, it has to pass from right to left through our head, meaning that
we must add delay on the left channel.

The delay value we got from the ITD call gives us the number of zeros that we need to put before our HRTF. 
We can do that by the above statements.

Looking at this: 

        lft = [lft' zeros(size(1:abs(delay)))];
Notice the `'` operator. This is what is called the complex transpose operator. If we are not dealing with complex numbers,
it is the same as the transpose operator.
We take values along an axis and put them in the direction of another axis. 
For example, if we have a two dimensional array:

```java
    normalArray = { [1, 2],
                    [3, 4]
                    }
```

Transposing this array: 
```java
    transposedArray = {[1, 3],
                       [2, 4]
                       }
``` 
    
We see that the row `[1, 2]` becomes a column.

Refer back to where we grabbed our HRTF data in the `Zed` direction, after accessing the data we then called 
squeeze() which changed it from a `Zed` dimension array to a `200 row by 1 column` array.


Calling `'` on our array, changes the array from a `200 row by 1 column` array into a `1 row by 200 column`  - a traditional
linear array.


We can then easily add our delay zeros to the front and back of our HRTFs.

If we add delay to the front of one HRTF we **HAVE** to add the same delay to the back of the opposite HRTF.

###Convolution Time
``` 
        sig = step(FileReader);
        sig = sig(:, 1); 
        
        wav_left = [conv(lft', sig)];
        wav_right = [conv(rgt', sig)];
        
```
Wav file supports stereo audio, which means we have a left and right channel. We read in that first frame 
of audio data. After that, we only want to grab the first column, or channel, which is done with `sig(:, 1)`.

After grabbing our sound data, we now apply the HRTF Filter! We do this by using the mathematical idea of convolution.
**Convolution** is a type of transform that takes two functions f and g and produces 
another function via an integration. In our case, our two functions are the arrays.

Convolution has strong applications in Machine Learning and Image/Signal Processing. In the case of image processing,
we use it to apply "filters", for example: *blurs*, *sharpen*, *gray-scale* to images, pixel by pixel. Consider the 
idea of looking at every pixel in an image. We have some "magic" function that when applied to all pixels, produces 
a new picture.

#####Transposing again
Look and you can see that we transpose our HRTF once again to get the data in the column direction; we do this match our
signal. 

###Load the Columns Into Our Sound Player, Grab the Next Frame
```
        soundToPlay(:,1) = wav_left;
        soundToPlay(:,2) = wav_right;
        
        step(FilePlayer, soundToPlay);
    
    end
```

Our `while` loop ends with the copying of `wav_left` into the left chanel of our player and `wav_right`
into the right channel. We play that frame and get ready for the next frame.
