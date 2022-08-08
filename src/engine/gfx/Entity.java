package engine.gfx;

import math.Vec3;

public class Entity {
	private Model model;
	private Vec3 position;
	private Vec3 rotation;
	public Entity(Model model, Vec3 position, Vec3 rotation) {
		this.model = model;
		this.position = position;
		this.rotation = rotation;
	}
	public void move(Vec3 delta) {
		this.position = this.position.add(delta);
	}
	public void rotateX(float dr) {
		this.rotation.addtoComp(0, dr);
	}
	public void rotateY(float dr) {
		this.rotation.addtoComp(1, dr);
	}
	public void rotateZ(float dr) {
		this.rotation.addtoComp(2, dr);
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Vec3 getPosition() {
		return this.position;
	}
	public void setPosition(Vec3 position) {
		this.position = position;
	}
	public Vec3 getRotation() {
		return rotation;
	}
	public void setRotation(Vec3 rotation) {
		this.rotation = rotation;
	}
	
}
