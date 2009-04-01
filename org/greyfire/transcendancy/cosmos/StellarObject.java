package org.greyfire.transcendancy.cosmos;

public abstract class StellarObject {
	protected String name;
	protected Coord  position;
	protected String owner;
	
	public StellarObject(String name, Coord position, String owner) {
		this.name     = name;
		this.position = position;
		this.owner    = owner;
	}

	public abstract String getShortTitle();
	public abstract String getLongTitle();
	
	public void setPosition(Coord position) { this.position = position; }
	public Coord getPosition() { return position; }
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public void setOwner(String owner) { this.owner = owner; }
	public String getOwner() { return owner; }
}
