package RayTracing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ray {
	private static final float EPSILON = 1/10000;
	private Vector origin;
	private Vector dir;
	
	Ray(Vector origin, Vector dir){
		this.origin = origin.add(EPSILON);
		this.dir = dir;
	}
	
	Ray(float x, float y, float z, Vector dir){
		this(new Vector(x,y,z), dir);
	}
	
	public List<RayHit> rayIntersections(List<Surface> surfaces){
		
		List<RayHit> intersections = new ArrayList<RayHit>();
		
		for (Surface surface : surfaces){
			RayHit hit = surface.RayIntersect(this);		
			if (hit != null){
				intersections.add(hit);
			}
		}		
		Collections.sort(intersections);
		return intersections;

	}
	
	public Vector getRayVector(double t){
		Vector tV = this.dir.multiply((float)t);
		Vector RayVector = Vector.add(this.origin, tV);
		return RayVector;
	}

	public Vector getOrigin() {
		return origin;
	}

	public void setOrigin(Vector origin) {
		this.origin = origin;
	}

	public Vector getDir() {
		return dir;
	}

	public void setDir(Vector dir) {
		this.dir = dir;
	}
	

	@Override
	public String toString() {
		return "Ray [origin=" + origin + ", dir=" + dir + "]";
	}	
	
}
