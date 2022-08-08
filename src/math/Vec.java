package math;

public class Vec {
	private float[] comp;
	public Vec(float[] comp) {
		this.comp = comp;
	}
	public float[] getComponents() {
		return this.comp;
	}
	public float getY() {
		return this.comp[1];
	}
	public float getX() {
		return this.comp[0];
	}
	public float getZ() {
		return this.comp[2];
	}
	public float getW() {
		return this.comp[3];
	}
	public int getDimensions() {
		return this.comp.length;
	}
	public String toString() {
		String p = "[";
		for(int i = 0; i < this.comp.length; i++) {
			p+=this.comp[i]+";";
		}
		p = p.substring(0, p.length()-1);
		p+="]";
		return p;
	}
}
