package engine.gfx.renderPrograms;

import java.awt.Color;

import engine.Frame;
import engine.gfx.Vertex;
import math.DataType;
import math.Vec4;

public class RenderProgram {
	private VertexProgram vertexProgram;
	private FragmentProgram fragmentProgram;
	public RenderProgram(VertexProgram vertexProgram, FragmentProgram fragmentProgram) {
		this.vertexProgram = vertexProgram;
		this.fragmentProgram = fragmentProgram;
	}
	
	public Vertex runVertexProgram(Vertex vertex, int con_width, int con_height) {
		return vertexProgram.run(vertex, con_width, con_height);
	}
	public void loadVertexUniform(String uniform_name, DataType uniform_value) {
		this.vertexProgram.loadUniform(uniform_name, uniform_value);
	}

	public void paintFrag(Frame frame, int x, int y, Vertex interp) {
		Vec4 fragColor = fragmentProgram.run(interp);
		Color jcolor;
		try {
			jcolor = new Color(fragColor.getX(), fragColor.getY(), fragColor.getZ());
		}catch(Exception e) {
			jcolor = new Color(1,0,0);
		}
		frame.paintPixel(x, y, jcolor.getRGB(), fragColor.getW());
		
	}
}
