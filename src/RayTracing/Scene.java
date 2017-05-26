package RayTracing;

import java.util.ArrayList;
import java.util.List;

public class Scene {
	private List<Material> materials = new ArrayList<Material>();
	private List<Surface> surfaces = new ArrayList<Surface>();
	private List<Light> lights = new ArrayList<Light>();
	private Camera camera = null;
	private Color background = null;
	private int shadowRays = -1;
	private int maxRecursion = -1;
	private int superSampling = -1;
	
	public void setProperties(String[] params){
		float colorR = Float.parseFloat(params[0]);
		float colorG = Float.parseFloat(params[1]);
		float colorB = Float.parseFloat(params[2]);
		this.background = new Color(colorR, colorG, colorB);
		
		this.shadowRays = Integer.parseInt(params[3]);
		this.maxRecursion = Integer.parseInt(params[4]);
		this.superSampling = Integer.parseInt(params[5]);
	}

	public List<Material> getMaterials() {
		return materials;
	}
	public void addMaterial(Material material) {
		this.materials.add(material);
	}
	public List<Surface> getSurfaces() {
		return surfaces;
	}
	public void addSurface(Surface surface) {
		this.surfaces.add(surface);
	}
	public List<Light> getLights() {
		return lights;
	}
	public void addLight (Light light) {
		this.lights.add(light);
	}
	public Camera getCamera() {
		return camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	public Color getBackground() {
		return background;
	}
	public void setBackground(Color background) {
		this.background = background;
	}
	public int getShadowRays() {
		return shadowRays;
	}
	public void setShadowRays(int shadowRays) {
		this.shadowRays = shadowRays;
	}
	public int getMaxRecursion() {
		return maxRecursion;
	}
	public void setMaxRecursion(int maxRecursion) {
		this.maxRecursion = maxRecursion;
	}
	public Material getSurfaceMaterial(int materialNum){
		return this.materials.get(materialNum);
	}

	public boolean successfulParse() {
		boolean result = setMaterial();
		result = (result && checkParams());
		
		return result;
	}
	
	private boolean setMaterial(){
		for(Surface surface : this.surfaces){
			int materialIndex = surface.getMaterialIndex();
			if(materialIndex < 0 || materialIndex > this.materials.size()){
				return false;
			}
			Material material = getSurfaceMaterial(materialIndex);
			surface.setMaterial(material);
		}
		return true;
	}
	
	private boolean checkParams(){
		if(this.camera == null 
				|| this.background == null 
				|| this.shadowRays == -1
				|| this. maxRecursion == -1 
				|| this.superSampling == -1
				|| this.materials.size() == 0
				|| this.surfaces.size() == 0
				|| this.lights.size() == 0){
			return false;
		}
		
		return true;
	}
}
