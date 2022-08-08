package engine.gfx;

import math.Vec;

public class Model {
	private Vec[] verticies;
	private int[] indicies;
	private Vec[] textureCoords;
	private int drawCount;
	public Model(float[] verticies, float[] textureCoords, int[] indicies) {
		this.indicies = indicies;
		this.drawCount = this.indicies.length/3;
		this.verticies = new Vec[verticies.length/3];
		for(int i = 0; i < this.verticies.length; i++) {
			this.verticies[i] = new Vec(new float[] {verticies[i*3],verticies[i*3+1], verticies[i*3+2]});
		}
		this.textureCoords = new Vec[textureCoords.length/2];
		for(int i = 0; i < this.textureCoords.length; i++) {
			this.textureCoords[i] = new Vec(new float[] {textureCoords[i*2], textureCoords[i*2+1]});
		}
	}
	
	public Vec[] getVerticies() {
		return this.verticies;
	}
	public Vec getVertex(int n){
		return this.verticies[n];
	}
	public int[] getIndicies() {
		return this.indicies;
	}
	public int getDrawCount() {
		return this.drawCount;
	}
	public Vec[] getTextureCoords() {
		return this.textureCoords;
	}
}
