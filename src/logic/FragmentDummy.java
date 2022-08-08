package logic;

import java.util.Map;

import engine.gfx.renderPrograms.FragmentProgram;
import math.DataType;
import math.Vec2;
import math.Vec4;

public class FragmentDummy extends FragmentProgram{

	@Override
	public Vec4 main(Map<String, DataType> varying) {
		Vec2 uvCoords = varying.get("uvCoords").vec2();
		return new Vec4(1,uvCoords.getX(), uvCoords.getY(), 1);
	}

}
