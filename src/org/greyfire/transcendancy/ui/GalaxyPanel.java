package org.greyfire.transcendancy.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.greyfire.transcendancy.Constants;
import org.greyfire.transcendancy.cosmos.Coord;
import org.greyfire.transcendancy.cosmos.Galaxy;
import org.greyfire.transcendancy.cosmos.Star;
import org.greyfire.transcendancy.cosmos.Starlane;
import org.greyfire.transcendancy.cosmos.StellarLocation;

public class GalaxyPanel extends SecondaryPanel {

    private static final long serialVersionUID = 1L;
    private final Galaxy content;
    private final GalaxyPanel self;
    private ArtCanvas canvas;
    public boolean show_stars    = true;
    public boolean show_lanes    = true;
    public boolean show_colonies = true;
    public boolean show_traderoutes = false;
    private int cam_dist = Constants.GALAXY_VIEW_CAM_DIST;

    public GalaxyPanel(Galaxy content) {
        super();
        this.self = this;
        this.content = content;
        this.onOpen();
    }

    public GalaxyPanel(Galaxy content, boolean doublebuffer) {
        super(doublebuffer);
        this.self = this;
        this.content = content;
        this.onOpen();
    }

    @Override
    public void onClose() {
        /* donothing */
    }

    @Override
    public void onOpen() {
        this.canvas = new ArtCanvas(Constants.GALAXY_VIEW_WIDTH, Constants.GALAXY_VIEW_HEIGHT);
        GroupLayout lMain = new GroupLayout(this);
        ParallelGroup lPar, lPar2, lPar3;
        SequentialGroup lSeq, lSeq2, lSeq3;
        this.setLayout(lMain);
        
        JButton btnLeft = new JButton("left");
        btnLeft.addActionListener(new GalaxyPanelAdapter(this) {
            public void actionPerformed(ActionEvent e) {
                this.spin_left();
            }
        });
        JButton btnRight = new JButton("right");
        btnRight.addActionListener(new GalaxyPanelAdapter(this) {
            public void actionPerformed(ActionEvent e) {
                this.spin_right();
            }
        });
        JButton btnUp = new JButton("up");
        btnUp.addActionListener(new GalaxyPanelAdapter(this) {
            public void actionPerformed(ActionEvent e) {
                this.spin_up();
            }
        });
        JButton btnDown = new JButton("down");
        btnDown.addActionListener(new GalaxyPanelAdapter(this) {
            public void actionPerformed(ActionEvent e) {
                this.spin_down();
            }
        });
        JButton btnZoomIn = new JButton("zoom in");
        btnZoomIn.addActionListener(new GalaxyPanelAdapter(this) {
            public void actionPerformed(ActionEvent e) {
                this.zoom_in();
            }
        });
        JButton btnZoomOut = new JButton("zoom out");
        btnZoomOut.addActionListener(new GalaxyPanelAdapter(this) {
            public void actionPerformed(ActionEvent e) {
                this.zoom_out();
            }
        });
        JToggleButton btnStars = new JToggleButton("stars");
        btnStars.addChangeListener(new GalaxyPanelAdapter(this, btnStars) {
            public void stateChanged(ChangeEvent e) {
                this.toggle_show_stars(toggle.isSelected());
            }
        });
        if(this.show_stars) btnStars.doClick();
        JToggleButton btnLanes = new JToggleButton("lanes");
        btnLanes.addChangeListener(new GalaxyPanelAdapter(this, btnLanes) {
            public void stateChanged(ChangeEvent e) {
                this.toggle_show_lanes(toggle.isSelected());
            }
        });
        if(this.show_lanes) btnLanes.doClick();
        JToggleButton btnColonies = new JToggleButton("colonies");
        btnColonies.addChangeListener(new GalaxyPanelAdapter(this, btnColonies) {
            public void stateChanged(ChangeEvent e) {
                this.toggle_show_colonies(toggle.isSelected());
            }
        });
        if(this.show_colonies) btnColonies.doClick();
        JToggleButton btnTraderoutes = new JToggleButton("trade routes");
        btnTraderoutes.addChangeListener(new GalaxyPanelAdapter(this, btnTraderoutes) {
            public void stateChanged(ChangeEvent e) {
                this.toggle_show_traderoutes(toggle.isSelected());
            }
        });
        if(this.show_traderoutes) btnTraderoutes.doClick();
        
        lMain = new GroupLayout(this);
        this.setLayout(lMain);

        lPar = lMain.createParallelGroup(GroupLayout.Alignment.LEADING);
        lSeq = lMain.createSequentialGroup();
        lSeq.addComponent(this.canvas, this.canvas.width(), this.canvas.width(), this.canvas.width());
        lPar2 = lMain.createParallelGroup(GroupLayout.Alignment.LEADING);
        lSeq2 = lMain.createSequentialGroup();
        lSeq2.addComponent(btnLeft);
        lSeq2.addComponent(btnRight);
        lPar2.addGroup(lSeq2);
        lSeq2 = lMain.createSequentialGroup();
        lSeq2.addComponent(btnUp);
        lSeq2.addComponent(btnDown);
        lPar2.addGroup(lSeq2);
        lSeq2 = lMain.createSequentialGroup();
        lSeq2.addComponent(btnZoomIn);
        lSeq2.addComponent(btnZoomOut);
        lPar2.addGroup(lSeq2);
        lSeq2 = lMain.createSequentialGroup();
        lSeq2.addComponent(btnStars);
        lSeq2.addComponent(btnLanes);
        lPar2.addGroup(lSeq2);
        lSeq2 = lMain.createSequentialGroup();
        lSeq2.addComponent(btnColonies);
        lSeq2.addComponent(btnTraderoutes);
        lPar2.addGroup(lSeq2);
        lSeq.addGroup(lPar2);
        lSeq.addContainerGap(300, Short.MAX_VALUE);
        lPar.addGroup(lSeq);
        lMain.setHorizontalGroup(lPar);

        lPar = lMain.createParallelGroup(GroupLayout.Alignment.LEADING);
        lSeq = lMain.createSequentialGroup();
        lPar2 = lMain.createParallelGroup(GroupLayout.Alignment.LEADING);
        lPar2.addComponent(this.canvas, this.canvas.height(), this.canvas.height(), this.canvas.height());
        lSeq2 = lMain.createSequentialGroup();
        lPar3 = lMain.createParallelGroup(GroupLayout.Alignment.LEADING);
        lPar3.addComponent(btnLeft);
        lPar3.addComponent(btnRight);
        lSeq2.addGroup(lPar3);
        lPar3 = lMain.createParallelGroup(GroupLayout.Alignment.LEADING);
        lPar3.addComponent(btnUp);
        lPar3.addComponent(btnDown);
        lSeq2.addGroup(lPar3);
        lPar3 = lMain.createParallelGroup(GroupLayout.Alignment.LEADING);
        lPar3.addComponent(btnZoomIn);
        lPar3.addComponent(btnZoomOut);
        lSeq2.addGroup(lPar3);
        lPar3 = lMain.createParallelGroup(GroupLayout.Alignment.LEADING);
        lPar3.addComponent(btnStars);
        lPar3.addComponent(btnLanes);
        lSeq2.addGroup(lPar3);
        lPar3 = lMain.createParallelGroup(GroupLayout.Alignment.LEADING);
        lPar3.addComponent(btnColonies);
        lPar3.addComponent(btnTraderoutes);
        lSeq2.addGroup(lPar3);
        lPar2.addGroup(lSeq2);
        lSeq.addGroup(lPar2);
        lSeq.addContainerGap(250, Short.MAX_VALUE);
        lPar.addGroup(lSeq);
        lMain.setVerticalGroup(lPar);
    }
    
    public synchronized void spin_left() {
        synchronized(content.view_update) {
            content.view_update = true;
            content.view_theta += 0.1;
            while(content.view_theta>=2*Math.PI)  content.view_theta -= 2*Math.PI;
        }
    }
    public synchronized void spin_right() {
        synchronized(content.view_update) {
            content.view_update = true;
            content.view_theta -= 0.1;
            while(content.view_theta<=0)  content.view_theta += 2*Math.PI;
        }
    }
    public synchronized void spin_up() {
        synchronized(content.view_update) {
            content.view_update = true;
            content.view_phi += 0.1;
            while(content.view_phi>=2*Math.PI)  content.view_phi -= 2*Math.PI;
        }
    }
    public synchronized void spin_down() {
        synchronized(content.view_update) {
            content.view_update = true;
            content.view_phi -= 0.1;
            while(content.view_phi<=0)  content.view_phi += 2*Math.PI;
        }
    }
    
    public synchronized void zoom_in() {
        synchronized(content.view_update) {
            content.view_update = true;
            this.cam_dist = Math.max(this.cam_dist - 10, Constants.GALAXY_MIN_CAM_DIST);
        }
    }
    
    public synchronized void zoom_out() {
        synchronized(content.view_update) {
            content.view_update = true;
            this.cam_dist = Math.min(this.cam_dist + 10, Constants.GALAXY_VIEW_CAM_DIST);
        }
    }

    @Override
    public void redraw() {
        ArrayList<StellarLocation> anchors = content.anchors();
        ArrayList<Starlane>        lanes   = content.lanes();
        Coord c  = new Coord();
        Coord c2 = new Coord();
        int clr, cclr;
        int u, v, u2, v2;
        int z, z2;
        int width  = Constants.GALAXY_VIEW_WIDTH;
        int height = Constants.GALAXY_VIEW_HEIGHT;
        double anglex = Constants.GALAXY_VIEW_CAM_ANGLE_X;
        double angley = Constants.GALAXY_VIEW_CAM_ANGLE_Y;
        ArrayList<ArrayList<Object>> drawinglist = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> drawingentry;
        
        this.canvas.clear(Color.BLACK);
        /*System.out.println(String.format("paint <stars: %s; lanes: %s; colonies: %s; traderoutes: %s>",
                (this.show_stars ? "yes":"no"),
                (this.show_lanes ? "yes":"no"),
                (this.show_colonies ? "yes":"no"),
                (this.show_traderoutes ? "yes":"no")
                ));*/
        
        if(show_lanes) {
        for(Starlane lane : lanes) {
            c.set(lane.getAnchorA().getPosition());
            c2.set(lane.getAnchorB().getPosition());
            c.set(c.r(),   c.theta()+content.view_theta,  c.phi()+content.view_phi);
            c2.set(c2.r(), c2.theta()+content.view_theta, c2.phi()+content.view_phi);
            z  = ((int)c.z())  + this.cam_dist;
            z2 = ((int)c2.z()) + this.cam_dist;
            u  = (int)(width  * (  (c.x()  * anglex / z)  + 0.5));
            v  = (int)(height * (1-(c.y()  * angley / z)  - 0.5));
            u2 = (int)(width  * (  (c2.x() * anglex / z2) + 0.5));
            v2 = (int)(height * (1-(c2.y() * angley / z2) - 0.5));
            //System.err.println(String.format("%s: <%d, %d>--<%d, %d>", lane.getLongTitle(), u, v, u2, v2));
            //if(u>0 && v>0 && u2>0 && v2>0 && u<width && v<height && u2<width && v2<height) {
                drawingentry = new ArrayList<Object>();
                drawingentry.add(lane);
                drawingentry.add(Math.max(z, z2));
                drawingentry.add(u);
                drawingentry.add(v);
                drawingentry.add(u2);
                drawingentry.add(v2);
                drawinglist.add(drawingentry);
            //}
        }
        }
        if(show_stars) {
            int edge = Star.colony_inner_ring-1;
        for(StellarLocation star : anchors) {
            c.set(star.getPosition());
            c.set(c.r(),   c.theta()+content.view_theta,  c.phi()+content.view_phi);
            z = ((int)c.z()) + this.cam_dist;
            u  = (int)(width  * (  (c.x()  * anglex / z) + 0.5));
            v  = (int)(height * (1-(c.y()  * angley / z) - 0.5));
            //System.err.println(String.format("%s: <%d, %d>", star.getName(), u, v));
            if(star instanceof Star) {
                if(u>edge && v>edge && u<width-1-edge && v<height-1-edge) {
                    drawingentry = new ArrayList<Object>();
                    drawingentry.add(star);
                    drawingentry.add(z);
                    drawingentry.add(u);
                    drawingentry.add(v);
                    drawinglist.add(drawingentry);
                }
            } else {
                /* TODO: draw non-star object */
            }
        }
        }
        Collections.sort(drawinglist, new Comparator() {
            public int compare(Object o1, Object o2) {
                ArrayList<Object> entry1 = (ArrayList<Object>)o1;
                ArrayList<Object> entry2 = (ArrayList<Object>)o2;
                return ((Integer)(entry2.get(1))) - ((Integer)(entry1.get(1)));
            }
        });
        for(ArrayList<Object> entry : drawinglist) {
            if(entry.get(0) instanceof Starlane) {
                u  = (Integer)entry.get(2);
                v  = (Integer)entry.get(3);
                u2 = (Integer)entry.get(4);
                v2 = (Integer)entry.get(5);
                ((Starlane)entry.get(0)).painter(this.canvas, u, v, u2, v2, show_traderoutes);
            } else {
                u  = (Integer)entry.get(2);
                v  = (Integer)entry.get(3);
                ((Star)entry.get(0)).painter(this.canvas, u, v, show_colonies);
            }
        }
        this.canvas.refresh();
    }
}
