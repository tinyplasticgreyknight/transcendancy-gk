package org.greyfire.transcendancy.ui;

import java.awt.GridLayout;

import javax.swing.JLabel;

import org.greyfire.transcendancy.cosmos.*;

public class SystemPanel extends SecondaryPanel {

	private static final long serialVersionUID = 1L;
	private Star star;

	public SystemPanel(Star star) {
		super();
		this.star = star;
		this.onOpen();
	}

	public SystemPanel(Star star, boolean doublebuffer) {
		super(doublebuffer);
		this.star = star;
		this.onOpen();
	}

	@Override
	public void onClose() {
		/* donothing */
	}

	@Override
	public void onOpen() {
        JLabel filler = new JLabel(this.star.getName());
        filler.setHorizontalAlignment(JLabel.CENTER);
        this.setLayout(new GridLayout(1, 1));
        this.add(filler);
	}

}
