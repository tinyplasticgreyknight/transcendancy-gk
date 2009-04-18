package org.greyfire.transcendancy.cosmos;

import org.greyfire.transcendancy.bio.*;

public abstract class StellarObject {
    protected String name;
    protected Coord  position;
    protected Species owner;
    
    public StellarObject(String name, Coord position, Species owner) {
        this.name     = name;
        this.position = position;
        this.own(owner);
    }

    public abstract void own(Species new_owner);
    public abstract void disown();
    public abstract String getShortTitle();
    public abstract String getLongTitle();
    
    public void setPosition(Coord position) { this.position = position; }
    public Coord getPosition() { return position; }
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
}
