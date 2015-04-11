package de.slackspace.tinkerled.behavior.spectrum;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.slackspace.tinkerled.device.Led;

public class BeatLevelHandler {

	private static int VARIABLE_LEDS = 10;
	
	private Random random = new Random();
	private List<Color> colors = new ArrayList<>(); // the available colors
	private Color color = new Color(200, 100, 200); // the current color of the led
	private int startingLed; 
	private int index = 0;
	private boolean isIncrementing; // direction of led enabling
	private int numberOfDetects = 0;
	
	public BeatLevelHandler(int startingLed, List<Color> colors, boolean isIncrementing) {
		this.startingLed = startingLed;
		this.colors = colors;
		this.isIncrementing = isIncrementing;
		
		// set the starting led as index when direction of led enabling is reversed
		if(!isIncrementing) {
			index = startingLed;
		}
	}
	
	public void setLeds(boolean isDetected, List<Led> leds) {
		if(isDetected) {
			numberOfDetects++;
			
			// when detected, light a number of leds
			index = isIncrementing ? startingLed + 10 : startingLed + 10;
			
			// randomly light some more leds
			if(isIncrementing) {
				index += Math.abs(random.nextInt(VARIABLE_LEDS));	
			}
			else {
				index -= Math.abs(random.nextInt(VARIABLE_LEDS));
			}
			
			// change color every 20 detects
			if(numberOfDetects % 20 == 0) {
				color = colors.get(Math.abs(random.nextInt(colors.size())));
			}
		}
		else {
			// when not detected, reduce the number of lighten leds on every frame
			index = isIncrementing ? --index : ++index;
		}
		
		// set the leds
		if(isIncrementing) {
			for (int i = startingLed; i < index; i++) {
				leds.add(new Led(i, color));	
			}
		}
		else {
			for (int i = index; i < startingLed + 10 + VARIABLE_LEDS; i++) {
				leds.add(new Led(i, color));	
			}
		}
	}
	
}
