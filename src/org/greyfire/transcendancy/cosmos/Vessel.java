package org.greyfire.transcendancy.cosmos;

import org.greyfire.transcendancy.bio.*;
import org.greyfire.transcendancy.bio.Species.Term;

public class Vessel extends SystemObject implements Comparable<Vessel> {
    
    public enum Classification {
        SARATOGA   ("Saratoga");
        
        private String name;
        private Classification(String name) { this.name = name; }
        public String toString() { return this.name; }
    }
    
    public enum Size {
        TINY    ("tiny"),
        SMALL   ("small"),
        MEDIUM  ("medium"),
        LARGE   ("large"),
        ENORMOUS("enormous");
        
        private String name;
        private Size(String name) { this.name = name; }
        public String toString() { return this.name; }
    }

    protected Size size;
    protected Classification classification;

    public Vessel(String name, StellarLocation site, Coord position, Coord orbital_parameters, Species owner, Size size, Classification classification) {
        super(name, site, position, orbital_parameters, owner);
        this.size = size;
        this.classification = classification;
    }
    public void own(Species new_owner) {
        this.disown();
        if(new_owner!=null) {
            this.owner = new_owner;
            this.owner.registerVessel(this);
        }
    }
    public void disown() {
        if(this.owner!=null) {
            this.owner = null;
            this.owner.deregisterVessel(this);
        }
    }

    public void setClassification(Classification classification) { this.classification = classification; }
    public Classification getClassification() { return classification; }
    public void setSize(Size size) { this.size = size; }
    public Size getSize() { return size; }

    @Override
    public String getLongTitle() {
        String shorttitle = this.getShortTitle();
        if(owner==null) {
            return String.format("%s (%s %s-class vessel)", shorttitle, this.size, this.classification);
        } else {
            return String.format("%s (%s %s-class vessel, %s)", shorttitle, this.size, this.classification, this.owner.term(Term.QUALIFIER));
        }
    }

    @Override
    public String getShortTitle() {
        return String.format("\"%s\"", this.name);
    }

    public int compareTo(Vessel v) {
        return this.name.compareTo(v.getName());
    }
}
