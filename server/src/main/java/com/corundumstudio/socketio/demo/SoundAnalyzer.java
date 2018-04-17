package com.corundumstudio.socketio.demo;

import java.io.File; //File read in
import javax.sound.sampled.AudioInputStream; //Audio in 
import javax.sound.sampled.AudioSystem; //Audio in 
import javax.sound.sampled.UnsupportedAudioFileException; //Audio in 
import java.io.ByteArrayOutputStream; //Byte conversion out

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.io.IOException; 
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