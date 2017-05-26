package RayTracing;

public abstract class Surface {
	private Material material;
	private int materialIndex;
	
	public Surface(int index){
		this.materialIndex = index;
	}
	
	public Surface(int index, Material material){
		this(index);
		this.material = material;
	}
	
	public int getMaterialIndex(){
		return this.materialIndex;
	}
	
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public Color getDiffuse() {
		return this.material.getDiffuse();
	}
	public Color getSpecular() {
		return this.material.getSpecular();
	}
	public Color getReflection() {
		return this.material.getReflection();
	}
	public float getPhong() {
		return this.material.getPhong();
	}
	public float getTransparency() {
		return this.material.getTransparency();
	}
	
	public Vector getReflectionVector(Vector light, Vector normal) { 
		Vector light2 = light.multiply(2);
		Vector LN2 = Vector.multiply(light2,normal);
		Vector multi = Vector.multiply(LN2, normal);
		Vector R = Vector.subtract(multi, light);
		return R; // R = (2L*N)N - L
	}

	public abstract RayHit RayIntersect(Ray ray);
	
}
