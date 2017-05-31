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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (!(obj instanceof Surface)){
			return false;
		}
		Surface other = (Surface) obj;
		return (materialIndex == other.materialIndex);
	}
	
	@Override
	public String toString() {
		return ", materialIndex=" + materialIndex;
	}
	
	public abstract RayHit RayIntersect(Ray ray);


}
