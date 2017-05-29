package RayTracing;

public class Color {
	private float R, G, B;
	
	Color(float red, float green, float blue){
		this.R = Math.max(0, Math.min(red, 1));
		this.G = Math.max(0, Math.min(green, 1));;
		this.B = Math.max(0, Math.min(blue, 1));;
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
	
	public static Color getBlack(){
		return new Color(0,0,0);
	}
	
	public Color addColor(Color other){
		float newR = Math.min(this.R + other.R, 1);
		float newG = Math.min(this.G + other.G, 1);
		float newB = Math.min(this.B + other.B, 1);
		
		return new Color(newR, newG, newB);
	}
	
	public Color multiplyColor(Color other){
		return new Color(this.R * other.R, this.G * other.G, this.B * other.B);
	}
	
	public Color multiplyColor(float num){
		float newR = Math.min(this.R * num, 1);
		float newG = Math.min(this.G * num, 1);
		float newB = Math.min(this.B * num, 1);
		
		return new Color(newR, newG, newB);
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

	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (!(obj instanceof Color)) {
	    	return false;
	    }
	    
		Color other = (Color) obj;
		return (this.R == other.R && this.G == other.G && this.B == other.B);
	}

}
