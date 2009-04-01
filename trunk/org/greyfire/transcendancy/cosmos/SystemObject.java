package org.greyfire.transcendancy.cosmos;

public abstract class SystemObject {
	protected String name;
	protected Coord  position;
	protected Coord  orbital_parameters;
	protected String owner;
	
	/**
	 * @param name
	 * @param position The initial position of the object.
	 * @param orbital_parameters Fields should be: (r)=instantaneous linear velocity in cubes/tick, (theta)=azimuth of highest point on orbit, (phi)=altitude of said point.  Set (r) to zero if you are not orbiting.
	 * @param owner
	 */
	public SystemObject(String name, Coord position, Coord orbital_parameters, String owner) {
		this.name     = name;
		this.position = position;
		this.owner    = owner;
		double r = orbital_parameters.r();
		double q = orbital_parameters.theta();
		double f = orbital_parameters.phi();
		while(q<0) q += Math.PI;
		this.orbital_parameters = new Coord(r, q, f);
		this.initialise();
	}

	public abstract String getShortTitle();
	public abstract String getLongTitle();
	
	public void setPosition(Coord position) { this.position = position; }
	public Coord getPosition() { return position; }
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public void setOwner(String owner) { this.owner = owner; }
	public String getOwner() { return owner; }
	
	public void initialise() {
		if(orbital_parameters.r()==0) return;
		double q = position.theta();
		position.set(position.r(), q, Math.cos(q - orbital_parameters.theta()) * orbital_parameters.phi());
	}
	
	public void orbit() {
		if(orbital_parameters.r()==0) return;
		double r = position.r();
		double q = position.theta() + (orbital_parameters.r()/r);
		position.set(r, q, Math.cos(q - orbital_parameters.theta()) * orbital_parameters.phi());
	}
}
