package engine.gfx.renderPrograms;

import java.util.HashMap;

import math.Vec;

public abstract class VertexTransform {
	protected Vec position;
	public abstract HashMap<String, Vec> main(Vec vertex, Vec textureCoords, Vec normals);
	public Vec getPosition() {
		return this.position;
	}
}
