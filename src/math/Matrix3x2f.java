package math;

public class Matrix3x2f extends Matrix{
	public Matrix3x2f(float m00, float m01, float m02,
			float m10, float m11, float m12) {
		super(new float[][] {
			new float[] {
					m00,m10
			},
			new float[] {
					m01,m11
			},
			new float[] {
					m02,m12
			}
		});
	}
}
