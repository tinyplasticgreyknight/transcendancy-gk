package org.greyfire.transcendancy.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;

public class GalaxyPanel extends SecondaryPanel {

	private static final long serialVersionUID = 1L;
	private String content;

	public GalaxyPanel(String content) {
		super();
		this.content = content;
		this.onOpen();
	}

	public GalaxyPanel(String content, boolean doublebuffer) {
		super(doublebuffer);
		this.content = content;
		this.onOpen();
	}

	@Override
	public void onClose() {
		/* donothing */
	}

	@Override
	public void onOpen() {
        JLabel filler = new JLabel(this.content);
        filler.setHorizontalAlignment(JLabel.CENTER);
        this.setLayout(new GridLayout(1, 1));
        this.add(filler);
	}

}
