package de.slackspace.tinkerled.behavior;

import java.util.ArrayList;
import java.util.List;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.tinkerled.device.EnhancedLedStrip;
import de.slackspace.tinkerled.device.Led;

public class PulseMode extends AbstractLedBehavior implements FrameRenderedListener {

	private int numOfFullAnimations = 0;
	
	private boolean increment = true;
	private int minBoundary = 0;
	private int maxBoundary = 150;
	private short color = 1;
	
	public PulseMode(EnhancedLedStrip ledStrip, int frameRatePerSecond, int minBoundary, int maxBoundary) {
		super(ledStrip, frameRatePerSecond);
		
		this.minBoundary = minBoundary;
		this.maxBoundary = maxBoundary;
	}
	
	@Override
	public void frameRendered(int length) {
		ledStrip.turnOff();
		List<Led> leds = new ArrayList<>();
		leds = ledStrip.prepareRangeLeds(minBoundary, maxBoundary - minBoundary, (short)0, color, (short)0, leds);
		ledStrip.setLeds(leds);
		
		calcNextColor();
	}
	
	private void calcNextColor() {
		if(increment) {
			color += 8;
		}
		else {
			color -= 8;
		}
		
		if(color >= 110) {
			increment = false;
		}
		else if (color <= 0) {
			increment = true;
			color = 0;
			numOfFullAnimations++;
		}
	}
	
	public int getNumOfFullAnimations() {
		return numOfFullAnimations;
	}

	public void setNumOfFullAnimations(int numOfFullAnimations) {
		this.numOfFullAnimations = numOfFullAnimations;
	}
	
}
