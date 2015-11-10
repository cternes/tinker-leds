package de.slackspace.tinkerled.web;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tinkerforge.BrickletLEDStrip.FrameRenderedListener;

import de.slackspace.tinkerled.LedStripManager;
import de.slackspace.tinkerled.behavior.KnightRider;
import de.slackspace.tinkerled.behavior.PulseMode;
import de.slackspace.tinkerled.behavior.RainbowMode;

@RestController
@RequestMapping("/ledstrip")
public class LedStripResource {

	private Log logger = LogFactory.getLog(getClass());
	private FrameRenderedListener currentMode;
	private int frames = 0;
	
	@Autowired
	LedStripManager manager;
	
	@PostConstruct
	public void init() {
		boolean connected = manager.connect();
		if(connected) {
			logger.debug("Successfully connected to tinkerforge brick");
			
			startRainbowMode();
//			startKnightRiderMode(5, 19);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value = "/stop")
	public void stop() {
		manager.getLedStrip().turnOff();
		manager.disconnect();
	}
	
	private void startRainbowMode() {
		while(true) {
			if(frames % 100 == 0) {
				if(currentMode instanceof RainbowMode) {
					setMode(new PulseMode(manager.getLedStrip(), 15, 0, 150));
				}
				else {
					setMode(new RainbowMode(manager.getLedStrip(), 2));	
				}
			}
			
			try {
				Thread.sleep(500);
				frames++;
				
				System.out.println(frames);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void startKnightRiderMode(int minBoundary, int maxBoundary) {
		setMode(new KnightRider(manager.getLedStrip(), 20, minBoundary, maxBoundary, 5));
	}
	
	private void setMode(FrameRenderedListener newMode) {
		manager.getLedStrip().removeFrameRenderedListener(currentMode);
		manager.getLedStrip().addFrameRenderedListener(newMode);
		
		currentMode = newMode;
		currentMode.frameRendered(0);
	}
}
