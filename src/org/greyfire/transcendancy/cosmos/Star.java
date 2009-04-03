package org.greyfire.transcendancy.cosmos;

import org.greyfire.transcendancy.bio.*;
import org.greyfire.transcendancy.bio.Species.Term;

public class Star extends StellarLocation {
	
	public enum Classification {
		/* TODO: load this data from resources */
		BLUE_GIANT         ("Blue Giant"),
		BLACK_HOLE         ("Black Hole"),
		RED_GIANT          ("Red Giant"),
		ORANGE_GIANT       ("Orange Giant"),
		BLUE_MEDIUM        ("Blue Medium"),
		WHITE_MEDIUM       ("White Medium"),
		YELLOW_MEDIUM      ("Yellow Medium"),
		BINARY_WHITE_DWARF ("Binary White Dwarf"),
		BROWN_DWARF        ("Brown Dwarf"),
		PULSAR             ("Pulsar"),
		BLUE_DWARF         ("Blue Dwarf"),
		WHITE_DWARF        ("White Dwarf"),
		RED_DWARF          ("Red Dwarf");
		
		private String name;
		private Classification(String name) { this.name = name; }
		public String toString() { return this.name; }
	}
	
	protected Classification classification;

	public Star(String name, Coord position, Species owner, Classification classification) {
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
			return String.format("%s (%s system, %s)", shorttitle, this.classification, this.owner.term(Term.QUALIFIER));
		}
	}

	@Override
	public String getShortTitle() {
		return this.name;
	}

}
