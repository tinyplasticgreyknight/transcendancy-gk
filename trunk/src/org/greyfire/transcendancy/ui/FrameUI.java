package org.greyfire.transcendancy.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.*;

import org.greyfire.transcendancy.*;
import org.greyfire.transcendancy.cosmos.*;

public class FrameUI extends JFrame implements UI {

    private static final long serialVersionUID = 1L;
    private static FrameUI instance = null;

    private int monitor_width;
    private int monitor_height;
    private boolean running = false;

    private PrimaryTabPanel main_panel;
    private JMenuBar menubar;

    private Galaxy galaxy = new Galaxy();
    private SecondaryPanel galaxy_panel = null;
    
    private FrameUI() throws HeadlessException {
        super(DataResources.gameproperties.get("name"));
        Dimension monitor_size = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        monitor_width  = monitor_size.width;
        monitor_height = monitor_size.height;
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new FrameUIAdapter(this));
        this.setBackground(Color.BLACK);
    }
    
    public synchronized static FrameUI Instance() throws HeadlessException {
        if(FrameUI.instance==null) {
            FrameUI.instance = new FrameUI();
        }
        return FrameUI.instance;
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
        Music.active = true;
        this.repaint();
    }
    
    private void initMenu() {
        JMenu game = new JMenu("Game");
        JMenu help = new JMenu("Help");
        this.menubar    = new JMenuBar();
        
        JMenuItem quit = new JMenuItem("Quit");
        final FrameUI self = this;
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                self.shutdown();
            }
        });
        quit.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));
        game.add(quit);
        
        this.menubar.add(game);
        this.menubar.add(help);
        
        this.setJMenuBar(this.menubar);
    }

    public void initialise() {
        this.main_panel = new PrimaryTabPanel();
        this.initMenu();
        this.add(main_panel);
        
        this.main_panel.addGalaxyPanel(this.galaxy, "Galaxy");
        this.galaxy_panel = this.main_panel.getGalaxyPanel();
        this.main_panel.addMinistryPanel("bar", "Planets");
        this.main_panel.addMinistryPanel("baz", "Fleet");
        this.main_panel.addMinistryPanel("qux", "Research");
        this.main_panel.addMinistryPanel("007", "Diplomacy");
        this.main_panel.addMinistryPanel("zzz", "Special");
        
        /* testing */
        StellarLocation test_star = this.galaxy.anchors().get(0);
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
    
    public void shutdown_disposed() {
        this.setVisible(false);
        Music.active = false;
        this.running = false;
    }

    public void shutdown() {
        if(this.isVisible()) {
            this.setVisible(false);
            this.dispose();
        }
        Music.active = false;
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
    public Boolean verifyQuit() {
        Boolean answer = this.ynq("Save before quitting?");
        if(answer==null) return false;
        if(answer) {
            /* TODO: save here */
            this.notify("You know we cannot save yet :-)  Quitting anyway...");
        }
        return true;
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
