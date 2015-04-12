package de.slackspace.tinkerled.behavior;

import java.util.ArrayList;
import java.util.List;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.tinkerled.device.EnhancedLedStrip;
import de.slackspace.tinkerled.device.Led;

public class KnightRider extends AbstractLedBehavior implements FrameRenderedListener {

	private boolean increment = true;
	private int currentIndex = 0;
	
	private int minBoundary = 0;
	private int maxBoundary = 20;
	private int sizeOfBrightLeds = 5;
	
	public KnightRider(EnhancedLedStrip ledStrip, int frameRatePerSecond, int minBoundary, int maxBoundary, int sizeOfBrightLeds) {
		super(ledStrip, frameRatePerSecond);
		
		this.minBoundary = minBoundary;
		this.maxBoundary = maxBoundary;
		this.sizeOfBrightLeds = sizeOfBrightLeds;
		this.currentIndex = minBoundary;
	}

	@Override
	public void frameRendered(int length) {
		List<Led> leds = new ArrayList<>();
		
		// background
		leds = ledStrip.prepareRangeLeds(minBoundary, maxBoundary - minBoundary, "#380303", leds);
		
		// bright leds
		leds = ledStrip.prepareRangeLeds(currentIndex, sizeOfBrightLeds, "#FF0000", leds);
		
		// increase/decrease current index
		currentIndex = increment ? ++currentIndex : --currentIndex;
		
		// send signals to ledstrip
		ledStrip.setLeds(leds);
		
		// change direction when reaching boundaries
		if(currentIndex == maxBoundary - sizeOfBrightLeds) {
			increment = false;
		}
		if(currentIndex == minBoundary) {
			increment = true;
		}
	}

}
