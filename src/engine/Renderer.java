package engine;

import engine.gfx.Model;
import engine.gfx.renderPrograms.RenderProgram;
import math.Vec;

public class Renderer {
	private int con_width, con_height;
	private Frame frame;
	public Renderer(int con_width, int con_height) {
		this.con_width = con_width;
		this.con_height = con_height;
	}
	
	public void createFrame() {
		this.frame = new Frame(con_width, con_height, 0x0);
	}
	
	public void renderModel(Model model, RenderProgram renderProgram) {
		renderProgram.startDraw();
		int[] ind = model.getIndicies();
		Vec[] verticies = model.getVerticies();
		Vec[] textureCoords = model.getTextureCoords();
		Vec[] fragVert = new Vec[verticies.length];
		//Appling VertexTransformation:
		for(int i = 0; i < verticies.length; i++) {
			//running the VertexTransform and init the Varying HashMap used in the FragmentProgram; getting position in ScreenSpace: -1 to 1
			Vec position = renderProgram.runVertexTransform(verticies[i], textureCoords[i], null);		
			//Transforming to fragSpace con_width x con_height and origin at the left bottom corner
			fragVert[i] = new Vec(new float[] {(position.getX()+1f)*con_width/2, (position.getY()+1f)*con_height/2, position.getZ()});
		}
		for(int i = 0; i < model.getDrawCount(); i++) {
			renderProgram.setVarying(i, ind);
			renderAnyTriangle(new Vec[] {fragVert[ ind[i*3] ], fragVert[ ind[i*3+1] ], fragVert[ ind[i*3+2] ]},renderProgram);
		}
	}
	
	public void renderAnyTriangle(Vec[] fragVert, RenderProgram renderProgram) {
//		System.out.println("Drawing Triangle:" + fragVert[0].toString() + "  " + fragVert[1].toString() + "  " + fragVert[2].toString());
		//Sorting verticies by y
		if(fragVert[0].getY()<fragVert[1].getY())swap(fragVert, 0, 1);
		if(fragVert[1].getY()<fragVert[2].getY())swap(fragVert, 1, 2);
		if(fragVert[0].getY()<fragVert[1].getY())swap(fragVert, 0, 1);
		if(fragVert[1].getY()==fragVert[2].getY()) {
			renderUpTriangle(fragVert, renderProgram);
		}else if(fragVert[0].getY() == fragVert[1].getY()) {
			renderDownTriangle(fragVert, renderProgram);		
		}else {
			//Split up into 2 FlatTriangles by getting s throw Interpolation:
			float beta = (fragVert[0].getY()-fragVert[2].getY())/(fragVert[0].getY()-fragVert[1].getY());
			Vec s = new Vec(new float[] {
					(1-beta)*fragVert[0].getX()+beta*fragVert[0].getX(),
					fragVert[1].getY(),
					(1-beta)*fragVert[0].getZ()+beta*fragVert[0].getZ()
			});
			renderUpTriangle(new Vec[] {fragVert[0], s, fragVert[1]}, renderProgram);
			renderDownTriangle(new Vec[] {s, fragVert[1], fragVert[2]}, renderProgram);
		}
	}
	private void swap(Vec[] vert, int index1, int index2) {
		Vec temp = vert[index2];
		vert[index2] = vert[index1];
		vert[index1] = temp;
	}
	
	public void renderUpTriangle(Vec[] fragVert, RenderProgram renderProgram) {
		renderProgram.prepareFrag();
		if(fragVert[2].getX()<fragVert[1].getX())swap(fragVert, 1,2);	//sorting Verticies by X
		float m2 = (fragVert[0].getX()-fragVert[1].getX())/(fragVert[0].getY()-fragVert[1].getY());	//	deltaX/deltaY	;dx e R | dy e R*
		float c2 = fragVert[0].getX()-m2*fragVert[0].getY();
		float m3 = (fragVert[0].getX()-fragVert[2].getX())/(fragVert[0].getY()-fragVert[2].getY());	//	deltaX/deltaY	;dx e R | dy e R*
		float c3 = fragVert[0].getX()-m3*fragVert[0].getY();
		int yStart = (int) Math.ceil(fragVert[2].getY());	//lowest Vertex
		int yEnd = (int) Math.ceil(fragVert[0].getY());		//highest Vertex
		for(int y = yStart; y<yEnd; y++) {
			int xStart = (int) Math.ceil(m2*(y+0.5f) + c2);
			int xEnd = (int) Math.ceil(m3*(y+0.5f) + c3);
			for(int x = xStart; x <= xEnd; x++) {
				renderProgram.paintFrag(frame,x,y);
			}
		}	
	}
	
	public void renderDownTriangle(Vec[] fragVert, RenderProgram renderProgram) {
		renderProgram.prepareFrag();
		if(fragVert[0].getX()>fragVert[1].getX())swap(fragVert, 0,1);	//sorting Verticies by X
		float m1 = (fragVert[2].getX()-fragVert[0].getX())/(fragVert[2].getY()-fragVert[0].getY());	//	deltaX/deltaY	;dx e R | dy e R*
		float c1 = fragVert[2].getX()-m1*fragVert[2].getY();
		float m2 = (fragVert[2].getX()-fragVert[1].getX())/(fragVert[2].getY()-fragVert[0].getY());	//	deltaX/deltaY	;dx e R | dy e R*
		float c2 = fragVert[2].getX()-m2*fragVert[2].getY();
		int yStart = (int) Math.ceil(fragVert[2].getY());
		int yEnd = (int) Math.ceil(fragVert[0].getY());
		for(int y = yStart; y < yEnd; y++) {
			int xStart = (int) Math.ceil(m1*(y+0.5f)+c1);
			int xEnd = (int) Math.ceil(m2*(y+0.5f)+c2);
			for(int x = xStart; x <= xEnd; x++) {
				//Linear Interpolation TODO
				renderProgram.paintFrag(frame,x,y);
			}
		}
	}
	
	public Frame getFrame() {
		return this.frame;
	}
}
