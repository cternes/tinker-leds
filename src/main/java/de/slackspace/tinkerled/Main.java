package de.slackspace.tinkerled;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;


public class Main {

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
		LedStripManager mgr = new LedStripManager();
		boolean connected = mgr.connect();
		
		if(connected) {
			mgr.run();
			
			System.out.println("Press key to exit"); System.in.read();
			
			mgr.disconnect();
		}
	}
}
