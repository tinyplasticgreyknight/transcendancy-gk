package org.greyfire.transcendancy.ui;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import org.greyfire.transcendancy.cosmos.*;

public class VesselPanel extends SecondaryPanel {

    private static final long serialVersionUID = 1L;
    private Vessel vessel;

    public VesselPanel(Vessel vessel) {
        super();
        this.vessel = vessel;
        this.onOpen();
    }

    public VesselPanel(Vessel vessel, boolean doublebuffer) {
        super(doublebuffer);
        this.vessel = vessel;
        this.onOpen();
    }

    @Override
    public void onClose() {
        /* donothing */
    }

    @Override
    public void onOpen() {
        JLabel filler = new JLabel(this.vessel.getLongTitle());
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
