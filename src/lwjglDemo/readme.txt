LWJGL version: 2.9.2
In order to avoid unexpected errors, set up natives for lwjgl after importing all libraries.

Eclipse:
Go to your LWJGL folder that contains the folders named "jar", "res", "doc", and "native". You need to go into Eclipse (assuming you use eclipse), open your project in the Project Explorer on the left side of your screen.
Right click on the "JRE System Library" of your project, and click "Build Path" -> "Configure Build Path".
Include the LWJGL native libraries to your project in the Build Path Configurer by clicking the "Native library location" which can be seen in the JRE System Library dropdown menu.
Click on "Edit...", which will be the only button clickable in that general area.
A file explorer will pop up. Navigate to the location of your LWJGL native folder (The location should be something like "C:\Users\YOURUSERNAMEHERE\Desktop\Java\eclipse\lwjgl-2.9.0\native" if you are using Windows) and include the folder named [Your OS here].

IntelliJ:
For IntelliJ users, right click 'lwjgl.jar' under 'External Libraries', click 'Open Library Settings' -> 'Modules'. Select 'lwjgl.jar', click the 'Edit' button on the right pane [it looks like a pencil]. Click the 'Add' button in the new window, and select the package for your OS in the 'native' folder/directory of your 'lwjgl' installation location (e.g. <root>/native/windows/lwjgl.dll).