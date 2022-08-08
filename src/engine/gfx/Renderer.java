package engine.gfx;

import engine.gfx.model.RawTriangle;
import engine.gfx.shaders.FragmentShader;
import engine.gfx.shaders.VertexShader;
import engine.window.Frame;
import math.Matrix3f;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;

public class Renderer {
	
	private Frame frame;
	private int width, height;
	private Matrix4f projection;
	
	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;
		this.projection = new Matrix4f(
				2f/width, 	0, 			0,	 		-1f,
				0, 			2f/height, 	0,			-1f,
				0, 			0, 			1, 			0,
				0, 			0, 			1f/1000,	1);
				
	}
	
	public void setFrame(Frame currFrame) {
		this.frame = currFrame;
	}
	
	public void clearPixels(int color) {
		int[] pixels = new int[width*height];
		for(int i = 0; i < pixels.length; i++) { 
			pixels[i] = color-0xFFFFFF;
		}
		this.frame.setRawPixels(pixels);
	}
	
	public void renderTriangle(RawTriangle triangle, VertexShader vertexShader, FragmentShader fragmentShader) {
		vertexShader.unifromProjection(this.projection);
		vertexShader.run(triangle.getVerticies());
		Vector2f[] newVerticies = vertexShader.getTransformedVerticies();
		render2DVectorTriangle(newVerticies);
	}
	
	public void render2DVectorTriangle(Vector2f[] verticies) {
		Vector2f[] fragVert = prepareVerticies(verticies);
		float[] v1 = fragVert[0].getComponents(), v2 = fragVert[1].getComponents(), v3 = fragVert[2].getComponents();
		if(v1[1]==v2[1]) {
			boolean v1_hasBiggerX = v1[0]>v2[0];
			drawNormalTriangle(v3,v1_hasBiggerX?v2:v1,v1_hasBiggerX?v1:v2);
		}else if(v2[1]==v3[1]) {
			boolean v2_hasBiggerX = v2[0]>v3[0];
			drawNormalTriangle(v1,v2_hasBiggerX?v2:v3,v2_hasBiggerX?v3:v2);
		}else {
			float alpha = (v1[1]-v2[1])/(v1[1]-v3[1]);
			float[] s = new float[] {
					(1-alpha)*v1[0]+alpha*v3[0],
					Math.round((1-alpha)*v1[1]+alpha*v3[1])
			};
			boolean s_hasBiggerX = s[0]>v2[0];
			drawNormalTriangle(v1,s_hasBiggerX?s:v2, s_hasBiggerX?v2:s);
			drawFlipedTriangle(v3, s_hasBiggerX?v2:s, s_hasBiggerX?s:v2);
		}
	}
	private Vector2f[] prepareVerticies(Vector2f[] verticies){
		//sort by Y
		int width = frame.getWidth(), height = frame.getHeight();
		Vector2f[] fragVert = new Vector2f[verticies.length];
		Matrix3f ortho = new Matrix3f(
				width/2, 0, width/2,
				0, height/2,height/2,
				0, 0, 1);
		for(int i = 0; i < 3; i++) {
			float high = Float.NEGATIVE_INFINITY;
			int index = -1;
			for(int j = i; j < 3; j++) {
				float yComp = verticies[j].getComp(1);
				if(yComp>high) {
					high = yComp;
					index = j;
				}
			}
			if(i!=index)
				swapArray(verticies, i, index);
		}
		//Transform to pixelSpace
		for (int i = 0; i < verticies.length; i++) {
			Vector3f vertex3d = new Vector3f(verticies[i].getComp(0), verticies[i].getComp(1), 1);
			fragVert[i] = ortho.mult(vertex3d).cast2D();
		}
		return fragVert;
	}
	
	private void drawNormalTriangle(float[] v1, float[] v2, float[] v3) {
		float m2 = (v1[0]-v2[0])/(v1[1]-v2[1]);	//	deltaX/deltaY	;dx e R | dy e R*
		float c2 = v1[0]-m2*v1[1];
		float m3 = (v1[0]-v3[0])/(v1[1]-v3[1]);	//	deltaX/deltaY	;dx e R | dy e R*
		float c3 = v1[0]-m3*v1[1];
		int startY = (int) Math.ceil(v2[1]), endY = (int) Math.ceil(v1[1]);
		if(startY<0)startY=0;
		if(endY>height)endY=height;
		for(int y = startY; y <= endY; y++) {
			float startX = invSlopeFunction(m3, c3, y+0.5f), endX = invSlopeFunction(m2, c2, y+0.5f);
			if(startX<0)startX=0; if(endX>=width)endX=width-1;
			for(int x = (int)Math.ceil(startX); x <= endX; x++) {
				frame.setPixel(x, y, 0xAAAAAA, 255);
			}
		}
	}
	
	private void drawFlipedTriangle(float[] v1, float[] v2, float[] v3) {
		float m2 = (v1[0]-v2[0])/(v1[1]-v2[1]);	//	deltaX/deltaY	;dx e R | dy e R*
		float c2 = v1[0]-m2*v1[1];
		float m3 = (v1[0]-v3[0])/(v1[1]-v3[1]);	//	deltaX/deltaY	;dx e R | dy e R*
		float c3 = v1[0]-m3*v1[1];
		int startY = (int) Math.ceil(v1[1]), endY = (int) Math.ceil(v2[1]);
		if(startY<0)startY=0;
		if(endY>height)endY=height;
		for(int y = startY; y <= endY; y++) {
			float startX = invSlopeFunction(m2, c2, y+0.5f), endX = invSlopeFunction(m3, c3, y+0.5f);
			if(startX<0)startX=0; if(endX>=width)endX=width-1;
			for(int x = (int) Math.ceil(startX); x <= endX; x++) {
				frame.setPixel(x, y, 0xCCCCCC, 255);
			}
		}
	}
	
	private float invSlopeFunction(float m, float c, float y) {
		return y*m+c;
	}
	
	
	//Utility
	private void swapArray(Vector2f[] array, int index1, int index2) {
		Vector2f temp = array[index2];
		array[index2] = array[index1];
		array[index1] = temp;
	}
}
