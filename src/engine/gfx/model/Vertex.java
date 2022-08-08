package engine.gfx.model;

import math.Vec2;
import math.Vec3;
import math.Vec4;

public class Vertex {
	private Vec4 position;
	private Vec2 textureCoords;
	public Vertex(Vec3 position, Vec2 textureCoords) {
		this.position = new Vec4(position,1);
		this.textureCoords = textureCoords;
	}
	public Vertex(Vec4 position, Vec2 textureCoords) {
		this.position = position;
		this.textureCoords = textureCoords;
	}
	public Vec4 getPosition() {
		return this.position;
	}
	public Vec2 getTextureCoords() {
		return this.textureCoords;
	}
	public Vertex clone() {
		return new Vertex(this.position.clone(), this.textureCoords.clone());
	}
	
	
	public float getX() {
		return this.position.getX();
	}
	public float getY() {
		return this.position.getY();
	}
	public float getZ() {
		return this.position.getZ();
	}
	
	public String toString() {
		return "{"+position+";"+textureCoords+"}";
	}
}
