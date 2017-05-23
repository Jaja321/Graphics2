
public class Plane extends Surface {
	Vector normal;
	float offset;
	
	public Plane(Vector normal, float offset, Material material){
		super(material);
		this.normal = normal;
		this.offset = offset;
	}
	
	public Plane(float x, float y, float z, int offset, Material material){
		this(new Vector(x,y,z),offset, material);
	}
	
	public boolean RayIntersect(Ray ray){
		double t = getIntersectionT(ray);
		
		if((t >= 0) && (t < ray.getT())){
			ray.setT(t);
			ray.setClosestObject(this);
		}
		
		return true;
	}
	
	public double getIntersectionT(Ray ray){
		float denominator = Vector.dot(ray.getDir(), this.normal);
		if(denominator == 0){
			return -1;
		}
		float numerator = -(Vector.dot(ray.getOrigin(),this.normal) + this.offset);
		
		double t = numerator/denominator;
		if(t < 0){
			return -1;
		}
		
		return t;
	}

}
