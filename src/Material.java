import java.awt.Color;

public class Material {
	Color diffuse, specular, reflection;
	float phong, transparency;
	
	public Material(Color diffuse, Color specular, Color reflection, float phong, float trans) {
		this.diffuse = diffuse;
		this.specular = specular;
		this.reflection = reflection;
		this.phong = phong;
		this.transparency = trans;
	}
	
}
