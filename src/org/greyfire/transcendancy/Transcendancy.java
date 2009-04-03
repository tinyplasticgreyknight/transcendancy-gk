package org.greyfire.transcendancy;

import javax.swing.SwingUtilities;

import org.greyfire.transcendancy.bio.Species;
import org.greyfire.transcendancy.bio.Species.Term;
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
	public static DataResources data = new DataResources();
	
	public static void run() {
		/* testing */
		Galaxy milkyway  = ui.galaxy();
		Species race0 = Species.Instance(Species.names.get(0));
		Species race6 = Species.Instance(Species.names.get(6));
		Star star0 = new Star(data.starnames.get(0), new Coord( 10,  20,  30), race0, Star.Classification.ORANGE_GIANT);
		Star star1 = new Star(data.starnames.get(1), new Coord( 30,  80, -25), race6, Star.Classification.PULSAR);
		Star star2 = new Star(data.starnames.get(2), new Coord(-50, -50, -50), null,  Star.Classification.RED_DWARF);
		new Planet(null,              star0, 1, new Coord(4.0, 0.0, 0.0),  new Coord(1.0, 0.0, 0.5), null,  Planet.Size.TINY, Planet.Classification.ORE);
		new Planet(race0.term(Term.HOMEWORLD), star0, 2, new Coord(10.0, 0.0, 0.0), new Coord(1.0, 0.0, 0.5), race0, Planet.Size.MEDIUM, Planet.Classification.NIMBUS);
		new Planet(race6.term(Term.HOMEWORLD), star1, 1, new Coord(10.0, 0.0, 0.0), new Coord(1.0, 0.0, 0.5), race6, Planet.Size.LARGE, Planet.Classification.HELLISH);
		new Vessel("Saratoga",        star0, new Coord(10, 20, 30), new Coord(0.0, 0.0, 0.0), race0, Vessel.Size.LARGE, Vessel.Classification.SARATOGA);
		
		milkyway.addAnchor(star0);
		milkyway.addAnchor(star1);
		milkyway.addAnchor(star2);

		milkyway.addLane(new Starlane(null, null, star0, star1));
		milkyway.addLane(new Starlane(null, null, star2, star1));
		milkyway.addLane(new Starlane(null, null, star0, star2));
		
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
