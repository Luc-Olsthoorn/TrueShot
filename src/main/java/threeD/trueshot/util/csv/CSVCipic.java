package threeD.trueshot.util.csv;

import org.nd4j.linalg.api.buffer.DataBuffer;
import org.nd4j.linalg.api.buffer.DoubleBuffer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;

import java.io.*;

/**
 * Parses CIPIC CSV Files.
 */
public class CSVCipic
{
    private String subjectName;
    private BufferedReader bufferedReader;
    private String workingDirectory;

    public CSVCipic(String name)
    {
        subjectName = name;
        workingDirectory = System.getProperty("user.dir");
    }

    /**
     * Reads the hrir_l values from a 58_hrir_l.csv and stores the in a Rank 3 INDArray
     * @return INDArray Rank 3 filled with hrir_l data
     */
    public INDArray readHrir_l()
    {
        String fileName = workingDirectory + "/res/hrtfs/cipic/subject_" + subjectName + "/" + subjectName + "_hrir_l.csv";
        try
        {
            bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        Nd4j.setDataType(DataBuffer.Type.DOUBLE);
        INDArray hrir_l = Nd4j.create(25, 50, 200);
        INDArray tempRow = Nd4j.create(50);
        DoubleBuffer buffer = new DoubleBuffer(50);
        String line = "";

        int row = 0, column = 0, channel = 0;
        try
        {
            while((line = bufferedReader.readLine()) != null)
            {
                String[] dataPoints = line.split(",");
                for (String hrtf_data: dataPoints)
                {
                    // We done all channels for a given row
                    if(channel == 200)
                    {
                        if(++row == 25)
                        {
                           break;
                        }
                        channel = 0;
                    }

                    // We have finished the last column for a given row in a given channel
                    if (column == 49)
                    {
                        // Assign the new row, move to next channel, reset the column
                        buffer.put(column, Double.valueOf(hrtf_data));
                        tempRow = Nd4j.create(buffer);

                        // Grab the data at this row for all the columns on that given channel
                        // Assign that our new read in data.
                        hrir_l.get(NDArrayIndex.point(row), NDArrayIndex.all(), NDArrayIndex.point(channel)).assign(tempRow);

                        // Clear buffer
                        buffer.flush();

                        column = 0;
                        channel++;
                    }
                    else
                    {
                        buffer.put(column, Double.valueOf(hrtf_data));
                        column++;
                    }
                }

            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println(hrir_l.shapeInfoToString());
        System.out.println(hrir_l.getDouble(24,49,199));

        try
        {
            bufferedReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return hrir_l;
    }

    public INDArray readHrir_r()
    {

        return null;
    }
    public INDArray readITD()
    {

        return null;
    }
    public INDArray read()
    {

        return null;
    }
}
