package de.slackspace.tinkerled;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	private Log logger = LogFactory.getLog(getClass());
	
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
		SpringApplication.run(Main.class, args);
	}
	
	public Main() {
		LedStripManager mgr = new LedStripManager();
		boolean connected = mgr.connect();
		
		if(connected) {
			logger.debug("Successfully connected to tinkerforge brick");
			
			mgr.run();
		}
	}
}
