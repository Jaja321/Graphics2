import java.awt.Color;

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
	
	
}
