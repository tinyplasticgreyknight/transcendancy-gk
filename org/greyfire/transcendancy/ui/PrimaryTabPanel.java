package org.greyfire.transcendancy.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.greyfire.transcendancy.cosmos.Galaxy;
import org.greyfire.transcendancy.cosmos.Planet;
import org.greyfire.transcendancy.cosmos.StellarLocation;
import org.greyfire.transcendancy.cosmos.Vessel;

public class PrimaryTabPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<SecondaryPanel> panels = new ArrayList<SecondaryPanel>();
	private GalaxyPanel gpanel = null;
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
		this.add(tabs);
		tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
    
	public int addSecondaryPanel(SecondaryPanel panel, String title, String tooltip, Color clr) {
		int i = this.panels.size();
		if((panel instanceof GalaxyPanel) && (this.gpanel!=null)) throw new IllegalStateException("cannot have more than one galaxy tab");
		this.tabs.addTab(title, null, panel, tooltip);
		this.tabs.setBackgroundAt(i, clr);
		this.panels.add(panel);
		return i;
	}
    
	public int addSecondaryPanel(SecondaryPanel panel, String tooltip, Color clr) {
		int i = this.panels.size();
		if((panel instanceof GalaxyPanel) && (this.gpanel!=null)) throw new IllegalStateException("cannot have more than one galaxy tab");
		String title = tooltip;
		if(title==null) title = "New Tab";
		this.tabs.addTab(title, null, panel, title);
		this.tabs.setBackgroundAt(i, clr);
		this.panels.add(panel);
		return i;
	}
	
	public boolean removeSecondaryPanel(int handle) {
		if(handle<=0) return false;
		if(handle>=this.panels.size()) return false;
		SecondaryPanel candidate = this.panels.get(handle);
		if(candidate instanceof GalaxyPanel) return false;
		this.panels.remove(handle);
		return true;
	}
    
    public int selected() { return this.tabs.getSelectedIndex(); }

    public void setColour(int index, Color clr) { this.tabs.setBackgroundAt(index, clr); }
    public Color getColour(int index) { return this.tabs.getBackgroundAt(index); }

	public void addGalaxyPanel(Galaxy content, String title) {
		if(this.gpanel!=null) throw new IllegalStateException("cannot have more than one galaxy tab");
		GalaxyPanel panel = new GalaxyPanel(content, true);
		this.addSecondaryPanel(panel, title, title, PrimaryTabPanel.TC_GALAXY);
		this.gpanel = panel;
	}

	public void addMinistryPanel(String content, String title) {
		MinistryPanel panel = new MinistryPanel(content);
		this.addSecondaryPanel(panel, title, title, PrimaryTabPanel.TC_MINISTRY);
	}

	public void addSystemPanel(StellarLocation star) {
		SystemPanel panel = new SystemPanel(star);
		this.addSecondaryPanel(panel, star.getShortTitle(), star.getLongTitle(), PrimaryTabPanel.TC_SYSTEM);
	}

	public void addVesselPanel(Vessel vessel) {
		VesselPanel panel = new VesselPanel(vessel);
		this.addSecondaryPanel(panel, vessel.getShortTitle(), vessel.getLongTitle(), PrimaryTabPanel.TC_VESSEL);
	}

	public void addPlanetPanel(Planet planet) {
		PlanetPanel panel = new PlanetPanel(planet);
		this.addSecondaryPanel(panel, planet.getShortTitle(), planet.getLongTitle(), PrimaryTabPanel.TC_PLANET);
	}
    
	public GalaxyPanel getGalaxyPanel() { return this.gpanel; }
}
