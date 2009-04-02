package org.greyfire.transcendancy.cosmos;

public class Coord {
	private long x = 0;
	private long y = 0;
	private long z = 0;
	
	private double r = 0;
	private double theta = 0;
	private double phi = 0;

	public Coord(long x, long y, long z) {
		this.set(x, y, z);
	}
	public Coord(double r, double theta, double phi) {
		this.set(r, theta, phi);
	}
	
	public void set(long x, long y, long z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.r = Math.sqrt(x*x+y*y+z*z);
		if(r==0) {
			this.theta = 0;
			this.phi   = 0;
		} else {
			this.theta = Math.atan2(y, x) + Math.PI;
			this.phi   = Math.acos(z / r);
		}
	}
	
	public void set(double r, double theta, double phi) {
		this.r     = r;
		this.theta = theta;
		this.phi   = phi;
		
		double sin_phi = Math.sin(phi);
		
		this.x = Math.round(r * Math.cos(theta) * sin_phi);
		this.y = Math.round(r * Math.sin(theta) * sin_phi);
		this.z = Math.round(r * Math.cos(phi));
	}

	public long x() { return this.x; }
	public long y() { return this.y; }
	public long z() { return this.z; }

	public double r()     { return this.r;     }
	public double theta() { return this.theta; }
	public double phi()   { return this.phi;   }
	public long distanceTo(Coord p2) {
		long dx = this.x - p2.x();
		long dy = this.y - p2.y();
		long dz = this.z - p2.z();
		return Math.round(Math.sqrt(dx*dx+dy*dy+dz*dz));
	}
}