package math;

public class Vec4 extends DataType{

	public Vec4(float[] data) {
		super(data);
	}
	public Vec4(float x, float y, float z, float w) {
		super(new float[] {x,y,z,w});
	}
	public Vec4(Vec3 vec, float w) {
		super(new float[] {vec.getX(), vec.getY(),vec.getZ(),w});
	}
	public float getX() {
		return data[0];
	}
	public float getY() {
		return data[1];
	}
	public float getZ() {
		return data[2];
	}
	public float getW() {
		return data[3];
	}

}
