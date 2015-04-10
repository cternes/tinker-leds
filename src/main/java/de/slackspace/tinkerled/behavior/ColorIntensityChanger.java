package de.slackspace.tinkerled.behavior;

import java.awt.Color;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import de.slackspace.tinkerled.device.EnhancedLedStrip;

public class ColorIntensityChanger extends AbstractLedBehavior implements FrameRenderedListener {

	private boolean increment = true;
	private float changeFactor = 0.30f;
	private Color color;

	public ColorIntensityChanger(EnhancedLedStrip ledStrip, Color color, int frameRatePerSecond) {
		super(ledStrip, "#000000", frameRatePerSecond);
		this.color = color;
	}
	
	public void frameRendered(int length) {
		try {
			renderFrame();
			
			// intensity control
			if(increment) {
				color = brighter();
			}
			else {
				color = darker();
			}

			if(hasReachedHighIntensityThreshold()) {
				increment = false;
			}
			
			if(hasReachedLowIntensityThreshold()) {
				increment = true;
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	private boolean hasReachedLowIntensityThreshold() {
		int redColor = color.getRed();
		int greenColor = color.getGreen();
		int blueColor = color.getBlue();
		int threshold = 4;
		
		if(redColor <= threshold && blueColor <= threshold && greenColor <= threshold) {
			return true;
		}
		
		return false;
	}
	
	private boolean hasReachedHighIntensityThreshold() {
		int redColor = color.getRed();
		int greenColor = color.getGreen();
		int blueColor = color.getBlue();
		int threshold = 110;
		
		if(redColor > threshold || blueColor > threshold || greenColor > threshold) {
			return true;
		}
		
		return false;
	}

	private void renderFrame() throws TimeoutException, NotConnectedException {
		// set all leds
		ledStrip.setAllLeds((short)color.getRed(), (short)color.getGreen(), (short)color.getBlue());
	}

	private Color brighter() {
		int redColor = increaseColorValue(color.getRed());
		int greenColor = increaseColorValue(color.getGreen());
		int blueColor = increaseColorValue(color.getBlue());
		
		return new Color(redColor, greenColor, blueColor);
	}
	
	private int increaseColorValue(int value) {
		value = (int) (value + (value * changeFactor));
		if(value > 255) {
			value = 255;
		}
		return value;
	}
	
	private Color darker() {
		int redColor = decreaseColorValue(color.getRed());
		int greenColor = decreaseColorValue(color.getGreen());
		int blueColor = decreaseColorValue(color.getBlue());
		
		return new Color(redColor, greenColor, blueColor);
	}

	private int decreaseColorValue(int value) {
		value = (int) (value - (value * changeFactor));
		if(value <= 0) {
			value = 0;
		}
		return value;
	}
}
