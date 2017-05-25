
public class Camera {
	Vector position, direction, up;
	float screenDistance, screenWidth;
	
	public Camera(Vector position, Vector lookat, Vector up, float screenDistance, float screenWidth) {
		this.position = position;
		this.direction= Vector.subtract(lookat, position);
		//Fixing up vector:
		Vector temp=Vector.cross(direction, up);
		temp=Vector.cross(direction, temp);
		temp=temp.multiply(-1);
		this.up = temp.divide(temp.norm());
		
		this.screenDistance = screenDistance;
		this.screenWidth = screenWidth;
	}
	
	public static Camera parseCamera(String[] params){
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
		
		return new Camera(position, lookAt, upVector, distance, width);
	}
	
	
	
}