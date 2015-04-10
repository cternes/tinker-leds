package de.slackspace.tinkerled;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;

import de.slackspace.tinkerled.device.EnhancedLedStrip;
import de.slackspace.tinkerled.device.Led;

public class LedStripManager {

	private static final String HOST = "localhost";
    private static final int PORT = 4223;
    private static final String UID = "oVS"; // UID of LED Strip Bricklet
	
    private IPConnection ipcon = new IPConnection();
    private EnhancedLedStrip ledStrip;
    
    public boolean connect() {
        ledStrip = new EnhancedLedStrip(UID, ipcon);
        try {
			ipcon.connect(HOST, PORT);
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (AlreadyConnectedException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    }
    
    public void disconnect() {
    	try {
    		ledStrip.turnOff();
			ipcon.disconnect();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
    }
    
    public void run() throws InterruptedException {
    	//ColorIntensityChanger listener = new ColorIntensityChanger(ledStrip, Color.red, 20);
    	//SingleLedRunner listener = new SingleLedRunner(ledStrip, "#2F1FDE", 20);
    	//ledStrip.addFrameRenderedListener(listener);
    	
    	List<Led> leds = new ArrayList<>();
    	leds.add(new Led(0, "#2F1FDE"));
    	leds.add(new Led(5, "#15104F"));
    	leds.add(new Led(10, "#827CC2"));
    	leds.add(new Led(15, "#605D85"));
    	ledStrip.setLeds(leds);
    }
    
}
