package math;

public class Matrix2f extends Matrix{

	public Matrix2f(float m00, float m01,
			float m10, float m11) {
		super(new float[][] {
			new float[] {
					m00,m10
			},
			new float[] {
					m01,m11
			}
		});
	}

}
