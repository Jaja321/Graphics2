
public abstract class Surface {
	private Material material;
	
	public Surface(Material material){
		this.material = material;
	}
	
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public abstract boolean RayIntersect(Ray ray);
	
}
