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
	
	public abstract RayHit RayIntersect(Ray ray);
	
}
