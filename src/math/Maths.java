package math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Maths {
	
	public static float interpolateRatio(float f1, float f2, float i) {
		return (f1-i)/(f1-f2);
	}
	
	public static Vec4 interpolate(float alpha, final Vec4 v1, final Vec4 v2) {
		return Vec4.MULT(v1, 1-alpha).add(Vec4.MULT(v2, alpha));
		// (v1*(1-alpha)) / (v2*alpha)
	}
	public static Vec2 interpolate(float alpha, final Vec2 v1, final Vec2 v2) {
		return Vec2.MULT(v1, 1-alpha).add(Vec2.MULT(v2, alpha));
		// (v1*(1-alpha)) / (v2*alpha)
	}
	
	
	
	
	public static Mat4 perspective(float FOV, float aspect, float near, float far) {
		float ta = (float) Math.tan(Math.toRadians(FOV/2f));
		return new Mat4(new float[] {
			1f/(aspect*ta),0,0,0,
			0,1f/ta,0,0,
			0,0,-(near+far)/(near-far),(2*far*near)/(near-far),
			0,0,-1,0
		});
	}
	
	public static Mat4 tranformation(Vec3 position, Vec3 rotation) {
		Mat4 trans = new Mat4(new float[] {
				1,0,0,position.getX(),
				0,1,0,position.getY(),
				0,0,1,position.getZ(),
				0,0,0,1
		});
		double rx = Math.toRadians(rotation.getX());
		Mat4 rotX = new Mat4(new float[] {
				1,0,0,0,
				0,(float) cos(rx),(float) -sin(rx),0,
				0,(float) sin(rx),(float) cos(rx),0,
				0,0,0,1
		});
		double ry = Math.toRadians(rotation.getY());
		Mat4 rotY = new Mat4(new float[] {
				(float) cos(ry),0,(float) sin(ry),0,
				0,1,0,0,
				(float) -sin(ry),0,(float) cos(ry),0,
				0,0,0,1	
		});
		double rz = Math.toRadians(rotation.getZ());
		Mat4 rotZ = new Mat4(new float[] {
				(float) cos(rz),(float)-sin(rz),0,0,
				(float) sin(rz),(float)cos(rz),0,0,
				0,0,1,0,
				0,0,0,1
		});
		Mat4 scale = new Mat4(new float[] {
				1,0,0,0,
				0,1,0,0,
				0,0,1,0,
				0,0,0,1
		});
		return trans.mult(rotX.mult(rotY.mult(rotZ.mult(scale))));
	}
	
}
