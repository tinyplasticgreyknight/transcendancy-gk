package org.greyfire.transcendancy.cosmos;


public class Planet extends SystemObject {
	
	public enum Classification {
		CATHEDRAL   ("Cathedral"),
		CHAPEL      ("Chapel"),
		HUSK        ("Husk"),
		EDEN        ("Eden"),
		CORNUCOPIA  ("Cornucopia");
		
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

	public Planet(String name, Coord position, Coord orbital_parameters, String owner, Size size, Classification classification) {
		super(name, position, orbital_parameters, owner);
		this.size = size;
		this.classification = classification;
	}

	public void setClassification(Classification classification) { this.classification = classification; }
	public Classification getClassification() { return classification; }
	public void setSize(Size size) { this.size = size; }
	public Size getSize() { return size; }

	@Override
	public String getLongTitle() {
		String shorttitle = this.getShortTitle();
		if(owner==null) {
			return String.format("%s (%s %s-class planet)", shorttitle, this.size, this.classification);
		} else {
			return String.format("%s (%s %s-class planet, %s)", shorttitle, this.size, this.classification, this.owner);
		}
	}

	@Override
	public String getShortTitle() {
		return this.name;
	}

}
