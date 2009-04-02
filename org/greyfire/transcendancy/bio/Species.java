package org.greyfire.transcendancy.bio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.greyfire.transcendancy.cosmos.*;

public class Species {
	private String name;
	private String plural;

	private HashMap<String, Vessel> fleet   = new HashMap<String, Vessel>();
	private HashMap<String, Planet> planets = new HashMap<String, Planet>();
	private HashMap<String, StellarLocation> systems = new HashMap<String, StellarLocation>();

	public Species(String name, String plural) {
		this.name   = name;
		this.setPlural(plural);
	}
	public Species(String name) {
		this.name   = name;
		this.setPlural(name + "s");
	}

	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	public void setPlural(String plural) { this.plural = plural; }
	public String getPlural() { return plural; }
	
	public void registerVessel(Vessel vessel) {
		String name = vessel.getName();
		if(fleet.get(name)!=null) {
			throw new IllegalArgumentException(String.format("a vessel named \"%s\" already exists in the %s fleet", name, this.plural));
		}
		fleet.put(name, vessel);
	}
	public void deregisterVessel(Vessel vessel) {
		fleet.remove(vessel.getName());
	}
	public Vessel findVessel(String name) {
		return fleet.get(name);
	}
	public ArrayList<Vessel> getSortedFleet() {
		ArrayList<Vessel> F = new ArrayList<Vessel>();
		for(Vessel v : fleet.values()) {
			F.add(v);
		}
		Collections.sort(F);
		return F;
	}

	public void registerPlanet(Planet planet) {
		String name = planet.getName();
		if(planets.get(name)!=null) {
			throw new IllegalArgumentException(String.format("a planet named \"%s\" already exists in the %s empire", name, this.plural));
		}
		planets.put(name, planet);
	}
	public void deregisterPlanet(Planet planet) {
		planets.remove(planet.getName());
	}
	public Planet findPlanet(String name) {
		return planets.get(name);
	}
	public ArrayList<Planet> getSortedPlanets() {
		ArrayList<Planet> F = new ArrayList<Planet>();
		for(Planet p : planets.values()) {
			F.add(p);
		}
		Collections.sort(F);
		return F;
	}

	public void registerSystem(StellarLocation system) {
		String name = system.getName();
		if(systems.get(name)!=null) {
			throw new IllegalArgumentException(String.format("a system named \"%s\" already exists in the %s empire", name, this.plural));
		}
		systems.put(name, system);
	}
	public void deregisterSystem(StellarLocation system) {
		systems.remove(system.getName());
	}
	public StellarLocation findSystem(String name) {
		return systems.get(name);
	}
	public ArrayList<StellarLocation> getSortedSystems() {
		ArrayList<StellarLocation> F = new ArrayList<StellarLocation>();
		for(StellarLocation p : systems.values()) {
			F.add(p);
		}
		Collections.sort(F);
		return F;
	}
	
	public String toString() { return this.plural; }
}
