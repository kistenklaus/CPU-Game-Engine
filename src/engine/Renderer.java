package engine;

import engine.gfx.entity.Entity;
import engine.gfx.model.Model;
import engine.gfx.model.Triangle;
import engine.gfx.model.Vertex;
import engine.gfx.programs.RenderProgram;
import engine.gfx.programs.VertexProgram;
import math.Mat4;
import math.Maths;
import math.Vec2;
import math.Vec3;

public class Renderer {
	private int con_width, con_height;
	private Frame frame;
	private RenderProgram renderProgram;
//	private Matrix4 projection;
	public Renderer(int con_width, int con_height) {
		this.con_width = con_width;
		this.con_height = con_height;
//		this.projection = Matrix4.perspective(60, con_width, con_height, 0.1f, 100f);
	}
	
	public void drawEntity(Entity entity) {
		Mat4 transformion = Maths.tranformation(entity.getPosition(), entity.getRotation());
		renderProgram.uniform("transform", transformion);
		drawModel(entity.getModel());
	}
	
	public void drawModel(Model model) {
		Vertex[] verticies = model.getVerticies();
		int[] indicies = model.getIndicies();
		
		//VERTEX_PROGRAM:
		VertexProgram vs = renderProgram.getVertexProgram();
		Vertex[] vv = new Vertex[verticies.length]; 
		for(int i = 0; i < verticies.length; i++) {
			vv[i] = vs.main(verticies[i].clone());
		}
		//TRIANGLE_ASSAMBLY:
		Triangle[] triangles = new Triangle[model.getDrawCount()];
		for(int i = 0; i < triangles.length; i++) {
			triangles[i] = new Triangle(vv[indicies[i*3]], vv[indicies[i*3+1]], vv[indicies[i*3+2]]);
		}
		
		//BACK_FACE_CULLING:
		for(int i = 0; i < triangles.length; i++) {
			Triangle triangle = triangles[i];
			Vec3 normal = triangle.getNormal();
			Vec3 v1 = triangle.getV1Pos().vec3();
			if(v1.dot(normal)<=0) {	//if normal is facing away from (0|0|0)
				triangles[i].cullFace();
			}
		}
		
		//POST-VERTEX-TRANSFORMATION: (PerspectiveDivide)
		for(int i = 0; i < triangles.length; i++) {
			if(!triangles[i].isCulled()) {
//				System.out.println(triangles[i]);
				triangles[i].perspectiveDivide();
				triangles[i].mapVerticies(con_width, con_height);
			}
		}
		
		/* RENDERS TRIANGLES: includes
		 * 	-Primitv-Triangle-Tessalation.
		 *  -Rasterisation of Top and Bottom Triangle.
		 *  -Interpolates the Vertex Specifications including Vertex.position for zBuffering
		 *     #zOrder is not destroyed by perspectiv Divide
		 *  -Invokes the PixelProgram for every Fragment painted.
		 * Optional: RENDERS-MESH
		 */
		for(int i = 0; i < triangles.length; i++) {
			if(!triangles[i].isCulled()) {
				drawTriangle(triangles[i]);
				drawTriangleMesh(triangles[i], 0x00FFFF, 255);
			}
				
		}	
	}
	
	
	public void drawTriangle(Triangle triangle) {
		triangle.sortY();
		if(triangle.isUpTriangle()) {
			drawUpTriangle(triangle);
		}else if(triangle.isDownTriangle()) {
			drawDownTriangle(triangle);
		}else {
			//interpolate new Vertex S:
			
			
			
			//TODO
			
			
		}
	}
	
	public void drawUpTriangle(Triangle triangle) {
		//Rasterisation TODO
	}
	public void drawDownTriangle(Triangle triangle) {
		//Rasterisation TODO
	}
	
	public void drawTriangleMesh(Triangle triangle, int color, int alpha) {
		Vec2 v1 = triangle.getV1Pos().vec2();
		Vec2 v2 = triangle.getV2Pos().vec2();
		Vec2 v3 = triangle.getV3Pos().vec2();
		drawLine(v1, v2, color, alpha);
		drawLine(v1, v3, color, alpha);
		drawLine(v2, v3, color, alpha);
	}
	
	/**
	 * @author url:[https://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm#Java}
	 */
	public void drawLine(int x1, int y1, int x2, int y2, int color, int alpha) {
        // delta of exact value and rounded value of the dependent variable
        int d = 0;
 
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
 
        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point
 
        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;
 
        int x = x1;
        int y = y1;
 
        if (dx >= dy) {
            while (true) {
            	frame.putPixel(x, y, color, alpha);
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
            	frame.putPixel(x, y, color, alpha);
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
    }
	
	public void drawLine(Vec2 v1, Vec2 v2, int color, int alpha) {
		drawLine((int)Math.ceil(v1.getX()), (int)Math.ceil(v1.getY()),
				 (int)Math.ceil(v2.getX()), (int)Math.ceil(v2.getY()),
				 color, alpha);
	}
	
	public void createFrame() {
		this.frame = new Frame(con_width, con_height, 0x0);
	}
	public Frame getFrame() {
		return this.frame;
	}
	public void bindRenderProgram(RenderProgram rp) {
		this.renderProgram = rp;
	}
}
