package math;

public class Vec2 extends DataType{

	public Vec2(float x, float y) {
		super(new float[]{x,y});
	}
	public Vec2(float[] comp) {
		super(comp);
	}
	public float getX() {
		return data[0];
	}
	public float getY() {
		return data[1];
	}
	public Vec2 add(Vec2 v) {
		this.add(v);
		return this;
	}
	public Vec2 clone() {
		return new Vec2(this.data.clone());
	}
	
	public static Vec2 MULT(Vec2 v1, float scalar) {
		Vec2 res = v1.clone();
		res.mult(scalar);
		return res;
		
	}

}
