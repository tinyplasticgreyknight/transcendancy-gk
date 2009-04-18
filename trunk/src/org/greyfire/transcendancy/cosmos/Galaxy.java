package org.greyfire.transcendancy.cosmos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.greyfire.transcendancy.Util;

public class Galaxy {

    private ArrayList<StellarLocation> anchors = new ArrayList<StellarLocation>();
    private ArrayList<Starlane> lanes = new ArrayList<Starlane>();

    private HashMap<String, StellarLocation> anchor_map = new HashMap<String, StellarLocation>();
    private HashMap<String, HashMap<String, Starlane>> lane_map = new HashMap<String, HashMap<String, Starlane>>();
    
    private ArrayList<ArrayList<Double>> anchor_distances = new ArrayList<ArrayList<Double>>();
    private boolean[][] link = new boolean[1][1];
    private boolean[][] reachable = new boolean[1][1];
    private ArrayList<Integer> connections = new ArrayList<Integer>();
    
    public double view_theta = 0.0;
    public double view_phi   = 0.0;
    public Boolean view_update = true;
    
    public Galaxy() {
    }

    public ArrayList<StellarLocation> anchors() { return anchors; }
    public ArrayList<Starlane> lanes() { return lanes; }
    public int addAnchor(StellarLocation o) {
        int i, j;
        double delta_tolerance = 1.0;
        Coord newpos = o.getPosition();
        Coord oldpos;
        for(i=0; i<anchors.size(); i++) {
            if(anchors.get(i).equals(o)) {
                return i;
            } else {
                oldpos = anchors.get(i).getPosition();
                if(oldpos.distance(newpos)<=delta_tolerance) {
                    throw new IllegalStateException("an anchor already exists at the same position");
                }
            }
        }
        String name = o.getName();
        if(anchor_map.get(name)!=null)
            throw new IllegalArgumentException(String.format("an anchor by the name \"%s\" already exists", name));
        if(!anchors.add(o)) throw new RuntimeException("cannot add anchor to list");
        anchor_map.put(name, o);
        int n = anchors.size();
        ArrayList<Double> newdistances = new ArrayList<Double>();
        double dist;
        for(i=0; i<n-1; i++) {
            dist = this.anchors.get(i).getPosition().distance(o.getPosition());
            this.anchor_distances.get(i).add(dist);
            newdistances.add(dist);
        }
        newdistances.add(0.0);
        this.anchor_distances.add(newdistances);
        this.connections.add(0);
        boolean[][] newlink = new boolean[n][n];
        boolean[][] newreachable = new boolean[n][n];
        for(i=0; i<n; i++) {
            for(j=0; j<n; j++) {
                if(i==n-1 && j==n-1) {
                    newlink[i][j]      = true;
                    newreachable[i][j] = true;
                } else if(i==n-1 || j==n-1) {
                    newlink[i][j]      = false;
                    newreachable[i][j] = false;
                } else {
                    newlink[i][j]      = this.link[i][j];
                    newreachable[i][j] = this.reachable[i][j];
                }
            }
        }
        this.link      = newlink;
        this.reachable = newreachable;
        return anchors.size()-1;
    }
    public int addLane(Starlane L) {
        int k;
        for(k=0; k<lanes.size(); k++) {
            if(lanes.get(k).equals(L)) {
                return k;
            }
        }
        String nameA = L.getAnchorA().getName();
        String nameB = L.getAnchorB().getName();
        Integer i = this.getAnchorIndex(nameA);
        Integer j = this.getAnchorIndex(nameB);
        if(i==null) throw new IllegalArgumentException(String.format("no such anchor as \"%s\"", nameA));
        if(j==null) throw new IllegalArgumentException(String.format("no such anchor as \"%s\"", nameB));
        HashMap<String, Starlane> temp_map = lane_map.get(nameA);
        if(temp_map!=null && temp_map.get(nameB)!=null)
            throw new IllegalArgumentException(String.format("a starlane from \"%s\" to \"%s\" already exists", nameA, nameB));
        HashMap<String, Starlane> temp_map_inv = lane_map.get(nameB);
        if(temp_map_inv!=null && temp_map_inv.get(nameA)!=null)
            throw new IllegalArgumentException(String.format("a starlane from \"%s\" to \"%s\" already exists", nameB, nameA));
        if(!lanes.add(L)) throw new RuntimeException("cannot add starlane to list");
        if(temp_map==null) {
            temp_map = new HashMap<String, Starlane>();
        }
        temp_map.put(nameB, L);
        lane_map.put(nameA, temp_map);
        int a, b;
        int n = this.anchors.size();
        this.link[i][j] = true;
        this.link[j][i] = true;
        this.reachable[j][i] = true;
        this.reachable[i][j] = true;
        for(a=0; a<n; a++) {
            for(b=0; b<n; b++) {
                if(!this.reachable[a][b]) {
                    if((this.reachable[a][i] && this.reachable[j][b]) || (this.reachable[a][j] && this.reachable[i][b])) {
                        this.reachable[a][b] = true;
                        this.reachable[b][a] = true;
                    }
                }
            }
        }
        this.connections.set(i, this.connections.get(i)+1);
        this.connections.set(j, this.connections.get(j)+1);
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
    
    public Integer getAnchorIndex(String name) {
        for(int i=0; i<anchors.size(); i++) {
            if(anchors.get(i).getName().equals(name)) {
                return new Integer(i);
            }
        }
        return null;
    }
    public boolean anchorsLinkedP(Integer index1, Integer index2) {
        if(index1==null || index2==null) return false;
        return this.link[index1][index2];
    }
    public boolean anchorsReachableP(Integer index1, Integer index2) {
        if(index1==null || index2==null) return false;
        return this.reachable[index1][index2];
    }

    /**
     * Try to create enough lanes to make the galaxy fully-connected.
     * Ideally the lanes will be at most preferred_maximum in length; retry up
     * to max_retries times if the random selection does not meet this
     * criterion.  If we still cannot satisfy the criterion, create anyway but
     * make it a redlink.
     */
    private Random stream_lanes = new Random(Util.seed());
    public void addRandomLanes(long preferred_maximum, int max_retries) {
        int i, j, k;
        StellarLocation A = null;
        StellarLocation B = null;
        Starlane L;
        if(max_retries<1) throw new IllegalArgumentException("max_retries must be positive");
        int n = this.anchors.size();
        boolean finished = false;
        ArrayList<Integer> preferred_set = new ArrayList<Integer>();
        ArrayList<Double> dist;
        do {
            for(k=0; k<n; k++) {
                finished = reachable[0][k];
                if(!finished) break;
            }
            if(finished) return;
            
            do { i = stream_lanes.nextInt(n); } while(this.connections.get(i) >= ((Star)anchors.get(i)).maxConnections());
            preferred_set.clear();
            dist = this.anchor_distances.get(i);
            for(k=0; k<dist.size(); k++) {
                if((k!=i) && (!link[k][i]) && (dist.get(k)<=preferred_maximum) && (this.connections.get(k) < ((Star)anchors.get(k)).maxConnections() )) {
                    preferred_set.add(k);
                }
            }
            A = anchors.get(i);
            Coord posA = A.getPosition();
            if(preferred_set.size()<1) {
                j = -1;
                for(k=0; k<max_retries; k++) {
                    do { j = stream_lanes.nextInt(n); } while(i==j || link[i][j] || this.connections.get(j) >= ((Star)anchors.get(j)).maxConnections() );
                    B = anchors.get(j);
                    if(posA.distanceInt(B.getPosition()) <= preferred_maximum) {
                        break;
                    }
                }
            } else {
                j = preferred_set.get(stream_lanes.nextInt(preferred_set.size()));
                B = anchors.get(j);
            }
            if(anchor_distances.get(i).get(j) > preferred_maximum) {
                L = new Redlink(null, null, A, B);
            } else {
                L = new Starlane(null, null, A, B);
            }
            this.addLane(L);
        } while(!finished);
    }
}
