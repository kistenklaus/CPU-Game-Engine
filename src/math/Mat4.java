package math;

public class Mat4 extends DataType{
	
	public Mat4(float[] data) {
		super(data);
	}
	public Mat4(Vec4 i, Vec4 j, Vec4 k, Vec4 l) {
		super(new float[] {
				i.getX(),j.getX(),k.getX(),l.getX(),
				i.getY(),j.getY(),k.getY(),l.getY(),
				i.getZ(),j.getZ(),k.getZ(),l.getZ(),
				i.getW(),j.getW(),k.getW(),l.getW()
		});
	}
	
	public Vec4 mult(Vec4 vec) {
		float[] res = new float[4];
		float[] vec_comp = vec.getData();
		for(int y = 0; y < 4; y++) {
			float comp = 0;
			for(int x = 0; x < 4; x++) {
				comp+=get(x, y)*vec_comp[x];
			}
			res[y] = comp;
		}
		return new Vec4(res);
	}
	
	public Mat4 mult(Mat4 mat4) {
		Vec4[] unitVectors = new Vec4[4];
		for(int i = 0; i < 4; i++) {
			float[] comp = new float[4];
			for(int j = 0; j < 4; j++) {
				comp[j] = mat4.get(i, j);
			}
			unitVectors[i] = new Vec4(comp);
		}
		return new Mat4(mult(unitVectors[0]), mult(unitVectors[1]), mult(unitVectors[2]), mult(unitVectors[3]));
	}
	
	public float get(int x, int y) {
		return this.data[y*4+x];
	}
}
