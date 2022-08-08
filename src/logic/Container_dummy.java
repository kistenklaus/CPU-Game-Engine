package logic;

import engine.Container;
import engine.Renderer;
import engine.gfx.Model;
import engine.gfx.renderPrograms.RenderProgram;

public class Container_dummy extends Container{

	public Container_dummy() {super(240, 160);}
	
	private RenderProgram renderProgram;
	
	private Model model;

	@Override
	public void init() {
		this.renderProgram = new RenderProgram(new Vertex_dummy(), new Fragment_dummy());
		this.model = new Model(new float[] 
		{	//Positions
					-0.5f,0.5f,0,		//0
					0.5f,0.5f,0,		//1
					0.5f,-0.5f,0,		//2
					-0.5f, -0.5f,0 		//3
		},new float[]
		{	//TextureCoords
				0,0,
				1,0,
				1,1,
				0,1
		},
		new int[] 
		{	//Indicies
				0,2,3,
				0,1,2
		});
	}

	@Override
	public void tick(double fD) {
		
	}

	@Override
	public void render(Renderer renderer) {
		renderer.renderModel(model, renderProgram);
	}

	@Override
	public void cleanUp() {
		
	}

}
