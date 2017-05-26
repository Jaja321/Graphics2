package RayTracing;

public class Light {
	Vector position;
	Color color;
	float specular; //Specular intensity.
	float shadow; //Shadow intensity.
	float radius;
	
	public Light(Vector position, Color color, float specular, float shadow, float radius) {
		this.position = position;
		this.color = color;
		this.specular = specular;
		this.shadow = shadow;
		this.radius = radius;
	}
	
	public static Light parseLight(String[] params) {
		float posX = Float.parseFloat(params[0]);
		float posY = Float.parseFloat(params[1]);
		float posZ = Float.parseFloat(params[2]);
		Vector position = new Vector(posX, posY, posZ);
		
		float colorR = Float.parseFloat(params[3]);
		float colorG = Float.parseFloat(params[4]);
		float colorB = Float.parseFloat(params[5]);
		Color color = new Color(colorR, colorG, colorB);
		
		float specularIntensity = Float.parseFloat(params[6]); 
		float shadowIntensity = Float.parseFloat(params[7]); 
		float radius = Float.parseFloat(params[8]);

		return new Light(position, color, specularIntensity, shadowIntensity, radius);
	}
	
}
