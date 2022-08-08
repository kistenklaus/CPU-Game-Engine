package logic;

import java.util.Map;

import engine.Frame;
import engine.gfx.renderPrograms.FragmentProgram;
import math.Vec;

public class Fragment_dummy extends FragmentProgram{

	@Override
	public Vec main(Frame frame, Map<String, Vec> varying, Vec fragPosition) {
		Vec uv = varying.get("textureCoords");
		return new Vec(new float[] {1,uv.getX(),uv.getY(),1});
	}

}
