package org.greyfire.transcendancy.ui;

import java.awt.GridLayout;

import org.greyfire.transcendancy.cosmos.Galaxy;

public class GalaxyPanel extends SecondaryPanel {

	private static final long serialVersionUID = 1L;
	private Galaxy content;
	private ArtCanvas canvas;

	public GalaxyPanel(Galaxy content) {
		super();
		this.content = content;
		this.onOpen();
	}

	public GalaxyPanel(Galaxy content, boolean doublebuffer) {
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
		this.canvas = new ArtCanvas(320, 240);
        this.setLayout(new GridLayout(1, 1));
        this.add(this.canvas);
	}
}
