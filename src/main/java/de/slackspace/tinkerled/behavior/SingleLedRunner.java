package de.slackspace.tinkerled.behavior;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import de.slackspace.tinkerled.device.EnhancedLedStrip;

public class SingleLedRunner extends AbstractLedBehavior implements FrameRenderedListener {

	private int LED_IDX = 0;
	private boolean increment = true;

	public SingleLedRunner(EnhancedLedStrip ledStrip, String colorHexTriplet, int frameRatePerSecond) {
		super(ledStrip, colorHexTriplet, frameRatePerSecond);
	}
	
	public void frameRendered(int length) {
		try {
			prepareFrame();
			renderFrame();
			
			// direction control
			if(increment) {
				LED_IDX++;
			}
			else {
				LED_IDX--;
			}
			
			if(LED_IDX > 150) {
				increment = false;
			}
			if(LED_IDX == 0) {
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
		short[] r = new short[16];
		short[] g = new short[16];
		short[] b = new short[16];

		r[0] = red;
		g[0] = green;
		b[0] = blue;
		
		// set single led
		ledStrip.setRGBValues(LED_IDX, (short)16, b, r, g);	
	}

}
