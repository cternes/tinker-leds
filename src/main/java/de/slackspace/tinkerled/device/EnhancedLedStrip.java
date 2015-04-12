package de.slackspace.tinkerled.device;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.IPConnection;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class EnhancedLedStrip extends BrickletLEDStrip {

	private int size = 150;
	
	public EnhancedLedStrip(String uid, IPConnection ipcon) {
		super(uid, ipcon);
	}
	
	public EnhancedLedStrip(String uid, IPConnection ipcon, int size) {
		super(uid, ipcon);
		this.size = size;
	}

	public void turnOff() {
		try {
			short[] r = new short[16];
	        short[] g = new short[16];
	        short[] b = new short[16];
	        
	        // turn off all leds
	        for (int i = 0; i < 150; i += 16) {
	        	setRGBValues(i, (short)16, b, r, g);	
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	public void setAllLeds(String colorHexTriplet) {
		Color color = Color.decode(colorHexTriplet);
		short red = (short) color.getRed();
		short green = (short) color.getGreen();
		short blue = (short) color.getBlue();
			
		setAllLeds(red, green, blue);
	}
	
	public void setAllLeds(short red, short green, short blue) {
		try {
			short[] r = new short[16];
	        short[] g = new short[16];
	        short[] b = new short[16];
	        
	        for (int i = 0; i < 16; i++) {
	        	r[i] = red;
	        	g[i] = green;	
	        	b[i] = blue;	
			}
	        
	        // turn off all leds
	        for (int i = 0; i < 150; i += 16) {
	        	setRGBValues(i, (short)16, b, r, g);	
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	public void setSingleLed(int index, String colorHexTriplet) {
		Color color = Color.decode(colorHexTriplet);
		short red = (short) color.getRed();
		short green = (short) color.getGreen();
		short blue = (short) color.getBlue();
			
		setSingleLed(index, red, green, blue);
	}
	
	public void setSingleLed(int index, short red, short green, short blue) {
		try {
			// calculate offset
			int offset = index / 16;
			int pixelIdx = index % 16;
			
			short[] r = new short[16];
	        short[] g = new short[16];
	        short[] b = new short[16];
	        
	        r[pixelIdx] = red;
        	g[pixelIdx] = green;	
        	b[pixelIdx] = blue;
	        
        	setRGBValues(offset * 16, (short)16, b, r, g);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	public void setLeds(List<Led> leds) {
		try {
			HashMap<Integer, LedGroup> ledGroups = new HashMap<>();
	        
			for (Led led : leds) {
				int offset = led.getIndex() / 16;
				int pixelIdx = led.getIndex() % 16;
				
				LedGroup ledGroup = ledGroups.get(offset);
				if(ledGroup == null) {
					ledGroup = new LedGroup();
					ledGroups.put(offset, ledGroup);
				}
				
				ledGroup.getRed()[pixelIdx] = led.getRed();
				ledGroup.getGreen()[pixelIdx] = led.getGreen();	
				ledGroup.getBlue()[pixelIdx] = led.getBlue();
			}
			
			for (Integer groupOffset : ledGroups.keySet()) {
				LedGroup ledGroup = ledGroups.get(groupOffset);
				
				setRGBValues(groupOffset * 16, (short)16, ledGroup.getBlue(), ledGroup.getGreen(), ledGroup.getRed());
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	public int getSize() {
		return size;
	}
}
