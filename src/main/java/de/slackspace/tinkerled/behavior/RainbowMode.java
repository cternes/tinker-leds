package de.slackspace.tinkerled.behavior;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.tinkerled.device.EnhancedLedStrip;
import de.slackspace.tinkerled.device.Led;

public class RainbowMode extends AbstractLedBehavior implements FrameRenderedListener {

	private List<Color> colors;

	public RainbowMode(EnhancedLedStrip ledStrip, int frameRatePerSecond) {
		super(ledStrip, frameRatePerSecond);
		
		// 10 colors needed
		colors = new ArrayList<>();
		colors.add(Color.decode("#F7483B")); //red 
		colors.add(Color.decode("#F7A63B")); //orange
		colors.add(Color.decode("#F7F43B")); //yellow
		colors.add(Color.decode("#8DF73B")); //green
		colors.add(Color.decode("#6BFAE9")); //light blue
		colors.add(Color.decode("#6B8FFA")); //blue
		colors.add(Color.decode("#D14EFC")); //purple
		colors.add(Color.decode("#D60D21")); //red
		colors.add(Color.decode("#1665CC")); //dark blue
		colors.add(Color.decode("#248C0A")); //dark green
	}

	@Override
	public void frameRendered(int length) {
		List<Led> leds = new ArrayList<>();
		for (int i = 0; i < ledStrip.getSize(); i += colors.size()) {
			for (int j = 0; j < colors.size(); j++) {
				leds.add(new Led(i + j, colors.get(j)));
			}
		}
		
		reorderColors();
		ledStrip.setLeds(leds);
	}
	
	private void reorderColors() {
		Color lastElement = colors.get(colors.size() - 1);
		colors.remove(colors.size() - 1);
		colors.add(0, lastElement);
	}

}
