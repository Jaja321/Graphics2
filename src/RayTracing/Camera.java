package RayTracing;

public class Camera {
	private Vector position, direction, up, right, screenOrigin;
	private float screenDistance, screenWidth, screenHeight;

	public Camera(Vector position, Vector lookat, Vector up, float screenDistance, float screenWidth,
			float screenHeight) {
		this.position = position;
		Vector dir = Vector.subtract(lookat, position);
		dir = dir.divide(dir.norm());
		this.direction = dir;
		// Fixing up vector:
		Vector temp = Vector.cross(direction, up);
		temp = Vector.cross(direction, temp);
		// temp = temp.multiply(-1);
		this.up = temp.divide(temp.norm());

		this.screenDistance = screenDistance;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

		// Calculate the bottom-left point of the screen:
		Vector left = Vector.cross(this.up, this.direction);
		this.right = left.multiply(-1);

		Vector down = this.up.multiply(-1);
		screenOrigin = Vector.add(position, direction.multiply(this.screenDistance));
		screenOrigin = Vector.add(screenOrigin, left.multiply(this.screenWidth / 2));
		screenOrigin = Vector.add(screenOrigin, down.multiply(this.screenHeight / 2));
	}
	
	public Vector getPosition() {
		return position;
	}
	public Vector getDirection() {
		return direction;
	}
	public Vector getUp() {
		return up;
	}
	public Vector getRight() {
		return right;
	}
	public Vector getScreenOrigin() {
		return screenOrigin;
	}
	public float getScreenDistance() {
		return screenDistance;
	}
	public float getScreenWidth() {
		return screenWidth;
	}
	public float getScreenHeight() {
		return screenHeight;
	}

	public static Camera parseCamera(String[] params, float aspectRatio) {
		float posX = Float.parseFloat(params[0]);
		float posY = Float.parseFloat(params[1]);
		float posZ = Float.parseFloat(params[2]);
		Vector position = new Vector(posX, posY, posZ);

		float lookAtX = Float.parseFloat(params[3]);
		float lookAtY = Float.parseFloat(params[4]);
		float lookAtZ = Float.parseFloat(params[5]);
		Vector lookAt = new Vector(lookAtX, lookAtY, lookAtZ);

		float upVectorX = Float.parseFloat(params[6]);
		float upVectorY = Float.parseFloat(params[7]);
		float upVectorZ = Float.parseFloat(params[8]);
		Vector upVector = new Vector(upVectorX, upVectorY, upVectorZ);

		float distance = Float.parseFloat(params[9]);
		float width = Float.parseFloat(params[10]);
		float height = width / aspectRatio;

		return new Camera(position, lookAt, upVector, distance, width, height);
	}

	// Need to add super-sampling support.
	public Vector getPixelPosition(int x, int y, int imageWidth, int imageHeight) {
		float pixelHeight = screenHeight / imageHeight;
		float pixelWidth = screenWidth / imageWidth;
		Vector tempUp = up.multiply(pixelHeight * (y + 0.5f));
		Vector tempRight = right.multiply(pixelWidth * (x + 0.5f));
		Vector position = Vector.add(screenOrigin, tempUp);
		position = Vector.add(position, tempRight);
		
		return position;
	}

}