package org.greyfire.transcendancy;

import javax.swing.SwingUtilities;

import org.greyfire.transcendancy.bio.Species;
import org.greyfire.transcendancy.cosmos.Coord;
import org.greyfire.transcendancy.cosmos.Galaxy;
import org.greyfire.transcendancy.cosmos.Planet;
import org.greyfire.transcendancy.cosmos.Star;
import org.greyfire.transcendancy.cosmos.Starlane;
import org.greyfire.transcendancy.cosmos.Vessel;
import org.greyfire.transcendancy.ui.FrameUI;
import org.greyfire.transcendancy.ui.UI;

public class Transcendancy {
	
	public static UI ui = new FrameUI();
	
	public static void run() {
		/* testing */
		Galaxy milkyway  = ui.galaxy();
		Species hanshaks = new Species("Hanshak");
		Species shevar   = new Species("Shevar", "Shevar");
		Star alpheratz = new Star("Alpheratz", new Coord( 10,  20,  30), hanshaks, Star.Classification.YELLOW_GIANT);
		Star tonklinus = new Star("Tonklinus", new Coord( 30,  80, -25), shevar,   Star.Classification.NEUTRON_STAR);
		Star vega      = new Star("Vega",      new Coord(-50, -50, -50), null,     Star.Classification.RED_DWARF);
		new Planet(null,       alpheratz, 1, new Coord(4.0, 0.0, 0.0),  new Coord(1.0, 0.0, 0.5), null,     Planet.Size.TINY, Planet.Classification.CHAPEL);
		new Planet("Hanhome",  alpheratz, 2, new Coord(10.0, 0.0, 0.0), new Coord(1.0, 0.0, 0.5), hanshaks, Planet.Size.MEDIUM, Planet.Classification.CATHEDRAL);
		new Planet("Ravehs",   tonklinus, 1, new Coord(10.0, 0.0, 0.0), new Coord(1.0, 0.0, 0.5), shevar,   Planet.Size.LARGE, Planet.Classification.HUSK);
		new Vessel("Saratoga", alpheratz, new Coord(10, 20, 30), new Coord(0.0, 0.0, 0.0), hanshaks, Vessel.Size.LARGE, Vessel.Classification.SARATOGA);
		
		milkyway.addAnchor(alpheratz);
		milkyway.addAnchor(tonklinus);
		milkyway.addAnchor(vega);

		milkyway.addLane(new Starlane(null, null, alpheratz, tonklinus));
		milkyway.addLane(new Starlane(null, null, vega,      tonklinus));
		milkyway.addLane(new Starlane(null, null, alpheratz, vega));
		
		ui.initialise();
		ui.activate();
		
		ui.redrawGalaxy();
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
