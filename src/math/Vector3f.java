package math;

public class Vector3f extends Vector{

	public Vector3f(float[] comp) {
		super(comp);
	}
	
	public Vector3f(float x, float y, float z) {
		super(new float[] {x,y,z});
	}
	
	public Vector3f(float[] array, int off) {
		super(new float[] {array[off], array[off+1], array[off+2]});
	}
	
}
