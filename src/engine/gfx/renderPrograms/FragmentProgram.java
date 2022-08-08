package engine.gfx.renderPrograms;

import java.util.Map;

import engine.gfx.Vertex;
import math.DataType;
import math.Vec4;

public abstract class FragmentProgram {
	public abstract Vec4 main(Map<String, DataType> varying);
	
	public Vec4 run(Vertex vertex) {
		return main(vertex.getVaryingHashMap());
	}
}
