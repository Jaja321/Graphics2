
public class Camera {
	Vector position, direction, up;
	float screenDistance, screenWidth, screenHeight;
	
	public Camera(Vector position, Vector lookat, Vector up, float screenDistance, float screenWidth, float screenHeight) {
		this.position = position;
		this.direction= Vector.subtract(lookat, position);
		this.up = up; //Need to fix up vector.
		this.screenDistance = screenDistance;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
	}
	
	
	
}
