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

}
