package logic;

import engine.gfx.model.Vertex;
import engine.gfx.programs.VertexProgram;
import math.Mat4;
import math.Vec4;

public class VertexDummy extends VertexProgram{

	@Override
	public Vertex main(Vertex vertex) {
		Mat4 proj = super.uniform.get("projection").mat4();
		Mat4 transform = super.uniform.get("transform").mat4();
		Vec4 position = proj.mult(transform.mult(vertex.getPosition()));
		return new Vertex(position, vertex.getTextureCoords());
	}

}
