package org.greyfire.transcendancy.ui;

import javax.swing.JPanel;

public abstract class SecondaryPanel extends JPanel {

	public SecondaryPanel() {
		super(true);
	}

	public SecondaryPanel(boolean doublebuffer) {
		super(doublebuffer);
	}

	public abstract void onOpen();
	public abstract void onClose();
	
}
