package logic;

import engine.Container;
import engine.Renderer;
import engine.gfx.entity.Entity;
import engine.gfx.model.Model;
import engine.gfx.programs.RenderProgram;
import math.Mat4;
import math.Maths;
import math.Vec3;

public class Container_dummy extends Container{

	public Container_dummy() {super(240, 160);}
	
	private RenderProgram shader;
	private Entity entity;
	
	@Override
	public void init() {
		this.shader=new RenderProgram(new VertexDummy());
		Mat4 perspectiv = Maths.perspective(60f, CON_WIDTH/(float)CON_HEIGHT, 0.1f, 100f);
		this.shader.uniform("projection", perspectiv);
		
		Model model = new Model(new float[] {
				-0.5f,0.5f,0.5f,
				0.5f,0.5f,0.5f,
				0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				-0.5f,-0.5f,-0.5f
		}, new float[] {
				0,0,
				1,0,
				1,1,
				0,1,
				
				0,0,
				1,0,
				1,1,
				0,1
		}, new int[] {
				//FONT
				0,1,2,
				0,2,3,
				//BACK
				6,5,4,
				4,7,6,
				//LEFT-SIDE
				1,5,6,
				6,2,1,
				//RIGHT-SIDE
				3,7,4,
				3,4,0,
				//TOP-SIDE
				0,4,5,
				5,1,0,
				//BOTTOM-SIDE
				6,7,3,
				6,3,2
		});
		this.entity = new Entity(model, new Vec3(0, 0, 3), new Vec3(0, 0, 0));
	}

	@Override
	public void tick(double fD) {
//		this.entity.rotateZ(-0.5f);
//		this.entity.rotateX(0.3f);
//		this.entity.rotateY(0.1f);
	}

	@Override
	public void render(Renderer renderer) {
		renderer.bindRenderProgram(this.shader);
		renderer.drawEntity(this.entity);
	}

	@Override
	public void cleanUp() {
		
	}

}
