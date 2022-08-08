package engine.gfx.renderPrograms;

import java.util.HashMap;
import java.util.Map;

import engine.gfx.Vertex;
import math.DataType;
import math.Vec3;
import math.Vec4;

public abstract class VertexProgram {
	protected Vec4 position;
	protected Map<String, DataType> uniform = new HashMap<String, DataType>();
	public abstract Map<String, DataType> main(Vertex vertex);
	
	public Vertex run(Vertex vertex, int con_width, int con_height) {
		Map<String,DataType> varying = main(vertex);
		vertex.setVarying(varying);
		position.mult(1f/position.getW());
		float[] pos_comp = position.getData();
		pos_comp[0]+=1;
		pos_comp[1]+=1;
		pos_comp[0]*=con_width/2f;
		pos_comp[1]*=con_height/2f;
		vertex.setPosition(new Vec3(pos_comp));
		return vertex;
	}
	public void loadUniform(String uniform_name, DataType uniform_value) {
		this.uniform.put(uniform_name, uniform_value);
	}
}
