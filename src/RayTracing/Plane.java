package RayTracing;

public class Plane extends Surface {
	private Vector normal;
	private float offset;

	public Plane(Vector normal, float offset, int index, Material material) {
		super(index, material);
		this.normal = normal;
		this.offset = offset;
	}

	public Plane(Vector normal, float offset, int index) {
		this(normal, offset, index, null);
	}

	public RayHit RayIntersect(Ray ray) {
		float denominator = Vector.dot(ray.getDir(), this.normal);
		if (denominator == 0) {
			return null;
		}
		float numerator = -Vector.dot(ray.getOrigin(), this.normal) + this.offset;

		double t = numerator / denominator;
		if (t <= 0) {
			return null;
		}

		Vector intersectionNormal;
		if(Vector.dot(this.normal, ray.getDir()) > 0){
			intersectionNormal = this.normal;
		}else{
			intersectionNormal = this.normal.multiply(-1);
		}
		intersectionNormal = intersectionNormal.divide(intersectionNormal.norm());
		Vector intersectionPoint = ray.getRayVector(t);
		return new RayHit(t, this, intersectionNormal, intersectionPoint);
	}

	public static Plane paresPlane(String[] params) {
		try{
			float normalX = Float.parseFloat(params[0]);
			float normalY = Float.parseFloat(params[1]);
			float normalZ = Float.parseFloat(params[2]);
			Vector normal = new Vector(normalX, normalY, normalZ);
			float offset = Float.parseFloat(params[3]);
			int materialIndex = Integer.parseInt(params[4]) - 1;
	
			return new Plane(normal, offset, materialIndex);
		}catch (NumberFormatException e){
			return null;
		}catch (ArrayIndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Plane)) {
			return false;
		}
		Plane other = (Plane) obj;
		if (normal == null && other.normal != null) {
				return false;
		}
		
		return (this.normal.equals(other.normal) && this.offset == other.offset);
	}

	@Override
	public String toString() {
		return "Plane [normal=" + normal + ", offset=" + offset + super.toString() + "]";
	}

}
