package org.greyfire.transcendancy.cosmos;

import org.greyfire.transcendancy.bio.Species;

public class Redlink extends Starlane {

	protected static long SLUGGISHNESS = 3;

	public Redlink(String name, Coord position, Species owner, StellarLocation anchorA, StellarLocation anchorB) {
		super(name, position, owner, anchorA, anchorB);
	}

	public Redlink(String name, Species owner, StellarLocation anchorA, StellarLocation anchorB) {
		super(name, owner, anchorA, anchorB);
	}
	
	public long getLength() { return (physical_length * SLUGGISHNESS); }

}
