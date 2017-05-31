package RayTracing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Scene {
	private List<Material> materials = new ArrayList<Material>();
	private List<Surface> surfaces = new ArrayList<Surface>();
	private List<Light> lights = new ArrayList<Light>();
	private Camera camera = null;
	private Color background = null;
	private int shadowRays = -1;
	private int maxRecursion = -1;
	private int superSampling = -1;

	public void setProperties(String[] params) {
		try{		
			float colorR = Float.parseFloat(params[0]);
			float colorG = Float.parseFloat(params[1]);
			float colorB = Float.parseFloat(params[2]);
			this.background = new Color(colorR, colorG, colorB);
	
			this.shadowRays = Integer.parseInt(params[3]);
			this.maxRecursion = Integer.parseInt(params[4]);
			this.superSampling = Integer.parseInt(params[5]);

		}catch (NumberFormatException e){
			return;
		}catch (ArrayIndexOutOfBoundsException e){
			return;
		}

	}

	public void addMaterial(Material material) {
		this.materials.add(material);
	}

	public void addSurface(Surface surface) {
		this.surfaces.add(surface);
	}

	public void addLight(Light light) {
		this.lights.add(light);
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Material getSurfaceMaterial(int materialNum) {
		return this.materials.get(materialNum);
	}

	public Color getPixelColor(int x, int y, int imageWidth, int imageHeight) {
		float redSum = 0;
		float greenSum = 0;
		float blueSum = 0;
		int N = this.superSampling;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				Vector pixelPos = this.camera.getPixelPosition(x, y, imageWidth, imageHeight, N, i, j);
				Vector rayDir = Vector.subtract(pixelPos, this.camera.getPosition());
				rayDir = rayDir.divide(rayDir.norm());
				Ray ray = new Ray(this.camera.getPosition(), rayDir);
				Color color = getColor(ray);
				redSum += color.getRed();
				greenSum += color.getGreen();
				blueSum += color.getBlue();
			}
		}
		return new Color(redSum / (N * N), greenSum / (N * N), blueSum / (N * N));
	}

	public Color getColor(Ray ray) {
		List<RayHit> intersections = ray.rayIntersections(this.surfaces);

		// ray didn't hit anything, return background
		if (intersections.size() == 0) {
			return this.background;
		}

		Color color = getSurfaceColor(ray, intersections.iterator(), this.maxRecursion);

		return color;
	}

	private Color getSurfaceColor(Ray ray, Iterator<RayHit> surfaces, int level) {
		if (!surfaces.hasNext()) {
			return this.background;
		}

		RayHit intersect = surfaces.next();
		Surface surface = intersect.getSurface();
		Color totalColor = Color.getBlack();
		Vector normal = intersect.getIntersectionNormal();
		float normalSign;
		if (Vector.dot(ray.getDir().multiply(-1),normal) >= 0){
			normalSign = 1;
		}else{
			normalSign = -1;
		}

		// calculate transparency (background) color
		if (surface.getTransparency() > 0) {
			Color backgroundColor;
			if (level == 0) {
				backgroundColor = this.background;
			} else {
				backgroundColor = getSurfaceColor(ray, surfaces, level - 1);
			}
			backgroundColor = backgroundColor.multiplyColor(surface.getTransparency());
			totalColor = totalColor.addColor(backgroundColor);
		}
		
		// calculate diffuse and specular color
		if (surface.getTransparency() < 1){
			for (Light light : this.lights) {
	
				Vector lightDirection = Vector.subtract(light.getPosition(), intersect.getIntersectionPoint());
				lightDirection = lightDirection.divide(lightDirection.norm());
				Color lightDiffuse = surface.getDiffuse().multiplyColor(light.getColor());
				float cos = Math.max(Vector.dot(lightDirection, normal.multiply(normalSign)),0);
				lightDiffuse = lightDiffuse.multiplyColor(cos);
				
				Color lightSpecular = surface.getSpecular().multiplyColor(light.getColor());
				lightSpecular = lightSpecular.multiplyColor(light.getSpecular());
				
				float lightNormalSign = (Vector.dot(lightDirection, normal) >= 0) ? 1 : -1;
				Vector speculaReflection = normal.multiply(lightNormalSign);
				speculaReflection = speculaReflection.multiply(2 * Vector.dot(lightDirection, speculaReflection));
				speculaReflection = Vector.subtract(speculaReflection, lightDirection);
				speculaReflection = speculaReflection.divide(speculaReflection.norm());
				
				cos = Math.max(Vector.dot(speculaReflection, ray.getDir().multiply(-1)), 0);
				lightSpecular = lightSpecular.multiplyColor((float) Math.pow(cos, surface.getPhong()));
				
				Color lightColor = lightDiffuse.addColor(lightSpecular);
	
				// Add soft shadows:
				float softShadows = getSoftShadowsValue(light, intersect, this.shadowRays);
				float shadowFactor = softShadows + (1 - light.getShadow()) * (1 - softShadows);
				lightColor = lightColor.multiplyColor(shadowFactor);

				lightColor = lightColor.multiplyColor(1 - surface.getTransparency());	
				totalColor = totalColor.addColor(lightColor);
			}
		}
		
		// calculate reflection color
		Color materialReflection = surface.getReflection();
		if (!materialReflection.equals(Color.getBlack())) {
			Color reflectionColor;
			if (level == 0) {
				reflectionColor = this.background;
			} else {
				Vector viewDirection = ray.getDir().multiply(-1);
				Vector reflectionDirection = normal.multiply(normalSign);
				reflectionDirection = reflectionDirection.multiply(2*Vector.dot(viewDirection, reflectionDirection));
				reflectionDirection = Vector.subtract(reflectionDirection, viewDirection);
				reflectionDirection = reflectionDirection.divide(reflectionDirection.norm());
				 
				Ray reflectionRay = new Ray(intersect.getIntersectionPoint(), reflectionDirection);
				List<RayHit> ReflectionIntersections = reflectionRay.rayIntersections(this.surfaces);
				reflectionColor = getSurfaceColor(reflectionRay, ReflectionIntersections.iterator(), level - 1);
				reflectionColor = reflectionColor.multiplyColor(materialReflection);
			}
			totalColor = totalColor.addColor(reflectionColor);
		}

		return totalColor;
	}

	public float getSoftShadowsValue(Light light, RayHit hitPoint, int N) {
		Vector lightPos = light.getPosition();
		Vector intersectPos = hitPoint.getIntersectionPoint();
		float radius = light.getRadius();
		float pixelSize = radius / N;

		Vector normal = Vector.subtract(lightPos, intersectPos);
		normal = normal.divide(normal.norm());
		Vector temp = new Vector(normal.getX() + 1, normal.getY(), normal.getZ());
		temp = temp.divide(temp.norm());
		Vector up = Vector.cross(normal, temp);
		Vector right = Vector.cross(up, normal);
		Vector origin = Vector.subtract(lightPos, right.multiply(radius / 2f));

		origin = Vector.subtract(origin, up.multiply(radius / 2f));

		Random rand = new Random();
		float count = 0;
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				float rand_x = rand.nextFloat();
				float rand_y = rand.nextFloat();
				Vector upComponent = up.multiply((y + rand_y) * pixelSize);
				Vector rightComponent = right.multiply((x + rand_x) * pixelSize);
				Vector pixelPos = Vector.add(origin, upComponent);

				pixelPos = Vector.add(pixelPos, rightComponent);

				Vector rayDir = Vector.subtract(pixelPos, intersectPos);
				float distance = rayDir.norm();
				rayDir = rayDir.divide(rayDir.norm());
				Ray ray = new Ray(intersectPos, rayDir);

				List<RayHit> hits = ray.rayIntersections(this.surfaces);
				
				if (hits.size() == 0){
					count++;
				}
				else {
					float shadowFactor = -1;
					for (RayHit rayHit : hits){
						if(rayHit.getSurface().equals(hitPoint.getSurface())){
							continue;
						}else if((rayHit.getDist() > distance)) {
							count ++;
							break;
						}else if (rayHit.getSurface().getTransparency() > 0){
							shadowFactor = (shadowFactor == -1 ? 1 : shadowFactor);
							shadowFactor *= rayHit.getSurface().getTransparency();
						}else{
							break;
						}
					}
					count += (shadowFactor == -1 ? 0 : shadowFactor);
				}
			}
		}
		return (count / (N*N));
	}

	public String successfulParse() {
		String ErrorString = "\n";
		ErrorString = ErrorString.concat(setMaterial());
		ErrorString = ErrorString.concat(checkParams());

		return ErrorString;
	}

	private String setMaterial() {
		if(this.materials.size() == 0){
			return "ERROR no materials found for scene!\n";
		}
		String ErrorString = "";
		for (Surface surface : this.surfaces) {
			int materialIndex = surface.getMaterialIndex();
			if (materialIndex < 0 || materialIndex > this.materials.size() - 1) {
				ErrorString = ErrorString.concat("ERROR wrong material index for: " + surface + "\n");
			}else{
				Material material = getSurfaceMaterial(materialIndex);
				surface.setMaterial(material);
			}
		}
		return ErrorString;
	}

	private String checkParams() {
		String ErrorString = "";
		if(this.camera == null){
			ErrorString = ErrorString.concat("ERROR no camera found!\n");
		}
		if(this.background == null){
			ErrorString = ErrorString.concat("ERROR background color not set!\n");
		}
		if( this.shadowRays == -1){
			ErrorString = ErrorString.concat("ERROR number of shadow rays not set!\n");
		}
		if(this.maxRecursion == -1){
			ErrorString = ErrorString.concat("ERROR max recursion level not set!\n");
		}
		if(this.superSampling == -1){
			ErrorString = ErrorString.concat("ERROR super sampling level not set!\n");
		}
		if(this.surfaces.size() == 0){
			ErrorString = ErrorString.concat("ERROR no surfaces found!\n");
		}
		if(this.lights.size() == 0){
			ErrorString = ErrorString.concat("ERROR no lights found!\n");
		}

		return ErrorString;
	}
}
