package org.greyfire.transcendancy.ui;

import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.greyfire.transcendancy.Constants;
import org.greyfire.transcendancy.cosmos.Galaxy;
import org.greyfire.transcendancy.cosmos.Planet;
import org.greyfire.transcendancy.cosmos.StellarLocation;
import org.greyfire.transcendancy.cosmos.SystemObject;
import org.greyfire.transcendancy.cosmos.Vessel;

public class FrameUI extends JFrame implements UI {

	private static final long serialVersionUID = 1L;

	private int monitor_width;
	private int monitor_height;
	private boolean running = false;

	private PrimaryTabPanel main_panel;

	private Galaxy galaxy = new Galaxy();
	private SecondaryPanel galaxy_panel = null;
	
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
		int min_width  = Constants.GALAXY_VIEW_WIDTH;
		int min_height = Constants.GALAXY_VIEW_HEIGHT;
		int width  = Math.max(monitor_width/2,  min_width);
		int height = Math.max(monitor_height/2, min_height);
		this.setMinimumSize(new Dimension(min_width, min_height));
		this.setSize(width, height);
		this.setVisible(true);
		this.running = true;
		this.createBufferStrategy(2);
		this.repaint();
	}

	public void initialise() {
		this.main_panel = new PrimaryTabPanel();
		this.add(this.main_panel);
		
		this.main_panel.addGalaxyPanel(this.galaxy, "Galaxy");
		this.galaxy_panel = this.main_panel.getGalaxyPanel();
		this.main_panel.addMinistryPanel("bar", "Planets");
		this.main_panel.addMinistryPanel("baz", "Fleet");
		this.main_panel.addMinistryPanel("qux", "Research");
		this.main_panel.addMinistryPanel("007", "Diplomacy");
		this.main_panel.addMinistryPanel("zzz", "Special");
		
		/* testing */
		StellarLocation test_star = this.galaxy.findAnchor("Templeman");
		if(test_star!=null) {
			this.main_panel.addSystemPanel(test_star);
			SystemObject o;
			for(int i=0; i<test_star.numObjects(); i++) {
				o = test_star.findObject(i);
				if(o instanceof Planet) {
					this.main_panel.addPlanetPanel((Planet)o);
				} else if(o instanceof Vessel) {
					this.main_panel.addVesselPanel((Vessel)o);
				}
			}
		}
		
		this.pack();
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

	public Galaxy galaxy() { return this.galaxy; }
	
	public void redrawGalaxy() {
		if(this.galaxy_panel==null) throw new IllegalStateException("no galaxy panel exists");
		this.galaxy_panel.redraw();
	}
}
