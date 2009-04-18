package org.greyfire.transcendancy.ui;

import java.awt.event.*;

public class FrameUIAdapter extends WindowAdapter {

    private FrameUI owner = null;
    
    public FrameUIAdapter(FrameUI owner) {
        this.owner = owner;
    }
    
    public void windowClosing(WindowEvent e) {
        if(this.owner.verifyQuit()) {
            owner.shutdown();
        }
    }
    
    public void windowClosed(WindowEvent e) {
        owner.shutdown_disposed();
    }

}
