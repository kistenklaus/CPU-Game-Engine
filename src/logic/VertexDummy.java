package logic;

import java.util.HashMap;
import java.util.Map;

import engine.gfx.Vertex;
import engine.gfx.renderPrograms.VertexProgram;
import math.DataType;
import math.Matrix4;
import math.Vec4;

public class VertexDummy extends VertexProgram{
	
	@Override
	public Map<String, DataType> main(Vertex vertex) {
		Matrix4 proj = super.uniform.get("projection").mat4();
		Matrix4 view = super.uniform.get("view").mat4();
		super.position =proj.mult(view.mult(new Vec4(vertex.getPosition(),1)));
		Map<String, DataType> varying = new HashMap<String, DataType>();
		varying.put("uvCoords", vertex.getAttrib("textureCoords"));
		return varying;
	}

}
