package math;

public abstract class DataType {
	protected float[] data;
	public DataType(float[] data) {
		this.data = data;
	}
	
	public String toString() {
		String p = "[";
		for(int i = 0; i < this.data.length; i++) {
			p+=this.data[i]+";";
		}
		p = p.substring(0, p.length()-1);
		p+="]";
		return p;
	}
	public void mult(float scalar) {
		for(int i = 0; i < data.length; i++) {
			this.data[i]*=scalar;
		}
	}
	public static DataType MULT(DataType d1, float scalar) {
		DataType res = d1.clone();
		res.mult(scalar);
		return res;
		
	}
	public DataType add(DataType dataType) {
		for(int i = 0; i < this.data.length && i < dataType.data.length; i++) {
			this.data[i]+=dataType.data[i];
		}
		return this;
	}
	public void addtoComp(int comp, float value) {
		this.data[comp]+=value;
	}
	public float[] getData() {
		return this.data;
	}
	public DataType clone() {
		return new DataType(this.data.clone()) {	};
	}
	public void multComp(int comp, float scalar) {
		this.data[comp] *= scalar;
	}
	
	
	//Casting
	public Mat4 mat4() {
		return new Mat4(this.data);
	}
	public Vec2 vec2() {
		return new Vec2(this.data);
	}
	
}
