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
import de.slackspace.tinkerled.behavior.RainbowMode;

@RestController
@RequestMapping("/ledstrip")
public class LedStripResource {

	private Log logger = LogFactory.getLog(getClass());
	private FrameRenderedListener currentMode;
	
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
		setMode(new RainbowMode(manager.getLedStrip(), 2));
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
