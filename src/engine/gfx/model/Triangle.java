package engine.gfx.model;

import math.Vec3;
import math.Vec4;

public class Triangle {
	
	private Vertex v1,v2,v3;
	private Vec3 normal;
	private boolean culled;
	
	public Triangle(Vertex v1, Vertex v2, Vertex v3) {
		//Calc Normal
		Vec3 v12 = Vec3.SUB(v2.getPosition().vec3(), v1.getPosition().vec3());
		Vec3 v13 = Vec3.SUB(v3.getPosition().vec3(), v1.getPosition().vec3());
		this.normal = v12.cross(v13);
		
		this.culled = false;
		
		this.v1 = v1.clone();
		this.v2 = v2.clone();
		this.v3 = v3.clone();
	}
	
	public Vec3 getNormal() {
		return this.normal;
	}
	public Vec4 getV1Pos() {
		return v1.getPosition();
	}
	public Vec4 getV2Pos() {
		return v2.getPosition();
	}
	public Vec4 getV3Pos() {
		return v3.getPosition();
	}
	public void cullFace() {
		this.culled = true;
	}
	public boolean isCulled() {
		return this.culled;
	}
	
	public void sortY() {
		if(v1.getY()<v2.getY()) {Vertex temp = v1;v1=v2;v2=temp;}
		if(v2.getY()<v3.getY()) {Vertex temp = v2;v2=v3;v3=temp;}
		if(v1.getY()<v2.getY()) {Vertex temp = v1;v1=v2;v2=temp;}	
	}
	
	/*
	 * sortY needs to be exec before this function works
	 */
	public boolean isUpTriangle() {
		return (v2.getY()==v3.getY());
	}
	/*
	 * sortY needs to be exec before this function works
	 */
	public boolean isDownTriangle() {
		return (v1.getY()==v2.getY());
	}
	
	
	public String toString() {
		return "Triangle=> \n"+v1+"\n"+v2+"\n"+v3+"\n-------------------------";
	}

	public void perspectiveDivide() {
		v1.getPosition().mult(1f/v1.getPosition().getW());
		v2.getPosition().mult(1f/v2.getPosition().getW());
		v3.getPosition().mult(1f/v3.getPosition().getW());
	}

	public void mapVerticies(int con_width, int con_height) {
		//Shifting every Vector to have the origin at [0|0]
		v1.getPosition().addtoComp(0, 1f);
		v1.getPosition().addtoComp(1, 1f);
		v2.getPosition().addtoComp(0, 1f);
		v2.getPosition().addtoComp(1, 1f);
		v3.getPosition().addtoComp(0, 1f);
		v3.getPosition().addtoComp(1, 1f);
		//Mapping every Vector to con_width|con_height
		float w = con_width/2f, h = con_height/2f;
		v1.getPosition().multComp(0, w);
		v1.getPosition().multComp(1, h);
		v2.getPosition().multComp(0, w);
		v2.getPosition().multComp(1, h);
		v3.getPosition().multComp(0, w);
		v3.getPosition().multComp(1, h);
	}
}
