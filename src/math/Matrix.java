package math;

public class Matrix {
	protected float[][] comp;
	protected int xDim, yDim;
	public Matrix(float[][] comp) {
		this.comp = comp;
		this.xDim = comp.length;
		this.yDim = comp[0].length;
	}
	
	public Vector mult(Vector vec) {
		if(vec.getDimensions() == this.xDim) {
			float[] res = new float[this.yDim];
			for(int c = 0; c<this.yDim; c++) {
				for(int n = 0; n < this.xDim; n++) {
					res[c]+=comp[n][c]*vec.getComp(n);
				}
			}
			return new Vector(res);
		}
		throw new IllegalArgumentException("Arguments are not Comparable");
	}
	
	
}
