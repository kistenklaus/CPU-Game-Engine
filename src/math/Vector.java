package math;

public class Vector {
	protected float[] comp;
	protected int dimensions;
	public Vector(float[] comp) {
		this.comp = comp;
		this.dimensions = comp.length;
	}
	protected static boolean Compatable(Vector v1, Vector v2) {
		return v1.dimensions == v2.dimensions;
	}
	
	public Vector add(Vector vec) {
		if (Compatable(this, vec)) {
			for(int c = 0; c < this.dimensions; c++) {
				this.comp[c]+=vec.getComp(c);
			}
			return this;
		}
		throw new IllegalArgumentException("Arguments are not Comparable");
	}
	public static Vector ADD(Vector v1, Vector v2) {
		if (Compatable(v1, v2)) {
			float[] comp = new float[v1.dimensions];
			for(int c = 0; c < v1.dimensions; c++) {
				comp[c] = v1.comp[c]+v2.comp[c];
			}
			return new Vector(comp);
		}
		throw new IllegalArgumentException("Arguments are not Comparable");
	}
	
	public float dot(Vector vec) {
		return DOT(this,vec);
	}
	public float DOT(Vector v1, Vector v2) {
		if(Compatable(v1, v2)) {
			float dot = 0;
			for(int c = 0; c < v1.dimensions; c++) {
				dot += v1.comp[c] * v2.comp[c];
			}
			return dot;
		}
		throw new IllegalArgumentException("Arguments are not Comparable");
	}
	
	public Vector2f cast2D() {
		return new Vector2f(castArray(this.comp,this.dimensions,2));
	}
	public Vector3f cast3D() {
		return new Vector3f(castArray(this.comp,this.dimensions,3));
	}
	public Vector4f cast4D() {
		return new Vector4f(castArray(this.comp,this.dimensions,4));
	}
	private float[] castArray(float[] comp, int sdim, int cdim) {
		float[] cast = new float[cdim];
		for(int i = 0; i < cdim && i < sdim; i++) {
			cast[i] = comp[i];
		}
		return cast;
	}
	public void div(float scalar) {
		for(int i = 0; i < this.dimensions; i++) {
			this.comp[i]/=scalar;
		}
	}
	public void mul(float scalar) {
		for(int i = 0; i < this.dimensions; i++) {
			this.comp[i]*=scalar;
		}
	}

	public float mag(){
		float sqMag = 0;
		for(int i = 0; i < this.dimensions; i++) {
			sqMag += this.comp[i] * this.comp[i];
		}
		return (float) Math.sqrt(sqMag);
	}

	public void normahlize(){
		this.div(this.mag());
	}

	public void setMag(float mag){
		this.mul(mag / this.mag());
	}

	public int getDimensions() {
		return this.dimensions;
	}
	public float getComp(int c) {
		return this.comp[c];
	}
	public void setComp(int c, float value) {
		this.comp[c] = value;
	}
	public float[] getComponents() {
		return this.comp;
	}
	public void setComponents(float[] comp) {
		this.comp = comp;
	}
	public String toString() {
		String p = "[";
		for(int i = 0; i < this.dimensions; i++) {
			p+=this.getComp(i)+";";
		}
		p+="]";
		return p;
	}
}
