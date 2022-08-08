package engine.gfx.shaders;

import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

public class VertexShader {
	
	private Vector2f[] trans_Verticies;
	private Vector2f out_Position;
	private Matrix4f proj;
	
	public VertexShader() {
		
	}
	
	public void run(Vector3f[] verticies) {
		this.trans_Verticies = new Vector2f[3];
		for(int i = 0; i < 3; i++) {
			main(verticies[i]);
			trans_Verticies[i] = this.out_Position;
		}
	}
	
	private void main(Vector3f position) {
		Vector4f pos4D = position.cast4D();
		pos4D.setComp(3, 1);
		pos4D = proj.mult(pos4D).cast4D();
		pos4D.div(pos4D.getComp(3));
		this.out_Position = pos4D.cast2D();
		
	}
	
	public Vector2f[] getTransformedVerticies() {
		return this.trans_Verticies;
	}
	
	public void unifromProjection(Matrix4f proj) {
		this.proj = proj;
	}
}
