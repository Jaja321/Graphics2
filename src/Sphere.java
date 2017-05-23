
public class Sphere extends Surface {
	Vector center;
	float radius;
	Vector NormalAtIntersect = null;
	
	public Sphere(Vector center, float radius, Material material){
		super(material);
		this.center = center;
		this.radius = radius;
	}
	
	public Sphere(float x, float y, float z, float radius, Material material){
		this(new Vector(x,y,z),radius,material);
	}
	
	public boolean RayIntersect(Ray ray){

		Vector vectorL = Vector.subtract(this.center, ray.getOrigin());
		float tca = Vector.dot(vectorL, ray.getDir());
		if (tca < 0){
			return false;
		}
		float dSquare = Vector.dot(vectorL, vectorL) - (tca*tca);
		if(dSquare > this.radius*this.radius){
			return false;
		}
		double thc = Math.sqrt(this.radius*this.radius - dSquare);
		
		double t1 = (tca - thc);
		double t2 = (tca + thc);
		double t;
		//since thc and tca are positive, only t1 can be negative
		if(t1 < 0){
			t = t2;
		}else{
			t = Math.min(t1, t2);
		}
		
		if(t < ray.getT()){
			ray.setT(t);
			ray.setClosestObject(this);
			Vector RayVector = ray.getRayVector();
			Vector intersectionNormal = this.calculateNormal(RayVector);
			ray.setIntersectionNormal(intersectionNormal);
		}
		return true;
	}
	
	public Vector calculateNormal(Vector ray){
		Vector diff = Vector.subtract(ray,this.center);
		Vector normal = diff.divide(diff.norm());
		return normal;		
	}
}
