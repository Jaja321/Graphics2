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
	
	public Color getDiffuseColor(){
		return this.material.getDiffuse();
	}
	public abstract boolean RayIntersect(Ray ray);
	
}
