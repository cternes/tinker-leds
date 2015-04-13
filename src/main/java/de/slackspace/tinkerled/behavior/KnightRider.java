package de.slackspace.tinkerled.behavior;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.tinkerled.device.EnhancedLedStrip;

public class KnightRider extends AbstractLedBehavior implements FrameRenderedListener {

	private int mode = 1;
	
	private PulseMode pulseMode;
	private ScanMode scanMode;
	
	public KnightRider(EnhancedLedStrip ledStrip, int frameRatePerSecond, int minBoundary, int maxBoundary, int sizeOfBrightLeds) {
		super(ledStrip, frameRatePerSecond);
		
		pulseMode = new PulseMode(ledStrip, frameRatePerSecond, minBoundary, maxBoundary);
		scanMode = new ScanMode(ledStrip, frameRatePerSecond, minBoundary, maxBoundary, sizeOfBrightLeds);
	}

	@Override
	public void frameRendered(int length) {
		switch(mode) {
			case 1: scanMode.frameRendered(length);
					if(scanMode.getNumOfFullAnimations() == 3) {
						scanMode.setNumOfFullAnimations(0);
						mode++;
					}
					break;
			case 2: pulseMode.frameRendered(length);
					if(pulseMode.getNumOfFullAnimations() == 3) {
						pulseMode.setNumOfFullAnimations(0);
						mode++;
					}
					break;
			case 3: mode = 1; 
					break;
		}
		
	}

}
