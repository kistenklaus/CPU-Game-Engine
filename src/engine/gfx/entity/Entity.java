package engine.gfx.entity;

import engine.gfx.model.Model;
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
	public void rotateX(float dr) {
		this.rotation.addtoComp(0, dr);
	}
	public void rotateY(float dr) {
		this.rotation.addtoComp(1, dr);
	}
	public void rotateZ(float dr) {
		this.rotation.addtoComp(2, dr);
	}
	public void move(Vec3 delta) {
		this.position.add(delta);
	}
	public void moveX(float dx) {
		this.position.addtoComp(0, dx);
	}
	public void moveY(float dy) {
		this.position.addtoComp(1, dy);
	}
	public void moveZ(float dz) {
		this.position.addtoComp(2, dz);
	}
	public Vec3 getPosition() {
		return position;
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
	public Model getModel() {
		return model;
	}
	
	
}
