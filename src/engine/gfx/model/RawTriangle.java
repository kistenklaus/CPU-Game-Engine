package engine.gfx.model;

import math.Vector3f;

public class RawTriangle {
	private Vector3f[] verticies;
	public RawTriangle(float[] verticies) {
		this.verticies = new Vector3f[3];
		this.verticies[0] = new Vector3f(verticies,0);
		this.verticies[1] = new Vector3f(verticies,3);
		this.verticies[2] = new Vector3f(verticies,6);
	}
	public Vector3f[] getVerticies() {
		return this.verticies;
	}
}
