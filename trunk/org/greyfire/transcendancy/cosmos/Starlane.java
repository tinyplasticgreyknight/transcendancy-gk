package org.greyfire.transcendancy.cosmos;

import org.greyfire.transcendancy.bio.*;

public class Starlane extends StellarObject {

	protected StellarObject anchorA;
	protected StellarObject anchorB;
	protected long physical_length;
	
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
		physical_length = anchorA.getPosition().distanceTo(anchorB.getPosition());
	}

	@Override
	public String getLongTitle() {
		String shorttitle = this.getShortTitle();
		if(owner==null) return shorttitle;
		return String.format("%s (%s)", shorttitle, owner.toString());
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

}
