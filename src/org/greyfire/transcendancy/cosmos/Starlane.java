package org.greyfire.transcendancy.cosmos;

import java.awt.Color;
import org.greyfire.transcendancy.bio.*;
import org.greyfire.transcendancy.bio.Species.Term;
import org.greyfire.transcendancy.ui.ArtCanvas;

public class Starlane extends StellarObject {

    protected StellarObject anchorA;
    protected StellarObject anchorB;
    protected long physical_length;
    protected static int colour = ArtCanvas.translated_colour(64, 64, 255);
    
    public Starlane(String name, Coord position, Species owner, StellarLocation anchorA, StellarLocation anchorB) {
        super(name, position, owner);
        this.initialise(anchorA, anchorB);
    }
    
    public Starlane(String name, Species owner, StellarLocation anchorA, StellarLocation anchorB) {
        super(name, new Coord(0.0,0.0,0.0), owner);
        this.initialise(anchorA, anchorB);
    }
    
    private void initialise(StellarLocation anchorA, StellarLocation anchorB) {
        this.anchorA = anchorA;
        this.anchorB = anchorB;
        physical_length = anchorA.getPosition().distanceInt(anchorB.getPosition());
    }

    @Override
    public String getLongTitle() {
        String shorttitle = this.getShortTitle();
        if(owner==null) return shorttitle;
        return String.format("%s (%s)", shorttitle, owner.term(Term.QUALIFIER));
    }

    @Override
    public String getShortTitle() {
        return String.format("%s--%s starlane", anchorA.getName(), anchorB.getName());
    }

    @Override
    public void own(Species new_owner) {
        this.disown();
        this.owner = new_owner;
        /* TODO: register */
    }

    @Override
    public void disown() {
        if(this.owner!=null) {
            this.owner = null;
            /* TODO: deregister */
        }
    }

    public void setAnchorA(StellarObject anchorA) { this.anchorA = anchorA; }
    public void setAnchorB(StellarObject anchorB) { this.anchorB = anchorB; }
    public StellarObject getAnchorA() { return anchorA; }
    public StellarObject getAnchorB() { return anchorB; }
    public long getLength() { return physical_length; }
    
    public boolean equals(Object o) {
        if(o instanceof Starlane) {
            Starlane t = (Starlane)o;
            if(t.getAnchorA().equals(anchorA) && t.getAnchorB().equals(anchorB)) {
                return true;
            }
        }
        return false;
    }

    public void painter(ArtCanvas canvas, int u, int v, int u2, int v2) {
        canvas.line_safe(u, v, u2, v2, colour);
    }

    public void painter(ArtCanvas canvas, int u, int v, int u2, int v2, boolean traderoutes) {
        if(traderoutes) {
            if(owner==null) {
                canvas.line_safe(u, v, u2, v2, ArtCanvas.WHITE);
            } else {
                canvas.line_safe(u, v, u2, v2, owner.colour_translated());
            }
        } else {
            this.painter(canvas, u, v, u2, v2);
        }
    }

}
