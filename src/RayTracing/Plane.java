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
		double t = getIntersectionT(ray);

		if (t > 0) {
			Vector intersectionNormal;
			if(Vector.dot(this.normal, ray.getDir())>0)
				intersectionNormal = this.normal;
			else{
				intersectionNormal = this.normal.multiply(-1);
			}
			Vector intersectionPoint = ray.getRayVector(t);
			return new RayHit(t, this, intersectionNormal, intersectionPoint);
		} else {
			return null;
		}
	}

	public double getIntersectionT(Ray ray) {
		float denominator = Vector.dot(ray.getDir(), this.normal);
		if (denominator == 0) {
			return -1;
		}
		float numerator = -Vector.dot(ray.getOrigin(), this.normal) + this.offset;

		double t = numerator / denominator;
		if (t < 0) {
			return -1;
		}

		return t;
	}

	public static Plane paresPlane(String[] params) {
		float normalX = Float.parseFloat(params[0]);
		float normalY = Float.parseFloat(params[1]);
		float normalZ = Float.parseFloat(params[2]);
		Vector normal = new Vector(normalX, normalY, normalZ);
		float offset = Float.parseFloat(params[3]);
		int materialIndex = Integer.parseInt(params[4]) - 1;

		return new Plane(normal, offset, materialIndex);
	}

}
