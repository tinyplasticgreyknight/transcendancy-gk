package org.greyfire.transcendancy.cosmos;

import java.util.ArrayList;
import java.util.Random;
import org.greyfire.transcendancy.Constants;
import org.greyfire.transcendancy.Util;
import org.greyfire.transcendancy.bio.*;
import org.greyfire.transcendancy.bio.Species.Term;
import org.greyfire.transcendancy.ui.ArtCanvas;

public class Star extends StellarLocation {
    
    protected StarClass classification;
    
    private static ArrayList<String> available_names;
    private ArrayList<Planet> planets = new ArrayList<Planet>();
    private static Random stream_names  = new Random(Util.seed());
    private static Random stream_system = new Random(Util.seed());
    
    public final static int colony_inner_ring = 4; /** this radius should be sufficient to contain all of the possible startypes' mini.png glyphs */
    public final static int colony_outer_ring = colony_inner_ring + Constants.MAX_PLAYERS*2;

    public Star(String name, Coord position, Species owner, StarClass classification) {
        super(name, position, owner);
        this.setClassification(classification);
    }
    public static Star Random(int radius, boolean with_planets_p) {
        Star instance = null;
        StarClass classification = StarClass.Random();
        String name;
        long x = stream_system.nextInt(2*radius)-radius;
        long y = stream_system.nextInt(2*radius)-radius;
        long z = stream_system.nextInt(2*radius)-radius;
        Coord position = new Coord(x, y, z);
        double r = Math.cbrt(stream_system.nextInt(radius*radius*radius));
        position.set(r, position.theta(), position.phi());
        int nplanets = stream_system.nextInt(classification.maxPlanets()+1);
        name = Star.popRandomName();
        instance = new Star(name, position, null, classification);
        Coord orbital;
        Planet P;
        int i = 1;
        r = 2.0;
        while(i<=nplanets && r<20) {
            r += 2.0 + stream_system.nextDouble()*3.0;
            orbital = new Coord(0.2, 0.0, 0.0);
            position = new Coord(r, stream_system.nextDouble()*2*Math.PI, 0.0);
            P = new Planet(null, instance, i, position, orbital, null, Planet.Size.Random(), Planet.Classification.Random());
            //System.out.println(String.format("Created planet %s (%d/%d)", P.getName(), i, nplanets));
            instance.planets.add(P);
            P.orbit();
            i++;
        }
        return instance;
    }
    
    public void setClassification(StarClass classification) {
        if(classification==null) {
            this.classification = StarClass.Random();
        } else {
            this.classification = classification;
        }
    }
    public StarClass getClassification() { return classification; }
    
    
    public static void resetNames(ArrayList<String> allnames) {
        available_names = allnames;
    }
    public static String popRandomName() {
        String name;
        int i;
        if(available_names==null || available_names.isEmpty())  throw new IllegalStateException("no more star names left");
        i = stream_names.nextInt(available_names.size());
        name = available_names.get(i);
        available_names.remove(i);
        return name;
    }
    public static void releaseName(String name) { available_names.add(name); }

    @Override
    public String getLongTitle() {
        String shorttitle = this.getShortTitle();
        if(owner==null) {
            return String.format("%s (%s system)", shorttitle, this.classification);
        } else {
            return String.format("%s (%s system, %s)", shorttitle, this.classification, this.owner.term(Term.QUALIFIER));
        }
    }

    @Override
    public String getShortTitle() {
        return this.name;
    }
    
    public ArrayList<Planet> getPlanets() { return this.planets; }
    public Planet getPlanet(int i) { return this.planets.get(i); }
    public int numPlanets() { return this.planets.size(); }
    public int maxPlanets() { return this.classification.maxPlanets(); }
    public int maxConnections() { return this.classification.maxConnections(); }
    
    public void painter(ArtCanvas canvas, int u, int v) {
        this.classification.painter(canvas, u, v);
    }

    public void painter(ArtCanvas canvas, int u, int v, boolean showcolonies) {
        if(showcolonies) {
            if(this.owner==null) {
                canvas.pset_safe(u+Star.colony_inner_ring, v, ArtCanvas.WHITE);
                canvas.pset_safe(u-Star.colony_inner_ring, v, ArtCanvas.WHITE);
                canvas.pset_safe(u, v+Star.colony_inner_ring, ArtCanvas.WHITE);
                canvas.pset_safe(u, v-Star.colony_inner_ring, ArtCanvas.WHITE);
            } else {
                canvas.circle_safe(u, v, Star.colony_inner_ring, this.owner.colour_translated());
            }
        } else {
            
        }
        this.painter(canvas, u, v);
    }

}
