package engine.gfx.programs;

import java.util.Map;

import engine.gfx.model.Vertex;
import math.DataType;

public abstract class VertexProgram{
	protected Map<String, DataType> uniform;
	public abstract Vertex main(Vertex vertex);
	void setUniform(Map<String,DataType> uniform) {
		this.uniform = uniform;
	}
}
