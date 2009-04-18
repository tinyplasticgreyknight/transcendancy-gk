package org.greyfire.transcendancy.cosmos;

import org.greyfire.transcendancy.bio.Species;
import org.greyfire.transcendancy.ui.ArtCanvas;

public class Redlink extends Starlane {

    protected static long SLUGGISHNESS = 3;
    protected static int colour = ArtCanvas.translated_colour(255, 64, 64);

    public Redlink(String name, Coord position, Species owner, StellarLocation anchorA, StellarLocation anchorB) {
        super(name, position, owner, anchorA, anchorB);
    }

    public Redlink(String name, Species owner, StellarLocation anchorA, StellarLocation anchorB) {
        super(name, owner, anchorA, anchorB);
    }
    
    public long getLength() { return (physical_length * SLUGGISHNESS); }

    public void painter(ArtCanvas canvas, int u, int v, int u2, int v2) {
        canvas.line_safe(u, v, u2, v2, colour);
    }

}
