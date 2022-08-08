package math;

public class Vec3 extends DataType{

	public Vec3(float x, float y, float z) {
		super(new float[] {x,y,z});
	}
	public Vec3(float[] comp) {
		super(comp);
	}
	public Vec3(Vec4 vec) {
		super(new float[] {vec.getX(), vec.getY(), vec.getZ()});
	}
	public Vec3(Vec3 vec) {
		super(new float[] {vec.getX(),vec.getY(), vec.getZ()});
	}
	public Vec3 cross(Vec3 vec) {
		return new Vec3(new float[] {
				this.getY()*vec.getZ()-this.getZ()*vec.getY(),
				this.getZ()*vec.getX()-this.getX()*vec.getZ(),
				this.getX()*vec.getY()-this.getY()*vec.getX()
		});
	}
	public float dot(Vec3 vec) {
		return (this.getX()*vec.getX()+this.getY()*vec.getY()+this.getZ()*vec.getZ());
	}
	public Vec3 normalize() {
		this.mult(1f/length());
		return this;
	}
	public float length() {
		return (float) Math.sqrt(lengthSq());
	}
	public float lengthSq() {
		return data[0]*data[0]+data[1]*data[1]+data[2]*data[2];
	}
	public static Vec3 SUB(Vec3 vec1, Vec3 vec2) {
		return new Vec3(new float[] {
				vec1.data[0]-vec2.data[0],
				vec1.data[1]-vec2.data[1],
				vec1.data[2]-vec2.data[2]
		});
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
	public Vec3 clone() {
		return new Vec3(this.data.clone()) {	};
	}
	public static Vec3 MULT(Vec3 v1, float scalar) {
		Vec3 out = v1.clone();
		out.mult(scalar);
		return out;
	}
	public Vec3 add(Vec3 vec) {
		super.add(vec);
		return this;
	}


}
