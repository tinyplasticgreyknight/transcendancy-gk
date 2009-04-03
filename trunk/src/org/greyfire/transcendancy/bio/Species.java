package org.greyfire.transcendancy.bio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.greyfire.transcendancy.cosmos.*;

public class Species {
	
	public enum Term {
		SINGULAR   ("singular"),
		PLURAL     ("plural"),
		COLLECTIVE ("collective"),
		QUALIFIER  ("qualifier"),
		HOMEWORLD  ("homeworld");
		
		private String name;
		private Term(String name) { this.name = name; }
		public String toString() { return this.name; }
	}
	
	private String name;
	public static ArrayList<String> names = new ArrayList<String>();
	private static HashMap<String, Species> bestiary = new HashMap<String, Species>();
	
	private HashMap<String, String> terms = new HashMap<String, String>();

	private HashMap<String, Vessel> fleet   = new HashMap<String, Vessel>();
	private HashMap<String, Planet> planets = new HashMap<String, Planet>();
	private HashMap<String, StellarLocation> systems = new HashMap<String, StellarLocation>();

	private Species(String name, HashMap<String, String> terms) {
		if(name==null) throw new IllegalArgumentException("name cannot be null");
		this.name = name;
		this.initialise(name);
		for(String key : terms.keySet()) {
			this.setTerm(key, terms.get(key));
		}
	}
	private Species(String name) {
		if(name==null) throw new IllegalArgumentException("name cannot be null");
		this.name = name;
		this.initialise(name);
	}
	
	public static boolean add(Species S, String name) {
		if(name==null) throw new IllegalArgumentException("name cannot be null");
		if(bestiary.get(name)!=null) return false;
		bestiary.put(name, S);
		names.add(name);
		return true;
	}
	
	public static Species Instance(String name) {
		Species S = bestiary.get(name);
		if(S==null) {
			S = new Species(name);
			Species.add(S, S.name);
		}
		return S;
	}
	
	public static Species Instance(String name, HashMap<String, String> terms) {
		Species S = bestiary.get(name);
		String used_name = name;
		if(name==null || S==null) {
			used_name = terms.get(Term.COLLECTIVE);
			if(used_name==null) {
				used_name = name;
			} else {
				S = bestiary.get(used_name);
			}
		}
		if(S==null) {
			S = new Species(used_name, terms);
		}
		Species.add(S, S.name);
		return S;
	}
	
	public void initialise(String name) {
		this.name   = name;
		this.setTerm(Term.SINGULAR,   name);
		this.setTerm(Term.PLURAL,     name);
		this.setTerm(Term.COLLECTIVE, name);
		this.setTerm(Term.QUALIFIER,  name);
		this.setTerm(Term.HOMEWORLD,  String.format("%s Homeworld", term(Term.QUALIFIER)));
	}

	public void setTerm(String key, String name) { this.terms.put(key, name); }
	public void setTerm(Term key, String name) { this.terms.put(key.toString(), name); }
	public String getName() { return name; }
	public String term(String key) {
		return this.terms.get(key);
	}
	public String term(Term key) {
		return this.terms.get(key.toString());
	}
	
	public void registerVessel(Vessel vessel) {
		String name = vessel.getName();
		if(fleet.get(name)!=null) {
			throw new IllegalArgumentException(String.format("a vessel named \"%s\" already exists in the %s fleet", name, term(Term.QUALIFIER)));
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
			throw new IllegalArgumentException(String.format("a planet named \"%s\" already exists in the %s empire", name, term(Term.QUALIFIER)));
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
			throw new IllegalArgumentException(String.format("a system named \"%s\" already exists in the %s empire", name, term(Term.QUALIFIER)));
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
	
	public String toString() { return term(Term.COLLECTIVE); }
}
