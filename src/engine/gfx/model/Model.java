package engine.gfx.model;

import math.Vec2;
import math.Vec3;

public class Model {
	private Vertex[] verticies;
	private int[] indicies;
	private int drawCount;
	public Model(Vertex[] verticies, int[] indicies) {
		this.verticies = verticies;
		this.indicies = indicies;
		this.drawCount = indicies.length/3;
	}
	public Model(float[] positions, float[] textureCoords, int[] indicies) {
		this.drawCount = indicies.length/3;
		this.indicies = indicies;
		this.verticies = new Vertex[positions.length/3];
		for (int i = 0; i < this.verticies.length; i++) {
			this.verticies[i] = new Vertex(new Vec3(positions[i*3],positions[i*3+1],positions[i*3+2]),
					new Vec2(textureCoords[i*2], textureCoords[i*2+1]));
		}
	}
	public Vertex[] getVerticies() {
		return this.verticies;
	}
	public int[] getIndicies() {
		return this.indicies;
	}
	public int getDrawCount() {
		return this.drawCount;
	}
}
