/*
 * GalaxyPanelAdapter.java
 *
 * Created on 07 April 2009, 00:06
 */

package org.greyfire.transcendancy.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author rodge
 */
public abstract class GalaxyPanelAdapter implements ActionListener, ChangeListener {
    private GalaxyPanel parent;
    protected JToggleButton toggle;
    
    public GalaxyPanelAdapter(GalaxyPanel parent) {
        this.parent = parent;
    }
    public GalaxyPanelAdapter(GalaxyPanel parent, JToggleButton toggle) {
        this.parent = parent;
        this.toggle = toggle;
    }
    
    public void spin_left()  { parent.spin_left(); }
    public void spin_right() { parent.spin_right(); }
    public void spin_up()    { parent.spin_up(); }
    public void spin_down()  { parent.spin_down(); }
    public void zoom_in() { parent.zoom_in(); }
    public void zoom_out() { parent.zoom_out(); }
    public void toggle_show_lanes() {
        parent.show_lanes = !parent.show_lanes;
        parent.redraw();
    }
    public void toggle_show_lanes(boolean state) {
        parent.show_lanes = state;
        parent.redraw();
    }
    public void toggle_show_stars() {
        parent.show_stars = !parent.show_stars;
        parent.redraw();
    }
    public void toggle_show_stars(boolean state) {
        parent.show_stars = state;
        parent.redraw();
    }
    public void toggle_show_colonies() {
        parent.show_colonies = !parent.show_colonies;
        parent.redraw();
    }
    public void toggle_show_colonies(boolean state) {
        parent.show_colonies = state;
        parent.redraw();
    }
    public void toggle_show_traderoutes() {
        parent.show_traderoutes = !parent.show_traderoutes;
        parent.redraw();
    }
    public void toggle_show_traderoutes(boolean state) {
        parent.show_traderoutes = state;
        parent.redraw();
    }

    public void actionPerformed(ActionEvent e) {
        /* override me */
    }
    public void stateChanged(ChangeEvent e) {
        /* override me */
    }
    
}
