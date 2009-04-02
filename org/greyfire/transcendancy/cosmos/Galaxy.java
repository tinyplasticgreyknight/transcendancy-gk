package org.greyfire.transcendancy.cosmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Galaxy {

	private ArrayList<StellarLocation> anchors = new ArrayList<StellarLocation>();
	private ArrayList<Starlane> lanes = new ArrayList<Starlane>();

	private HashMap<String, StellarLocation> anchor_map = new HashMap<String, StellarLocation>();
	private HashMap<String, HashMap<String, Starlane>> lane_map = new HashMap<String, HashMap<String, Starlane>>();
	
	public Galaxy() {
	}
	
	public int addAnchor(StellarLocation o) {
		int i;
		for(i=0; i<anchors.size(); i++) {
			if(anchors.get(i).equals(o)) {
				return i;
			}
		}
		String name = o.getName();
		if(anchor_map.get(name)!=null)
			throw new IllegalArgumentException(String.format("an anchor by the name \"%s\" already exists", name));
		if(!anchors.add(o)) throw new RuntimeException("cannot add anchor to list");
		anchor_map.put(name, o);
		return anchors.size()-1;
	}
	public int addLane(Starlane L) {
		int i;
		for(i=0; i<lanes.size(); i++) {
			if(lanes.get(i).equals(L)) {
				return i;
			}
		}
		String nameA = L.getAnchorA().getName();
		String nameB = L.getAnchorB().getName();
		HashMap<String, Starlane> temp_map = lane_map.get(nameA);
		if(temp_map!=null && temp_map.get(nameB)!=null)
			throw new IllegalArgumentException(String.format("a starlane from \"%s\" to \"%s\" already exists", nameA, nameB));
		if(!lanes.add(L)) throw new RuntimeException("cannot add starlane to list");
		if(temp_map==null) {
			temp_map = new HashMap<String, Starlane>();
		}
		temp_map.put(nameB, L);
		lane_map.put(nameA, temp_map);
		return lanes.size()-1;
	}
	public StellarLocation findAnchor(String name) {
		return anchor_map.get(name);
	}
	public Starlane findLane(String nameA, String nameB) {
		HashMap<String, Starlane> temp_map = lane_map.get(nameA);
		Starlane L;
		if(temp_map!=null) {
			L = temp_map.get(nameB);
			if(L!=null) return L;
		}
		temp_map = lane_map.get(nameB);
		if(temp_map!=null) {
			L = temp_map.get(nameA);
			if(L!=null) return L;
		}
		return null;
	}
	public List<Starlane> findLanesAnchored(String location_name) {
		HashMap<String, Starlane> temp_map = lane_map.get(location_name);
		ArrayList<Starlane> L = new ArrayList<Starlane>();
		if(temp_map!=null) {
			for(Starlane elem : temp_map.values()) {
				L.add(elem);
			}
		}
		for(String nameA : lane_map.keySet()) {
			temp_map = lane_map.get(nameA);
			for(String nameB : temp_map.keySet()) {
				if(nameB.equals(location_name)) {
					L.add(temp_map.get(nameB));
				}
			}
		}
		return L;
	}
}
