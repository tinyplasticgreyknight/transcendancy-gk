package org.greyfire.transcendancy;

import org.greyfire.transcendancy.ui.*;

public class Transcendancy {
	
	public static void main(String[] args) {
		UI ui = new FrameUI();
		ui.initialise();
		ui.activate();
		while(ui.running()) {
			/* spin! */
		}
		System.exit(0);
	}
}
