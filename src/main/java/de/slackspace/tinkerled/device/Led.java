package de.slackspace.tinkerled.device;

import java.awt.Color;

public class Led {

	private int index;
	private short red;
	private short green;
	private short blue;
	
	public Led(int index, String colorHexTriplet) {
		Color color = Color.decode(colorHexTriplet);
		
		this.index = index;
		red = (short) color.getRed();
		green = (short) color.getGreen();
		blue = (short) color.getBlue();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public short getRed() {
		return red;
	}

	public void setRed(short red) {
		this.red = red;
	}

	public short getGreen() {
		return green;
	}

	public void setGreen(short green) {
		this.green = green;
	}

	public short getBlue() {
		return blue;
	}

	public void setBlue(short blue) {
		this.blue = blue;
	}
	
}
