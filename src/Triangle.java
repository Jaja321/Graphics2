
public class Triangle extends Surface {
	Vector vertex1, vertex2, vertex3;
	Plane trianglePlane;
	
	//constructor using 3 vectors
	public Triangle(Vector vertex1, Vector vertex2, Vector vertex3, Material material){
		super(material);
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.vertex3 = vertex3;
		this.calculatePlane();
	}
	//constructor using 3 (x,y,z) coordinates representing vectors
	public Triangle(float x1, float y1, float z1, float x2, float y2, 
			float z2, float x3, float y3, float z3, Material material){
		
		this(new Vector(x1, y1, z1), new Vector(x2, y2, z2), new Vector(x3, y3, z3), material);
	}
	
	private void calculatePlane(){
		Vector u = Vector.subtract(this.vertex2, this.vertex1);
		Vector v = Vector.subtract(this.vertex3, this.vertex1);
		Vector normal = Vector.cross(u, v);
		float offset = Vector.dot(normal, this.vertex1);
		this.trianglePlane = new Plane(normal, -offset, this.getMaterial());
	}
	
	public boolean RayIntersect(Ray ray){
		double t = (this.trianglePlane).getIntersectionT(ray);
		if (t < 0){
			return false;
		}

		Vector intersectPoint = ray.getRayVector(t);
		if(!this.isPointInTriangle(intersectPoint)){
			return false;
		}
		
		if(t < ray.getT()){
			ray.setT(t);
			ray.setClosestObject(this);
		}
		
		return true;
		
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

}
