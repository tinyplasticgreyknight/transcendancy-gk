package org.greyfire.transcendancy.ui;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import org.greyfire.transcendancy.cosmos.*;

public class PlanetPanel extends SecondaryPanel {

	private static final long serialVersionUID = 1L;
	private Planet planet;

	public PlanetPanel(Planet planet) {
		super();
		this.planet = planet;
		this.onOpen();
	}

	public PlanetPanel(Planet planet, boolean doublebuffer) {
		super(doublebuffer);
		this.planet = planet;
		this.onOpen();
	}

	@Override
	public void onClose() {
		/* donothing */
	}

	@Override
	public void onOpen() {
        JLabel filler = new JLabel(this.planet.getName());
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
