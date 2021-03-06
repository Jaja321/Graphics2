package RayTracing;

public class Triangle extends Surface {
	private Vector vertex1, vertex2, vertex3;
	private Plane trianglePlane;
	
	public Triangle(Vector vertex1, Vector vertex2, Vector vertex3, int index, Material material){
		super(index, material);
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.vertex3 = vertex3;
		this.calculatePlane();
	}

	public Triangle(Vector vertex1, Vector vertex2, Vector vertex3, int index){
		this(vertex1, vertex2, vertex3, index, null);
	}
	
	private void calculatePlane(){
		Vector u = Vector.subtract(this.vertex2, this.vertex1);
		Vector v = Vector.subtract(this.vertex3, this.vertex1);
		Vector normal = Vector.cross(u, v);
		normal = normal.divide(normal.norm());
		float offset = Vector.dot(normal, this.vertex1);
		this.trianglePlane = new Plane(normal, offset, this.getMaterialIndex(), this.getMaterial());
	}
	
	public RayHit RayIntersect(Ray ray){
		RayHit hit = this.trianglePlane.RayIntersect(ray);
		if(hit == null){
			return null;
		}
		
		Vector intersectPoint = hit.getIntersectionPoint();
		if(!this.isPointInTriangle(intersectPoint)){
			return null;
		}
		
		return new RayHit(hit.getDist(), this, hit.getIntersectionNormal(), intersectPoint);
	}
	
	public boolean isPointInTriangle(Vector point){
		Vector u = Vector.subtract(this.vertex2, this.vertex1);
		Vector v = Vector.subtract(this.vertex3, this.vertex1);
		Vector w = Vector.subtract(point, this.vertex1);
		
		double uu = Vector.dot(u, u);
		double vv = Vector.dot(v, v);
		double uv = Vector.dot(u, v);
		double wu = Vector.dot(w, u);
		double wv = Vector.dot(w, v);
		
		double numeratorA = uv*wv - vv*wu;
		double numeratorB = uv*wu - uu*wv;
		double denominator = uv*uv - uu*vv;

		double alpha = numeratorA/denominator;		
		double beta = numeratorB/denominator;
		if((alpha < 0) || (beta < 0) || (alpha + beta > 1)){
			return false;
		}

		return true;
	}
	public static Triangle paresTriangle(String[] params) {
		try{
			float vertex1X = Float.parseFloat(params[0]);
			float vertex1Y = Float.parseFloat(params[1]);
			float vertex1Z = Float.parseFloat(params[2]);
			Vector vertex1 = new Vector(vertex1X, vertex1Y, vertex1Z);
	
			float vertex2X = Float.parseFloat(params[3]);
			float vertex2Y = Float.parseFloat(params[4]);
			float vertex2Z = Float.parseFloat(params[5]);
			Vector vertex2 = new Vector(vertex2X, vertex2Y, vertex2Z);
	
			float vertex3X = Float.parseFloat(params[6]);
			float vertex3Y = Float.parseFloat(params[7]);
			float vertex3Z = Float.parseFloat(params[8]);
			Vector vertex3 = new Vector(vertex3X, vertex3Y, vertex3Z);
		
			int materialIndex = Integer.parseInt(params[9])-1;
			return new Triangle(vertex1, vertex2, vertex3, materialIndex);

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
		if (!(obj instanceof Triangle)) {
			return false;
		}
		
		Triangle other = (Triangle) obj;
		if ((this.trianglePlane == null) && (other.trianglePlane != null)) {
			return false;
		}
		if ((this.vertex1 == null) && (other.vertex1 != null)) {
			return false;
		}
		if ((this.vertex2 == null) && (other.vertex2 != null)) {
			return false;
		}
		if ((this.vertex3 == null) && (other.vertex3 != null)) {
			return false;
		}
		return ((this.trianglePlane.equals(other.trianglePlane)) && (this.vertex1.equals(other.vertex1))
				&& (this.vertex2.equals(other.vertex2)) && (this.vertex3.equals(other.vertex3)));

	}

	@Override
	public String toString() {
		return "Triangle [vertex1=" + vertex1 + ", vertex2=" + vertex2 + ", vertex3=" + vertex3 + super.toString() + "]";
	}

}
