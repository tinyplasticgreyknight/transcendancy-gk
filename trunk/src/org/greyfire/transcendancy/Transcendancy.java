package org.greyfire.transcendancy;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

import org.greyfire.transcendancy.bio.Species;
import org.greyfire.transcendancy.cosmos.*;
import org.greyfire.transcendancy.ui.*;

public class Transcendancy {
    
    public static UI ui = FrameUI.Instance();
    
    public static void run() {
        DataResources.load();
            
        /* testing */
        final Galaxy milkyway  = ui.galaxy();
        Species race0 = Species.Instance(Species.names.get(0));
        Species race6 = Species.Instance(Species.names.get(6));
        race0.setColour(new Color(0, 0, 255));
        race6.setColour(new Color(255, 0, 255));
        Star S;
        int density      = Constants.GALAXY_DENSITY_MEDIUM;
        double tolerance = Constants.LANE_STRECH_TOLERANCE_MEDIUM;
        for(int i=0; i<density; i++) {
            S = Star.Random(Constants.GALAXY_RADIUS, true);
            try {
                milkyway.addAnchor(S);
            } catch(IllegalStateException e) {
                /* stars too close */
                Star.releaseName(S.getName());
                i--;
            }
        }
        milkyway.addRandomLanes(Math.round(Constants.GALAXY_RADIUS*tolerance*Constants.LANE_STRETCH_PROPORTION), 3);
        ArrayList<StellarLocation> anchors = milkyway.anchors();
        Star A;
        A = (Star)anchors.get(0);
        A.own(race0);
        if(A.numPlanets()>0) {
            A.getPlanet(0).own(race0);
        }
        A = (Star)anchors.get(1);
        A.own(race6);
        if(A.numPlanets()>0) {
            A.getPlanet(0).own(race6);
        }
        
        //new Vessel("Saratoga", star0, new Coord(10, 20, 30), new Coord(0.0, 0.0, 0.0), race0, Vessel.Size.LARGE, Vessel.Classification.SARATOGA);

//        milkyway.addLane(new Starlane(null, null, star0, star1));
//        milkyway.addLane(new Starlane(null, null, star2, star1));
//        milkyway.addLane(new Starlane(null, null, star0, star2));
        
        ui.initialise();
        ui.activate();
        
        ui.redrawGalaxy();
        Thread spinthread = new Thread() {
            public void run() {
            int FPS = 5;
            long last_tick = System.currentTimeMillis();
            long tick;
            while(ui.running()) {
                tick = System.currentTimeMillis();
                if(tick - last_tick >= 1000/FPS) {
                    last_tick = tick;
                    synchronized(milkyway.view_update) {
                        if(milkyway.view_update) {
                            milkyway.view_update = false;
                            ui.redrawGalaxy();
                        }
                    }
                }
            }
            }
        };
        Thread musicthread = new Thread() {
            public void run() {
                try {
                    Music.loop("ui/let-the-same-mind.wav", 3);
                } catch(Exception e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace(System.err);
                }
            }
        };
        spinthread.start();
        musicthread.start();
    }
    
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(new Runnable() {public void run() { Transcendancy.run();}});
        } catch(Exception e) {
            System.err.println(String.format("Exception during execution: %s", e.getMessage()));
            System.exit(1);
        }
    }
}
