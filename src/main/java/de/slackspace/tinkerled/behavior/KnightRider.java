package de.slackspace.tinkerled.behavior;

import java.util.ArrayList;
import java.util.List;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.tinkerled.device.EnhancedLedStrip;
import de.slackspace.tinkerled.device.Led;

public class KnightRider extends AbstractLedBehavior implements FrameRenderedListener {

	private boolean increment = false;
	private boolean setPushMode = false;
	private int currentIndex = 0;
	
	private int minBoundary = 0;
	private int maxBoundary = 20;
	private int maxSizeOfBrightLeds = 5;
	private int sizeOfBrightLeds = 5;
	
	public KnightRider(EnhancedLedStrip ledStrip, int frameRatePerSecond, int minBoundary, int maxBoundary, int sizeOfBrightLeds) {
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
		
		// background
		leds = ledStrip.prepareRangeLeds(minBoundary, maxBoundary - minBoundary, "#380303", leds);
		
		// brights
		if(setPushMode) {
			leds = ledStrip.prepareRangeLeds(currentIndex, sizeOfBrightLeds, "#FF0000", leds);
		}
		else {
			leds = ledStrip.prepareRangeInverseLeds(currentIndex, sizeOfBrightLeds, "#FF0000", leds);
		}
		
		// send signals to ledstrip
		ledStrip.setLeds(leds);

		if(increment) {
			// when first led reaches boundary, reduce number of bright leds to get fading effect
			if(currentIndex + sizeOfBrightLeds == maxBoundary && sizeOfBrightLeds != 0) {
				sizeOfBrightLeds--;
			}
		}
		else {
			// when first led reaches boundary, reduce number of bright leds to get fading effect
			if(currentIndex - sizeOfBrightLeds == minBoundary && sizeOfBrightLeds != 0) {
				sizeOfBrightLeds--;
			}
		}
		
		// increase brights while moving from max boundary to lower
		if(!increment && currentIndex + sizeOfBrightLeds == maxBoundary && sizeOfBrightLeds < maxSizeOfBrightLeds) {
			sizeOfBrightLeds++;
		}
		else if(increment && currentIndex - sizeOfBrightLeds == minBoundary && sizeOfBrightLeds < maxSizeOfBrightLeds) {
			sizeOfBrightLeds++;
		}
		
		
		// increase/decrease current index
		currentIndex = increment ? ++currentIndex : --currentIndex;
		
//		if(currentIndex + sizeOfBrightLeds == maxBoundary && sizeOfBrightLeds <= maxSizeOfBrightLeds) {
//			sizeOfBrightLeds++;
//		}
		
//		if(increment) {
//			if(currentIndex - sizeOfBrightLeds == minBoundary && sizeOfBrightLeds <= maxSizeOfBrightLeds) {
//				sizeOfBrightLeds++;
//			}	
//		}
		
		// change direction when last led is reaching boundary
		if(currentIndex == maxBoundary) {
			increment = false;
		}
		if(currentIndex == minBoundary) {
			increment = true;
		}
	}
	
	
	// increase/decrease current index
//			currentIndex = increment ? ++currentIndex : --currentIndex;
//			
			// send signals to ledstrip
//			ledStrip.setLeds(leds);
	//
//			if(increment) {
//				// when first led reaches boundary, reduce number of bright leds to get fading effect
//				if(currentIndex == maxBoundary - sizeOfBrightLeds && sizeOfBrightLeds != 0) {
//					sizeOfBrightLeds--;
//				}
//			}
//			else {
//				// when last led reaches boundary, reduce number of bright leds to get fading effect
//				if(currentIndex < minBoundary && sizeOfBrightLeds != 0) {
//					sizeOfBrightLeds--;
//				}
//			}
//			
//			if(currentIndex + sizeOfBrightLeds == maxBoundary && sizeOfBrightLeds <= maxSizeOfBrightLeds) {
//				sizeOfBrightLeds++;
//			}
//			
//			// change direction when last led is reaching boundary
//			if(currentIndex == maxBoundary) {
//				increment = false;
//			}
//			if(currentIndex == minBoundary - maxSizeOfBrightLeds) {
//				increment = true;
//			}

}
