package testing;

import math.Matrix4;
import math.Vec4;

public class Testing {
	public static void main(String[] args) {
		
		Matrix4 mat = new Matrix4(new float[] {
				2f/960,0,0,1,
				0,2f/640,0,1,
				0,0,1,0,
				0,0,0,1
		});
		Vec4 vec = new Vec4(new float[] {
				100,100,1,1
		});
		Vec4 res = mat.mult(vec);
		System.out.println(res);
	}
}
