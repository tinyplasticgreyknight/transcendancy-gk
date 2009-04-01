package org.greyfire.transcendancy.cosmos;

public class Star extends StellarObject {
	
	public enum Classification {
		YELLOW_DWARF("yellow dwarf"),
		RED_DWARF   ("red dwarf"),
		WHITE_DWARF ("white dwarf"),
		YELLOW_GIANT("yellow giant"),
		RED_GIANT   ("red giant"),
		BLUE_GIANT  ("blue giant"),
		BLACK_HOLE  ("black hole"),
		NEUTRON_STAR("neutron star"),
		BROWN_DWARF ("brown dwarf");
		
		private String name;
		private Classification(String name) { this.name = name; }
		public String toString() { return this.name; }
	}
	
	protected Classification classification;

	public Star(String name, Coord position, String owner, Classification classification) {
		super(name, position, owner);
		this.setClassification(classification);
	}

	public void setClassification(Classification classification) { this.classification = classification; }
	public Classification getClassification() { return classification; }

	@Override
	public String getLongTitle() {
		String shorttitle = this.getShortTitle();
		if(owner==null) {
			return String.format("%s (%s system)", shorttitle, this.classification);
		} else {
			return String.format("%s (%s system, %s)", shorttitle, this.classification, this.owner);
		}
	}

	@Override
	public String getShortTitle() {
		return this.name;
	}

}
