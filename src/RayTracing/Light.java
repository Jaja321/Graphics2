package RayTracing;

public class Light {
	private Vector position;
	private Color color;
	private float specular; //Specular intensity.
	private float shadow; //Shadow intensity.
	private float radius;
	
	public Light(Vector position, Color color, float specular, float shadow, float radius) {
		this.position = position;
		this.color = color;
		this.specular = specular;
		this.shadow = shadow;
		this.radius = radius;
	}	
	
	public Vector getPosition() {
		return position;
	}

	public Color getColor() {
		return color;
	}

	public float getSpecular() {
		return specular;
	}

	public float getShadow() {
		return shadow;
	}

	public float getRadius() {
		return radius;
	}

	public static Light parseLight(String[] params) {
		try{		
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
		}catch (NumberFormatException e){
			return null;
		}catch (ArrayIndexOutOfBoundsException e){
			return null;
		}


	}
	
}
