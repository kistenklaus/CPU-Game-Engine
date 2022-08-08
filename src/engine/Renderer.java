package engine;

import engine.gfx.Entity;
import engine.gfx.Model;
import engine.gfx.Vertex;
import engine.gfx.renderPrograms.RenderProgram;
import math.Matrix4;
import math.Vec3;

public class Renderer {
	private int con_width, con_height;
	private Frame frame;
	private Matrix4 projection;
	public Renderer(int con_width, int con_height) {
		this.con_width = con_width;
		this.con_height = con_height;
		this.projection = Matrix4.perspective(60, con_width, con_height, 0.1f, 100f);
//		this.projection = Matrix4.ortho(0, con_width, 0, con_height, 0.1f, 100f);
	}
	
	public void createFrame() {
		this.frame = new Frame(con_width, con_height, 0x0);
	}
	public Frame getFrame() {
		return this.frame;
	}
	
	
	public void renderEntity(Entity entity, RenderProgram renderProgram) {
		renderProgram.loadVertexUniform("view", Matrix4.view(entity.getPosition(), entity.getRotation()));
		renderModel(entity.getModel(), renderProgram);
	}
	
	public void renderModel(Model model, RenderProgram renderProgram) {
		Vertex[] model_verticies = model.getVerticies();
		Vertex[] verticies = new Vertex[model_verticies.length];
		//VertexUniforms:
		renderProgram.loadVertexUniform("projection", this.projection);
		
		for (int i = 0; i < model_verticies.length; i++) {
			//running the VertexProgram for every Vertex
			verticies[i] = renderProgram.runVertexProgram(model_verticies[i].clone(), con_width, con_height);
		}
		int[] indicies = model.getIndicies();
		for(int i = 0; i < model.getDrawCount(); i++) {
			//drawing Triangles by Indicies
			renderTriangle(verticies[indicies[i*3]], verticies[indicies[i*3+1]], verticies[indicies[i*3+2]], renderProgram);
		}
	}
	private void renderTriangle(Vertex v1, Vertex v2, Vertex v3, RenderProgram renderProgram) {
		//Sorting by hightest Y
		if(v1.getY() < v2.getY()) {
			Vertex temp = v2; v2 = v1; v1 = temp;	//swaping v1 and v2
		}if(v2.getY() < v3.getY()) {
			Vertex temp = v3; v3 = v2; v2 = temp;	//swaping v2 and v3
		}if(v1.getY() < v2.getY()) {
			Vertex temp = v2; v2 = v1; v1 = temp;	//swaping v1 and v2
		}
		if(v1.getY() == v2.getY()) {
			//Downward Facing Triangle
			renderDownTriangle(v1, v2, v3, renderProgram);
		}else if (v2.getY() == v3.getY()){
			renderUpTriangle(v1, v2, v3, renderProgram);
		}else {
			//Not a Flat Triangle => Split up into Up and Down Triangle:
			//Find new Point: s throw linear Interpolation
			Vertex s = Vertex.interpolate_byY(v1, v3, v2.getY());
			renderUpTriangle(v1, v2, s, renderProgram);
			renderDownTriangle(s, v2, v3, renderProgram);
		}
	}
	private void renderUpTriangle(Vertex v1, Vertex v2, Vertex v3, RenderProgram renderProgram) {
		//Sorting 2 and 3 by highest X:
		if(v3.getX() < v2.getX()) {Vertex temp = v3; v3 = v2; v2 = temp;}//Swaps vertex 2 and 3
		
		float vx = v1.getX(), vy = v1.getY();
		float m2 = (vx-v2.getX())/(vy-v2.getY());
		float c2 = vx-m2*vy;
		float m3 = (vx-v3.getX())/(vy-v3.getY());
		float c3 = vx-m3*vy;
		int yStart = (int) Math.ceil(v3.getY());
		if (yStart<0)yStart = 0;
		int yEnd = (int) Math.ceil(vy);
		if (yEnd>con_height)yEnd = con_height;
		for(int y = yStart; y < yEnd; y++) {
			Vertex interp_21 = Vertex.interpolate_byY(v2, v1, y);
			Vertex interp_31 = Vertex.interpolate_byY(v3, v1, y);
			int xStart = (int) Math.ceil(m2*(y+0.5f)+c2);
			if(xStart<0)xStart=0;
			int xEnd = (int) Math.ceil(m3*(y+0.5f)+c3);
			if(xEnd>con_width)xEnd=con_width;
			for(int x = xStart; x <= xEnd; x++) {
				renderProgram.paintFrag(frame, x, y, Vertex.interpolate_byX(interp_21, interp_31, x));
			}
		}
	}
	private void renderDownTriangle(Vertex v1, Vertex v2, Vertex v3, RenderProgram renderProgram) {
		//Sorting 1 and 2 by highest X:
		if(v1.getX() > v2.getX()){Vertex temp = v1; v1 = v2; v2 = temp;}//Swaps vertex 1 and 2
		
		double vx = v3.getX(), vy = v3.getY();
		double m1 = (vx-v1.getX())/(double)(vy-v1.getY());
		double c1 = vx-m1*(vy+0);
		double m2 = (vx-v2.getX())/(double)(vy-v2.getY());
		double c2 = vx-m2*(vy+0);
		int yStart = (int) Math.ceil(vy);
		if (yStart<0)yStart = 0;
		int yEnd = (int) Math.ceil(v1.getY());
		if (yEnd>con_height)yEnd = con_height;
		for(int y = yStart; y < yEnd; y++) {
			//Interpolating between v3-v2 and v3-v1
			Vertex interp_32 = Vertex.interpolate_byY(v2, v3, y);
			Vertex interp_31 = Vertex.interpolate_byY(v1, v3, y);
			int xStart = (int) Math.ceil(m1*(y+0.5f)+c1);
			if(xStart<0)xStart=0;
			int xEnd = (int) Math.ceil(m2*(y+0.5f)+c2);
			if(xEnd>con_width)xEnd=con_width;
			for(int x = xStart; x<= xEnd; x++) {
				//Interpolating between i31-i32
				renderProgram.paintFrag(frame, x, y, Vertex.interpolate_byX(interp_32, interp_31, x));
			}
		}
	}
}
