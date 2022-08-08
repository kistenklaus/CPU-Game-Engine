package math;

public class Matrix3f extends Matrix{

	public Matrix3f(float m00, float m01, float m02,
			float m10, float m11, float m12,
			float m20, float m21, float m22) {
		super(new float[][] {
			new float[] {
					m00,m10,m20
			},
			new float[] {
					m01,m11,m21
			},
			new float[] {
					m02,m12,m22
			}
		});
	}
}
