package logic;

import java.util.HashMap;

import engine.gfx.renderPrograms.VertexTransform;
import math.Vec;

public class Vertex_dummy extends VertexTransform{

	@Override
	public HashMap<String,Vec> main(Vec vertex, Vec textureCoords, Vec normals) {
		this.position = vertex;
		HashMap<String, Vec> varying = new HashMap<String, Vec>();
		varying.put("textureCoords", textureCoords);
		return varying;
	}
}
