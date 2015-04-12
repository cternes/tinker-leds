package de.slackspace.tinkerled.behavior;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ddf.minim.analysis.BeatDetect;
import de.slackspace.tinkerled.behavior.spectrum.BeatLevelHandler;
import de.slackspace.tinkerled.device.EnhancedLedStrip;
import de.slackspace.tinkerled.device.Led;

public class SpectrumAnalyzer extends AbstractLedBehavior implements FrameRenderedListener {

	private BeatDetect beatDetector;
	private AudioPlayer audioPlayer;
	
	private List<Color> colors = new ArrayList<>();
	private BeatLevelHandler hatLevelHandler;
	private BeatLevelHandler kickLevelHandler;
	private BeatLevelHandler snareLevelHandler;

	public SpectrumAnalyzer(EnhancedLedStrip ledStrip, String colorHexTriplet, int frameRatePerSecond, String audioFile) {
		super(ledStrip, colorHexTriplet, frameRatePerSecond);
		
		colors.add(Color.decode("#B91BF2"));
		colors.add(Color.decode("#D921C0"));
		colors.add(Color.decode("#D92158"));
		colors.add(Color.decode("#F20707"));
		colors.add(Color.decode("#F26507"));
		colors.add(Color.decode("#BF5F1F"));
		colors.add(Color.decode("#E0C424"));
		colors.add(Color.decode("#D7E024"));
		
		hatLevelHandler = new BeatLevelHandler(0, colors, true);
		kickLevelHandler = new BeatLevelHandler(51, colors, true);
		snareLevelHandler = new BeatLevelHandler(29, colors, false);
		
		Minim minim = new Minim(this);
		audioPlayer = minim.loadFile(audioFile);
		beatDetector = new BeatDetect(audioPlayer.bufferSize(), audioPlayer.sampleRate());
		beatDetector.setSensitivity(150);
		
		audioPlayer.play();
	}

	@Override
	public void frameRendered(int length) {
		beatDetector.detect(audioPlayer.mix);
		
		ledStrip.turnOff();
		
		List<Led> leds = new ArrayList<>();
		hatLevelHandler.setLeds(beatDetector.isHat(), leds);
		snareLevelHandler.setLeds(beatDetector.isSnare(), leds);
		kickLevelHandler.setLeds(beatDetector.isKick(), leds);
		
		ledStrip.setLeds(leds);
	}
	
	public InputStream createInput(String fileName) {
		try {
			return new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String sketchPath(String fileName) {
		return fileName;
	}
}
