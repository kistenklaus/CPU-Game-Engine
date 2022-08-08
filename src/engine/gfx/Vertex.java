package engine.gfx;

import java.util.HashMap;
import java.util.Map;

import math.DataType;
import math.Vec3;

public class Vertex {
	private Map<String, DataType> attributes;
	private Vec3 position;
	public Vertex(Vec3 position) {
		this.attributes = new HashMap<String, DataType>();
		this.position = position;
	}
	public void addAttriute(String attrib_name, DataType attrib_data) {
		this.attributes.put(attrib_name, attrib_data);
	}
	public DataType getAttrib(String attrib_name) {
		return this.attributes.get(attrib_name);
	}
	public Vertex clone() {
		Vertex clone = new Vertex(position.clone());
		for(String key: attributes.keySet()) {
			clone.addAttriute(key, attributes.get(key).clone());
		}
		return clone;
	}
	public Vec3 getPosition() {
		return this.position;
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
		return position + "=> " +attributes;
	}
	public void setVarying(Map<String, DataType> varying) {
		attributes = varying;
	}
	public DataType getVarying(String attrib_name) {
		return this.attributes.get(attrib_name);
	}
	public void setPosition(Vec3 position) {
		this.position = position;
	}
	public Map<String, DataType> getVaryingHashMap(){
		return this.attributes;
	}
	public static Vertex interpolate_byY(Vertex v1, Vertex v2, float y) {
		float beta = (v1.getY()-y)/(v1.getY()-v2.getY());
		return v1.interpolate(v2, beta);
		
	}
	public static Vertex interpolate_byX(Vertex v1, Vertex v2, int x) {
		float beta = (v1.getX()-x)/(v1.getX()-v2.getX());
		return v1.interpolate(v2, beta);
	}
	
	private Vertex interpolate(Vertex v2, float beta) {
		Vertex v1 = this.clone();
		Vec3 pos = Vec3.MULT(v1.getPosition(), 1f-beta).add(Vec3.MULT(v2.getPosition(), beta));
		Vertex inp = new Vertex(pos);
		for(String attrib: v1.attributes.keySet()) {
			DataType data_i = DataType.MULT(v1.getAttrib(attrib), 1f-beta).add(DataType.MULT(v2.getAttrib(attrib), beta));
			inp.addAttriute(attrib, data_i);
		}
		return inp;
	}

}
