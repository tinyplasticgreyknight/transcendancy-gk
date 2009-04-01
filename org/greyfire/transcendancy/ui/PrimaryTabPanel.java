package org.greyfire.transcendancy.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.greyfire.transcendancy.cosmos.*;

public class PrimaryTabPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<SecondaryPanel> panels;
	private JTabbedPane tabs;

	public static Color TC_GALAXY   = new Color(255, 255, 255);
	public static Color TC_MINISTRY = new Color(224, 255, 255);
	public static Color TC_SYSTEM   = new Color(255, 255, 224);
	public static Color TC_PLANET   = new Color(224, 255, 224);
	public static Color TC_VESSEL   = new Color(224, 224, 224);
	public static Color TC_DEFAULT  = new Color(255, 255, 255);

	public PrimaryTabPanel() {
		super(new GridLayout(1, 1));
		this.tabs = new JTabbedPane();
		this.panels = new ArrayList<SecondaryPanel>();
		this.add(tabs);
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
    
	public int addSecondaryPanel(SecondaryPanel panel, String title, String tooltip) {
		this.tabs.addTab(title, null, panel, tooltip);
		this.panels.add(panel);
		return this.panels.size()-1;
	}
    
	public int addSecondaryPanel(SecondaryPanel panel, String tooltip) {
		String title = tooltip;
		if(title==null) title = "New Tab";
		this.tabs.addTab(title, null, panel, title);
		this.panels.add(panel);
		return this.panels.size()-1;
	}
	
	public boolean removeSecondaryPanel(int handle) {
		if(handle<=0) return false;
		if(handle>=this.panels.size()) return false;
		this.panels.remove(handle);
		return true;
	}
    
    public int selected() { return this.tabs.getSelectedIndex(); }

    public void setColour(int index, Color clr) { this.tabs.setBackgroundAt(index, clr); }
    public Color getColour(int index) { return this.tabs.getBackgroundAt(index); }

	public void addGalaxyPanel(String content, String title) {
		GalaxyPanel panel = new GalaxyPanel(content);
		int i = this.addSecondaryPanel(panel, title, title);
		this.setColour(i, PrimaryTabPanel.TC_GALAXY);
	}

	public void addMinistryPanel(String content, String title) {
		MinistryPanel panel = new MinistryPanel(content);
		int i = this.addSecondaryPanel(panel, title, title);
		this.setColour(i, PrimaryTabPanel.TC_MINISTRY);
	}

	public void addSystemPanel(Star star) {
		SystemPanel panel = new SystemPanel(star);
		int i = this.addSecondaryPanel(panel, star.getShortTitle(), star.getLongTitle());
		this.setColour(i, PrimaryTabPanel.TC_SYSTEM);
	}

	public void addVesselPanel(Vessel vessel) {
		VesselPanel panel = new VesselPanel(vessel);
		int i = this.addSecondaryPanel(panel, vessel.getShortTitle(), vessel.getLongTitle());
		this.setColour(i, PrimaryTabPanel.TC_VESSEL);
	}

	public void addPlanetPanel(Planet planet) {
		PlanetPanel panel = new PlanetPanel(planet);
		int i = this.addSecondaryPanel(panel, planet.getShortTitle(), planet.getLongTitle());
		this.setColour(i, PrimaryTabPanel.TC_PLANET);
	}
    
}
