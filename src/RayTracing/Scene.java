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

	public void addMaterial(Material material) {
		this.materials.add(material);
	}
	public void addSurface(Surface surface) {
		this.surfaces.add(surface);
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
	public Material getSurfaceMaterial(int materialNum){
		return this.materials.get(materialNum);
	}
	
	public Color getColor(Ray ray) {
		List<RayHit> intersections = ray.rayIntersections(this.surfaces);

		//ray didn't hit anything, return background
		if (intersections.size() == 0){
			return this.background;
		}		

		Color color = getSurfaceColor(ray, intersections, this.maxRecursion);
		
		return color;
	}
	
	private Color getSurfaceColor(Ray ray, List<RayHit> surfaces, int level){
		if ((this.maxRecursion - level) > surfaces.size()-1){
			return this.background;
		}
		
		RayHit intersect = surfaces.get(this.maxRecursion - level);
		Surface surface = intersect.getSurface();
		Color totalColor = Color.getBlack();
					
		//calculate transparency (background) color
		if (surface.getTransparency() > 0){
			Color backgroundColor;
			if(level == 0){
				backgroundColor = this.background;
			}else{
				backgroundColor = getSurfaceColor(ray, surfaces, level-1);
			}
			backgroundColor = backgroundColor.multiplyColor(surface.getTransparency());
			totalColor = totalColor.addColor(backgroundColor);
		}

		//calculate diffuse and specular
		for (Light light : this.lights) {
			Color lightColor = Color.getBlack();

			Vector lightDirection = Vector.subtract(light.getPosition(), ray.getRayVector(intersect.getDist()));
			float lightDistance = lightDirection.norm();
			lightDirection = lightDirection.divide(lightDistance);
			Color lightDiffuse = surface.getDiffuse().multiplyColor(light.getColor());
			float cos = Math.abs(Vector.dot(lightDirection, intersect.getIntersectionNormal()));
			lightDiffuse = lightDiffuse.multiplyColor(cos);
			lightColor = lightColor.addColor(lightDiffuse);

			float temp = Vector.dot(lightDirection, intersect.getIntersectionNormal());
			Vector lightReflection = intersect.getIntersectionNormal().multiply(temp);
			lightReflection = lightReflection.multiply(2);
			lightReflection = Vector.subtract(lightReflection, lightDirection);
			Vector viewDirection = ray.getDir().multiply(-1);
			cos = Math.abs(Vector.dot(lightReflection, viewDirection));
			Color lightSpecular = surface.getSpecular().multiplyColor(light.getColor());
			lightSpecular = lightSpecular.multiplyColor((float) Math.pow(cos, surface.getPhong()));
			lightSpecular = lightSpecular.multiplyColor(light.getSpecular());
			lightColor = lightColor.addColor(lightSpecular);
			
			lightColor.multiplyColor(1-surface.getTransparency());

			// Check if light is obstructed and add shadows:
			Ray lightRay = new Ray(ray.getRayVector(intersect.getDist()), lightDirection);
			boolean intersection = false;
			for (Surface surf : this.surfaces) {
				RayHit lightHit = surf.RayIntersect(lightRay);
				if (lightHit != null) {
					float intersectDistance = (float) lightHit.getDist();
					if (intersectDistance < lightDistance) {
						intersection = true;
						break;
					}
				}
			}
			if (intersection){
				lightColor = lightColor.multiplyColor(1 - light.getShadow());
			}
			
			totalColor = totalColor.addColor(lightColor);
		}
		
		//calculate reflection color
		Color materialReflection = surface.getReflection();
		if(!materialReflection.equals(Color.getBlack())){
			Color reflectionColor;
			if(level == this.maxRecursion-1){
				reflectionColor = this.background;
			}else{
				Vector reflectionDirection = surface.getReflectionVector(ray.getDir(),
						intersect.getIntersectionNormal());
				Ray reflectionRay = new Ray(ray.getRayVector(intersect.getDist()), reflectionDirection);	
				List<RayHit> ReflectionIntersections = reflectionRay.rayIntersections(this.surfaces);
				reflectionColor = getSurfaceColor(reflectionRay, ReflectionIntersections, level-1);
				reflectionColor.multiplyColor(surface.getReflection());
			}
			totalColor.addColor(reflectionColor);
		}
		
		return totalColor;
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
