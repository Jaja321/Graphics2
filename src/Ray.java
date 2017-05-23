
public class Ray {
	private Vector origin;
	private Vector dir;
	private double t;
	private Surface closestObject;
	private Vector intersectionNormal;
	
	Ray(Vector origin, Vector dir){
		this.origin = origin;
		this.dir = dir;
		this.t = Double.MAX_VALUE;
		this.closestObject = null;
		this.intersectionNormal = null;
	}
	
	Ray(float x, float y, float z, Vector dir){
		this(new Vector(x,y,z), dir);
	}
	
	public Vector getRayVector(){
		return this.getRayVector(this.t);
	}
	
	public Vector getRayVector(double t){
		if(t == Double.MAX_VALUE){
			return null;
		}
		Vector tV = (this.dir).multiply((float)this.t);
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

	public double getT() {
		return t;
	}

	public void setT(double t) {
		this.t = t;
	}

	public Surface getClosestObject() {
		return closestObject;
	}

	public void setClosestObject(Surface closestObject) {
		this.closestObject = closestObject;
	}

	public Vector getIntersectionNormal() {
		return intersectionNormal;
	}

	public void setIntersectionNormal(Vector intersectionNormal) {
		this.intersectionNormal = intersectionNormal;
	}
	
	
}
