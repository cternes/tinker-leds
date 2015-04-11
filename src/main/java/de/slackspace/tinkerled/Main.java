package de.slackspace.tinkerled;
import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {
		LedStripManager mgr = new LedStripManager();
		boolean connected = mgr.connect();
		
		if(connected) {
			mgr.run();
			
			System.out.println("Press key to exit"); System.in.read();
			
			mgr.disconnect();
		}
	}
}
