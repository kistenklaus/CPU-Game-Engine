package engine.gfx.renderPrograms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.Frame;
import math.Vec;

public class RenderProgram {
	private VertexTransform vertexTransform;
	private FragmentProgram fragProgram;

	List<Map<String, Vec>> varying;
	Map<String, Vec> var1,var2,var3;
	private Set<String> keys;
	public RenderProgram(VertexTransform vertexTransform, FragmentProgram fragProgram) {
		this.vertexTransform = vertexTransform;
		this.fragProgram = fragProgram;
	}
	
	public void startDraw() {
		this.varying = new ArrayList<>();
	}
	
	public Vec runVertexTransform(Vec vertex, Vec textureCoords, Vec normals) {
		Map<String, Vec> vary = vertexTransform.main(vertex, textureCoords, normals);
		vary.put("position", vertexTransform.getPosition());
		varying.add(vary);
		return vertexTransform.getPosition();
	}

	public void prepareFrag() {
		this.keys = varying.get(0).keySet();
	}
	
	public void paintFrag(Frame frame, int x, int y) {
		HashMap<String, Vec> vary = new HashMap<String, Vec>();
		for(String key: this.keys) {	//Linear Interpolation
			if(key.equals("position"))continue;
			float[] a1 = var1.get(key).getComponents();
			float[] a2 = var2.get(key).getComponents();
			float[] a3 = var3.get(key).getComponents();
			int dim = a1.length;
			float[] interp = new float[dim];
			vary.put(key, new Vec(interp));
		}
		
		Vec fragColor = fragProgram.main(frame, vary, new Vec(new float[] {x,y}));
		int red = ((byte)(fragColor.getX())<<16);
		int green =	((byte)(fragColor.getY())<<8);
		int blue = (byte)(fragColor.getZ());
		int hexColor =255*( ((byte)1<<24)+red+green+blue );
		frame.paintPixel(x, y, hexColor, (int)(fragColor.getW()*255));
	}
	
	
	public void setVarying(int drawCount, int[] indicies) {
		var1 = varying.get(indicies[drawCount*3]);
		var2 = varying.get(indicies[drawCount*3+1]);
		var3 = varying.get(indicies[drawCount*3+2]);
	}
	public VertexTransform getVertexTransform() {
		return vertexTransform;
	}
	public FragmentProgram getFragProgram() {
		return fragProgram;
	}
}
