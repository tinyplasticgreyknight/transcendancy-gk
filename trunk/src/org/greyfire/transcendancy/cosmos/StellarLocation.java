package org.greyfire.transcendancy.cosmos;

import org.greyfire.transcendancy.bio.*;

import java.util.ArrayList;

public abstract class StellarLocation extends StellarObject implements Comparable<StellarLocation> {
    
    protected ArrayList<SystemObject> survey = new ArrayList<SystemObject>();

    public StellarLocation(String name, Coord position, Species owner) {
        super(name, position, owner);
    }

    public int addObject(SystemObject o) {
        int i;
        for(i=0; i<survey.size(); i++) {
            if(survey.get(i).equals(o)) {
                return i;
            }
        }
        if(!survey.add(o)) throw new RuntimeException("cannot add system object to list");
        return survey.size()-1;
    }
    public SystemObject findObject(int index) {
        return survey.get(index);
    }
    public int findObject(SystemObject o) {
        return survey.indexOf(o);
    }
    public SystemObject removeObject(int index) {
        SystemObject target = survey.get(index);
        survey.remove(index);
        return target;
    }
    public SystemObject removeObject(SystemObject o) {
        return this.removeObject(this.findObject(o));
    }
    public void orbit() {
        for(SystemObject o : survey) {
            o.orbit();
        }
    }
    public int numObjects() {
        return this.survey.size();
    }

    @Override
    public void own(Species new_owner) {
        this.disown();
        if(new_owner!=null) {
            this.owner = new_owner;
            this.owner.registerSystem(this);
        }
    }

    @Override
    public void disown() {
        if(this.owner!=null) {
            this.owner = null;
            this.owner.deregisterSystem(this);
        }
    }

    public int compareTo(StellarLocation s) {
        return this.name.compareTo(s.getName());
    }
}
