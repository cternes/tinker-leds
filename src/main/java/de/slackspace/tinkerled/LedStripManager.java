package de.slackspace.tinkerled;

import java.io.IOException;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import de.slackspace.tinkerled.behavior.KnightRider;
import de.slackspace.tinkerled.device.EnhancedLedStrip;

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
			ledStrip.setChipType(BrickletLEDStrip.CHIP_TYPE_WS2812);
			return true;
		} catch (AlreadyConnectedException | IOException | TimeoutException | NotConnectedException e) {
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
    
    public void run() {
    	KnightRider listener = new KnightRider(ledStrip, 20, 5, 19, 5);
    	ledStrip.addFrameRenderedListener(listener);
    	listener.frameRendered(0);
    }
    
}
