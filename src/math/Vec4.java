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
	public Vec4 clone() {
		return new Vec4(this.data.clone());
	}
	public Vec3 vec3() {
		return new Vec3(this);
	}
	public Vec4 add(Vec4 v) {
		this.add(v);
		return this;
	}
	public static Vec4 SUB(Vec4 vec1, Vec4 vec2) {
		return new Vec4(new float[] {
				vec1.data[0]-vec2.data[0],
				vec1.data[1]-vec2.data[1],
				vec1.data[2]-vec2.data[2],
				vec1.data[3]-vec2.data[3]
		});
	}
	
	public static Vec4 MULT(Vec4 v1, float scalar) {
		Vec4 res = v1.clone();
		res.mult(scalar);
		return res;
		
	}
}
