
public class Sphere extends Surface {
	Vector center;
	float radius;
	Vector NormalAtIntersect = null;
	
	public Sphere(Vector center, float radius, int index, Material material){
		super(index, material);
		this.center = center;
		this.radius = radius;
	}
	
	public Sphere(Vector center, float radius, int index){
		this(center, radius, index, null);
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

	public static Sphere paresSphere(String[] params) {
		float centerX = Float.parseFloat(params[0]);
		float centerY = Float.parseFloat(params[1]);
		float centerZ = Float.parseFloat(params[2]);
		Vector center = new Vector(centerX, centerY, centerZ);
		
		float radius = Float.parseFloat(params[3]);
		int materialIndex = Integer.parseInt(params[4])-1;

		return new Sphere(center,radius, materialIndex);
	}

}
