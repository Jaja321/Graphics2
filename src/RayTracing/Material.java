package RayTracing;

public class Material {
	private Color diffuse, specular, reflection;
	private float phong, transparency;
	
	public Material(Color diffuse, Color specular, Color reflection, float phong, float trans) {
		this.diffuse = diffuse;
		this.specular = specular;
		this.reflection = reflection;
		this.phong = phong;
		this.transparency = trans;
	}

	public Color getDiffuse() {
		return diffuse;
	}
	public Color getSpecular() {
		return specular;
	}
	public Color getReflection() {
		return reflection;
	}
	public float getPhong() {
		return phong;
	}
	public float getTransparency() {
		return transparency;
	}


	public static Material parseMaterial(String[] params) {
		try{
			float diffuseR = Float.parseFloat(params[0]);
			float diffuseG = Float.parseFloat(params[1]);
			float diffuseB = Float.parseFloat(params[2]);
			Color diffuseColor = new Color(diffuseR, diffuseG, diffuseB);
			
			float specularR = Float.parseFloat(params[3]);
			float specularG = Float.parseFloat(params[4]);
			float specularB = Float.parseFloat(params[5]);
			Color specularColor = new Color(specularR, specularG, specularB);
			
			float reflectionR = Float.parseFloat(params[6]);
			float reflectionG = Float.parseFloat(params[7]);
			float reflectionB = Float.parseFloat(params[8]);
			Color reflectionColor = new Color(reflectionR, reflectionG, reflectionB);
			
			float phongCoeff = Float.parseFloat(params[9]);
			float transparency = Float.parseFloat(params[10]);
			
			return new Material(diffuseColor, specularColor, reflectionColor, phongCoeff, transparency);

		}catch (NumberFormatException e){
			return null;
		}catch (ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
}
