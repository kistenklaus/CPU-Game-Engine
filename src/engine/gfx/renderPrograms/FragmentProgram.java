package engine.gfx.renderPrograms;

import java.util.Map;

import engine.Frame;
import math.Vec;

public abstract class FragmentProgram {
	
	
	public abstract Vec main(Frame frame, Map<String, Vec> varying, Vec fragPosition);
}
