package RayTracing;

public class RayHit implements Comparable<RayHit>{
	private double dist;
	private Surface surface;
	private Vector intersectionNormal;
	private Vector intersectionPoint;
	
	RayHit(double dist, Surface surface, Vector normal, Vector intersection){
		this.dist = dist;
		this.surface = surface;
		this.intersectionNormal = normal;
		this.intersectionPoint = intersection;
	}
	
	public double getDist() {
		return dist;
	}
	public Surface getSurface() {
		return surface;
	}
	public Vector getIntersectionNormal() {
		return intersectionNormal;
	}
	public Vector getIntersectionPoint() {
		return intersectionPoint;
	}


	@Override
	public int compareTo(RayHit ray) {
		if(this.dist < ray.dist){
			return -1;
		}else if(this.dist == ray.dist){
			return 0;
		}else{
			return 1;
		}
	}

	@Override
	public String toString() {
		return "RayHit [dist=" + dist + "]";
	}
	
	
	
}
