package org.greyfire.transcendancy.ui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;

import org.greyfire.transcendancy.cosmos.*;

public class SystemPanel extends SecondaryPanel {

	private static final long serialVersionUID = 1L;
	private StellarLocation star;

	public SystemPanel(StellarLocation star) {
		super();
		this.star = star;
		this.onOpen();
	}

	public SystemPanel(StellarLocation star, boolean doublebuffer) {
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
        JLabel filler = new JLabel(this.star.getLongTitle());
        filler.setHorizontalAlignment(JLabel.CENTER);
		filler.setForeground(Color.WHITE);
        this.setLayout(new GridLayout(1, 1));
        this.add(filler);
	}

	@Override
	public void redraw() {
		// TODO Auto-generated method stub
	}
}
