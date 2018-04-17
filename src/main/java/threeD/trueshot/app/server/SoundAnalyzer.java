package threeD.trueshot.app.server;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class SoundAnalyzer
{
	public void SoundAnalyzer(){

	}
	public byte[] newCoordinates(){
		Path path = Paths.get("src/main/resources/test.wav");
	    byte[] audio = null;
        try{
          audio  = Files.readAllBytes(path);
        }
        catch (Exception e) {
                System.out.println("Audio io error");
                e.printStackTrace(); 

            }
        return audio;
	}
}