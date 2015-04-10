package de.slackspace.tinkerled.behavior;

import java.awt.Color;

import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import de.slackspace.tinkerled.device.EnhancedLedStrip;

public abstract class AbstractLedBehavior {

	protected EnhancedLedStrip ledStrip;
	protected short red;
	protected short green;
	protected short blue;
	
	public AbstractLedBehavior(EnhancedLedStrip ledStrip, String colorHexTriplet, int frameRatePerSecond) {
		this.ledStrip = ledStrip;
		
		Color color = Color.decode(colorHexTriplet);
		red = (short) color.getRed();
		green = (short) color.getGreen();
		blue = (short) color.getBlue();
		
		try {
			ledStrip.setFrameDuration(1000 / frameRatePerSecond);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}

}
