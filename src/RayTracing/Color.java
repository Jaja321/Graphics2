package RayTracing;

public class Color {
	private float R, G, B;
	
	Color(float red, float green, float blue){
		this.R = red;
		this.G = green;
		this.B = blue;
	}

	public float getRed() {
		return this.R;
	}
	public float getGreen() {
		return this.G;
	}
	public float getBlue() {
		return this.B;
	}
	
	public Color addColor(Color other){
		return new Color(this.R + other.R, this.G + other.G, this.B + other.B);
	}
	
	public Color multiplyColor(Color other){
		return new Color(this.R * other.R, this.G * other.G, this.B * other.B);
	}
	
	public Color multiplyColor(float num){
		return new Color(this.R * num, this.G * num, this.B * num);
	}

	public byte getRedByte() {
		if (this.R > 1){
			return (byte) 255;
		}else{
			return (byte) (this.R * 255);
		}
	}
	public byte getGreenByte() {
		if (this.G > 1){
			return (byte) 255;
		}else{
			return (byte) (this.G * 255);
		}
	}
	public byte getBlueByte() {
		if (this.B > 1){
			return (byte) 255;
		}else{
			return (byte) (this.B * 255);
		}
	}

}
