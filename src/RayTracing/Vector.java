package RayTracing;

public class Vector {
	private float x, y, z;

	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return this.x;
	}
	public float getY() {
		return this.y;
	}
	public float getZ() {
		return this.z;
	}

	// Return the eucledian norm of the vector.
	public float norm() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	// Return the sum of vectors a and b.
	public static Vector add(Vector a, Vector b) {
		return new Vector(a.x + b.x, a.y + b.y, a.z + b.z);
	}
	
	// adds scalar to this vector.
	public Vector add(float scalar) {
		return new Vector(scalar + this.x, scalar + this.y, scalar + this.z);
	}

	// Return the subtraction of vectors a and b.
	public static Vector subtract(Vector a, Vector b) {
		return new Vector(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	// Multiply this vector with a scalar.
	public Vector multiply(float scalar) {
		return new Vector(scalar * x, scalar * y, scalar * z);
	}

	// Divide this vector with a scalar.
	public Vector divide(float scalar) {
		return this.multiply(1 / scalar);
	}

	// Multiply vectors a and b component-wise.
	public static Vector multiply(Vector a, Vector b) {
		return new Vector(a.x * b.x, a.y * b.y, a.z * b.z);
	}

	// Return the dot product of vectors a and b.
	public static float dot(Vector a, Vector b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	// Return the cross product of vectors a and b.
	public static Vector cross(Vector a, Vector b) {
		return new Vector(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
	}

	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Vector)) {
			return false;
		}
		Vector other = (Vector) obj;
		return ((this.x == other.x) && (this.y == other.y) && (this.z == other.z));
	}

}
