package logic;

import engine.Container;
import engine.Renderer;
import engine.gfx.Entity;
import engine.gfx.Model;
import engine.gfx.renderPrograms.RenderProgram;
import math.Vec3;

public class Container_dummy extends Container{

	public Container_dummy() {super(240, 160);}

	private Entity e1;
	private RenderProgram renderProgram;
	
	@Override
	public void init() {
		this.renderProgram = new RenderProgram(new VertexDummy(), new FragmentDummy());
		Model model = new Model(new float[] {
//				-50,50,0,
//				50,50,0,
//				50,-50,0,
//				-50,-50,0,
//				
//				-50,50,-50,
//				50,50,-50,
//				50,-50,-50,
//				-50,-50,-50,
				
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
				0.8f,0,
				0.8f,0.8f,
				0,0.8f,
				0,0,
				0.8f,0,
				0.8f,0.8f,
				0,0.8f
		}, new int[] {
				0,1,2,
				0,2,3,
				
				4,5,6,
				4,6,7,
				
				0,4,5,
				0,5,1,
				
				1,5,6,
				1,6,2,
				
				3,2,6,
				3,6,7,
				
				7,3,0,
				0,4,7,
				
		});
		this.e1 = new Entity(model, new Vec3(0,0,2f), new Vec3(0,0,0));
	}

	@Override
	public void tick(double fD) {
		this.e1.rotateY(0.5f);
//		this.e1.rotateX(0.1f);
	}

	@Override
	public void render(Renderer renderer) {
		renderer.renderEntity(this.e1, renderProgram);
	}

	@Override
	public void cleanUp() {
		
	}

}
