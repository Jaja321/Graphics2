package RayTracing;

public class Sphere extends Surface {
	private Vector center;
	private float radius;
	
	public Sphere(Vector center, float radius, int index, Material material){
		super(index, material);
		this.center = center;
		this.radius = radius;
	}
	
	public Sphere(Vector center, float radius, int index){
		this(center, radius, index, null);
		}
		
	public RayHit RayIntersect(Ray ray){
		
		Vector vectorL = Vector.subtract(this.center, ray.getOrigin());
		float tca = Vector.dot(vectorL, ray.getDir());
		if (tca < 0){
			return null;
		}
		float dSquare = Vector.dot(vectorL, vectorL) - (tca*tca);
		if(dSquare > this.radius*this.radius){
			return null;
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
		if(t==0){
			return null;
		}

		Vector intersectionPoint = ray.getRayVector(t);
		Vector intersectionNormal = this.calculateNormal(intersectionPoint);
		
		return new RayHit(t, this, intersectionNormal, intersectionPoint);
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Sphere)) {
			return false;
		}
		Sphere other = (Sphere) obj;
		if (center == null && other.center != null) {
				return false;
		}
		return (this.center.equals(other.center)) && (this.radius==other.radius);
	}

	@Override
	public String toString() {
		return "Sphere [center=" + center + ", radius=" + radius + super.toString() + "]";
	}

}
