package engine.gfx;

import math.Vec2;
import math.Vec3;

public class Model {
	private Vertex[] verticies;
	private int[] indicies;
	private int drawCount;
	public Model(float[] verticies, float[] textureCoords, int[] indicies) {
		this.indicies = indicies;
		this.drawCount = indicies.length/3;
		this.verticies = new Vertex[verticies.length/3];
		for(int i = 0; i < this.verticies.length; i++) {
			this.verticies[i] = new Vertex(new Vec3(verticies[i*3], verticies[i*3+1], verticies[i*3+2]));
			this.verticies[i].addAttriute("textureCoords", new Vec2(textureCoords[i*2], textureCoords[i*2+1]));
		}
	}
	public Vertex[] getVerticies() {
		return verticies;
	}
	public int[] getIndicies() {
		return indicies;
	}
	public int getDrawCount() {
		return this.drawCount;
	}
}
