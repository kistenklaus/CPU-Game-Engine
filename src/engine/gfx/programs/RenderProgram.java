package engine.gfx.programs;

import java.util.HashMap;
import java.util.Map;

import math.DataType;

public class RenderProgram {

	private VertexProgram vertexProgram;
	private Map<String,DataType> uniform;
	
	public RenderProgram(VertexProgram vertexProgram) {
		this.vertexProgram = vertexProgram;
		this.uniform = new HashMap<String, DataType>();
		this.vertexProgram.setUniform(uniform);
	}
	
	public VertexProgram getVertexProgram() {
		return this.vertexProgram;
	}
	
	
	public void uniform(String uniform_name, DataType unifrom_value) {
		this.uniform.put(uniform_name, unifrom_value);
	}

}
