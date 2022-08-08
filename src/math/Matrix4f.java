package math;

public class Matrix4f extends Matrix{

	public Matrix4f(float m00, float m01, float m02, float m03,
			float m10, float m11, float m12, float m13,
			float m20, float m21, float m22, float m23,
			float m30, float m31, float m32, float m33) {
		super(new float[][] {
			new float[] {
					m00,m10,m20,m30
			},
			new float[] {
					m01,m11,m21,m31
			},
			new float[] {
					m02,m12,m22,m32
			},
			new float[] {
					m03,m13,m23,m33
			},
		});
	}
}
