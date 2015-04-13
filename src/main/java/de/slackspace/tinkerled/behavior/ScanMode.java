package de.slackspace.tinkerled.behavior;

import java.util.ArrayList;
import java.util.List;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.tinkerled.device.EnhancedLedStrip;
import de.slackspace.tinkerled.device.Led;

public class ScanMode extends AbstractLedBehavior implements FrameRenderedListener {

	private int numOfFullAnimations = 0;
	
	private boolean increment = false;
	private int currentIndex = 0;
	private int minBoundary = 0;
	private int maxBoundary = 20;
	private int maxSizeOfBrightLeds = 5;
	private int sizeOfBrightLeds = 5;
	
	public ScanMode(EnhancedLedStrip ledStrip, int frameRatePerSecond, int minBoundary, int maxBoundary, int sizeOfBrightLeds) {
		super(ledStrip, frameRatePerSecond);
		
		this.minBoundary = minBoundary;
		this.maxBoundary = maxBoundary;
		this.sizeOfBrightLeds = sizeOfBrightLeds;
		this.maxSizeOfBrightLeds = sizeOfBrightLeds;
		this.currentIndex = maxBoundary;
	}
	
	@Override
	public void frameRendered(int length) {
		List<Led> leds = new ArrayList<>();
		
		// set background
		leds = ledStrip.prepareRangeLeds(minBoundary, maxBoundary - minBoundary, "#380303", leds);
		
		// set bright leds
		leds = ledStrip.prepareRangeInverseLeds(currentIndex, sizeOfBrightLeds, "#FF0000", leds);
		
		// hide leds out of max boundary
		if(currentIndex > maxBoundary) {
			for (Led led : leds) {
				if(led.getIndex() > maxBoundary) {
					led.turnOff();
				}
			}
		}
		
		// send signals to ledstrip
		ledStrip.setLeds(leds);

		if(!increment) {
			// when first led reaches boundary, reduce number of bright leds to get fading effect
			if(currentIndex - sizeOfBrightLeds == minBoundary - 1 && sizeOfBrightLeds != 1) {
				sizeOfBrightLeds--;
			}
		}
		
		// increase brights while moving from max boundary to lower
		if(!increment && currentIndex + sizeOfBrightLeds == maxBoundary + 1 && sizeOfBrightLeds < maxSizeOfBrightLeds) {
			sizeOfBrightLeds++;
		}
		else if(increment && currentIndex - sizeOfBrightLeds == minBoundary - 1 && sizeOfBrightLeds < maxSizeOfBrightLeds) {
			sizeOfBrightLeds++;
		}
		
		// change direction when last led is reaching boundary
		if(currentIndex == maxBoundary + 4) {
			increment = false;
			numOfFullAnimations++;
		}
		if(currentIndex == minBoundary) {
			increment = true;
			sizeOfBrightLeds++;
		}
		
		// increase/decrease current index
		currentIndex = increment ? ++currentIndex : --currentIndex;
	}

	public int getNumOfFullAnimations() {
		return numOfFullAnimations;
	}

	public void setNumOfFullAnimations(int numOfFullAnimations) {
		this.numOfFullAnimations = numOfFullAnimations;
	}
}
