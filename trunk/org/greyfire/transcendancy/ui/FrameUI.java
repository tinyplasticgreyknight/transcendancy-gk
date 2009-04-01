package org.greyfire.transcendancy.ui;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.greyfire.transcendancy.cosmos.*;

public class FrameUI extends JFrame implements UI {

	private static final long serialVersionUID = 1L;

	private int monitor_width;
	private int monitor_height;
	private boolean running = false;

	private PrimaryTabPanel main_panel;
	
	public FrameUI() throws HeadlessException {
		// TODO Auto-generated constructor stub
		super("Transcendancy");
		Dimension monitor_size = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		monitor_width  = monitor_size.width;
		monitor_height = monitor_size.height;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addWindowListener(new FrameUIAdapter(this));
	}

	public void activate() {
		this.setVisible(true);
		this.running = true;
		Star alpheratz = new Star("Alpheratz", new Coord(1, 2, 3), "Hanshaks", Star.Classification.YELLOW_GIANT);
		Planet alpheratz_2 = new Planet("Alpheratz II", new Coord(10.0, 0.0, 0.0), new Coord(1.0, 0.0, 0.5), "Shevar", Planet.Size.SMALL, Planet.Classification.CATHEDRAL);
		Vessel saratoga    = new Vessel("Saratoga", new Coord(10, 20, 30), new Coord(0.0, 0.0, 0.0), "Hanshaks", Vessel.Size.LARGE, Vessel.Classification.SARATOGA);
		this.main_panel.addGalaxyPanel("foo", "Main");
		this.main_panel.addMinistryPanel("bar", "Planets");
		this.main_panel.addMinistryPanel("baz", "Fleet");
		this.main_panel.addMinistryPanel("qux", "Research");
		this.main_panel.addMinistryPanel("qax", "Diplomacy");
		this.main_panel.addMinistryPanel("zzz", "Special");
		this.main_panel.addSystemPanel(alpheratz);
		this.main_panel.addPlanetPanel(alpheratz_2);
		this.main_panel.addVesselPanel(saratoga);
		
	}

	public void initialise() {
		this.main_panel = new PrimaryTabPanel();
		this.add(this.main_panel);
		this.pack();
		this.setSize(monitor_width/2, monitor_height/2);
	}

	public void shutdown() {
		if(this.isVisible()) {
			this.setVisible(false);
			this.dispose();
		}
		this.running = false;
	}
	
	public boolean running() {
		return this.running;
	}

	public String prompt(String question) {
		return JOptionPane.showInputDialog(this, question);
	}

	public String prompt(String question, String default_value) {
		if(default_value==null) {
			return JOptionPane.showInputDialog(this, question);
		} else {
			return JOptionPane.showInputDialog(this, question, default_value);
		}
	}
	
	public String choice(String question, String[] values, String default_value) {
		return (String) JOptionPane.showInputDialog(this, question, "Prompt", JOptionPane.QUESTION_MESSAGE, null, values, default_value);
	}

	public Boolean yn(String question) {
		int response = JOptionPane.showConfirmDialog(this, question, "Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(response==JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean ynq(String question) {
		int response = JOptionPane.showConfirmDialog(this, question, "Question", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(response==JOptionPane.YES_OPTION) {
			return true;
		} else if(response==JOptionPane.NO_OPTION) {
			return false;
		} else {
			return null;
		}
	}

	public void notify(String message) {
		JOptionPane.showMessageDialog(this, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
	}

	public void warn(String message) {
		JOptionPane.showMessageDialog(this, message, "Notification", JOptionPane.WARNING_MESSAGE);
	}

}
