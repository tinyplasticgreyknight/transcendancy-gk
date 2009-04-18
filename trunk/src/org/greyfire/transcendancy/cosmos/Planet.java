package org.greyfire.transcendancy.cosmos;

import org.greyfire.transcendancy.Util;
import org.greyfire.transcendancy.bio.*;
import org.greyfire.transcendancy.bio.Species.Term;

public class Planet extends SystemObject implements Comparable<Planet> {
    
    public enum Classification {
        /* TODO: load this data from resources */
        HELLISH   ("Hellish"),
        SCORCHED  ("Scorched"),
        GAS_GIANT ("Gas Giant"),
        FORESTED  ("Forested"),
        ORE       ("Ore"),
        METALLIC  ("Metallic"),
        NIMBUS    ("Nimbus"),
        SOJOURNER ("Sojourner"),
        JEWEL     ("Jewel"),
        JUNGLE    ("Jungle"),
        PARADISE  ("Paradise");
        
        private String name;
        private Classification(String name) { this.name = name; }
        public String toString() { return this.name; }
        public static Classification Random() {
            return NIMBUS;
        }
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
        public static Size Random() {
            return MEDIUM;
        }
    }

    protected Size size;
    protected Classification classification;
    protected int site_index;
    protected String canonical_name;
    protected boolean overridden_name;

    public Planet(String name, StellarLocation site, int site_index, Coord position, Coord orbital_parameters, Species owner, Size size, Classification classification) {
        super(name, site, position, orbital_parameters, owner);
        this.size = size;
        this.site_index = site_index;
        this.classification = classification;
        this.canonical_name = String.format("%s %s", site.getName(), Util.romanNumerals(site_index));
        this.setName(this.name);
    }

    public void setClassification(Classification classification) { this.classification = classification; }
    public Classification getClassification() { return classification; }
    public void setSize(Size size) { this.size = size; }
    public Size getSize() { return size; }
    
    public void setName(String new_name) {
        if(new_name==null || new_name.equals(this.canonical_name)) {
            this.name = this.canonical_name;
            this.overridden_name = false;
        } else {
            this.name = new_name;
            this.overridden_name = true;
        }
    }

    @Override
    public String getLongTitle() {
        String shorttitle = this.getShortTitle();
        String sys_spec;
        if(this.overridden_name) {
            sys_spec = this.canonical_name;
        } else {
            sys_spec = String.format("%s system", this.site.getName());
        }
        if(owner==null) {
            return String.format("%s (%s %s-class planet, %s)", shorttitle, this.size, this.classification, sys_spec);
        } else {
            return String.format("%s (%s %s-class planet, %s, %s)", shorttitle, this.size, this.classification, this.owner.term(Term.QUALIFIER), sys_spec);
        }
    }

    @Override
    public String getShortTitle() {
        return this.name;
    }

    @Override
    public void own(Species new_owner) {
        this.disown();
        if(new_owner!=null) {
            this.owner = new_owner;
            this.owner.registerPlanet(this);
        }
    }

    @Override
    public void disown() {
        if(this.owner!=null) {
            this.owner = null;
            this.owner.deregisterPlanet(this);
        }
    }

    public int compareTo(Planet p) {
        return this.name.compareTo(p.getName());
    }
}
