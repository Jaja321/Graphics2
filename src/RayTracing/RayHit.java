package RayTracing;

public class RayHit implements Comparable<RayHit>{
	private double dist;
	private Surface surface;
	private Vector intersectionNormal; 
	
	RayHit(double dist, Surface surface, Vector normal){
		this.dist = dist;
		this.surface = surface;
		this.intersectionNormal = normal;
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
