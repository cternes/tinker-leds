package de.slackspace.tinkerled;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
		SpringApplication.run(Main.class, args);
	}
}
