package de.slackspace.tinkerled.behavior;

import java.util.ArrayList;
import java.util.List;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import de.slackspace.tinkerled.device.EnhancedLedStrip;
import de.slackspace.tinkerled.device.Led;

public class SingleLedRunner extends AbstractLedBehavior implements FrameRenderedListener {

	private int currentLed = 0;
	private boolean increment = true;

	public SingleLedRunner(EnhancedLedStrip ledStrip, String colorHexTriplet, int frameRatePerSecond) {
		super(ledStrip, colorHexTriplet, frameRatePerSecond);
	}
	
	public void frameRendered(int length) {
		try {
			prepareFrame();
			renderFrame();
			
			// direction control
			currentLed = increment ? ++currentLed : --currentLed;
			
			if(currentLed > ledStrip.getSize()) {
				increment = false;
			}
			
			if(currentLed == 0) {
				increment = true;
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	private void prepareFrame() {
		ledStrip.turnOff();
	}

	private void renderFrame() throws TimeoutException, NotConnectedException {
		// set single led
		List<Led> leds = new ArrayList<>();
		leds.add(new Led(currentLed, red, green, blue));
		ledStrip.setLeds(leds);
	}

}
