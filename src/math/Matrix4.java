package math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class Matrix4 extends DataType{
	
	public Matrix4(float[] data) {
		super(data);
	}
	public Matrix4(Vec4 i, Vec4 j, Vec4 k, Vec4 l) {
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
	
	public Matrix4 mult(Matrix4 mat4) {
		Vec4[] unitVectors = new Vec4[4];
		for(int i = 0; i < 4; i++) {
			float[] comp = new float[4];
			for(int j = 0; j < 4; j++) {
				comp[j] = mat4.get(i, j);
			}
			unitVectors[i] = new Vec4(comp);
		}
		return new Matrix4(mult(unitVectors[0]), mult(unitVectors[1]), mult(unitVectors[2]), mult(unitVectors[3]));
	}
	
	public float get(int x, int y) {
		return this.data[y*4+x];
	}
	public static Matrix4 perspective(float FOV, float con_width, float con_height, float NearZ, float FarZ) {
		float ta = (float) Math.tan(Math.toRadians(FOV/2f));
		return new Matrix4(new float[] {
			1f/((con_width/con_height)*ta),0,0,0,
			0,1f/ta,0,0,
			0,0,-(NearZ+FarZ)/(NearZ-FarZ),(2*FarZ*NearZ)/(NearZ-FarZ),
			0,0,1,0
//			1,0,0,0,
//			0,1,0,0,
//			0,0,1,0,
//			0,0,0,1
		});
	}
	public static Matrix4 ortho(float left, float right, float bottom, float top, float near, float far) {
		return new Matrix4(new float[] {
			2f/(right-left),0,0,-(right+left)/(right-left),
			0,2f/(top-bottom),0,-(top+bottom)/(top-bottom),
			0,0,-2f/(far-near),-(far+near)/(far-near),
			0,0,0,1
		});
	}
	public static Matrix4 view(Vec3 position, Vec3 rotation) {
		Matrix4 trans = new Matrix4(new float[] {
				1,0,0,position.getX(),
				0,1,0,position.getY(),
				0,0,1,position.getZ(),
				0,0,0,1
		});
		double rx = Math.toRadians(rotation.getX());
		Matrix4 rotX = new Matrix4(new float[] {
				1,0,0,0,
				0,(float) cos(rx),(float) -sin(rx),0,
				0,(float) sin(rx),(float) cos(rx),0,
				0,0,0,1
		});
		double ry = Math.toRadians(rotation.getY());
		Matrix4 rotY = new Matrix4(new float[] {
				(float) cos(ry),0,(float) sin(ry),0,
				0,1,0,0,
				(float) -sin(ry),0,(float) cos(ry),0,
				0,0,0,1	
		});
		double rz = Math.toRadians(rotation.getZ());
		Matrix4 rotZ = new Matrix4(new float[] {
				(float) cos(rz),(float)-sin(rz),0,0,
				(float) sin(rz),(float)cos(rz),0,0,
				0,0,1,0,
				0,0,0,1
		});
		Matrix4 scale = new Matrix4(new float[] {
				1,0,0,0,
				0,1,0,0,
				0,0,1,0,
				0,0,0,1
		});
		return trans.mult(rotX.mult(rotY.mult(rotZ.mult(scale))));
	}
}
