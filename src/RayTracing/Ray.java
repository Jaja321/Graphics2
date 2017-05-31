package RayTracing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ray {
	private static final float EPSILON = .0001f;
	private Vector origin;
	private Vector dir;
	
	Ray(Vector origin, Vector dir){
		this.origin = Vector.add(origin, dir.multiply(EPSILON));
		this.dir = dir;
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

	public Vector getDir() {
		return dir;
	}

}
