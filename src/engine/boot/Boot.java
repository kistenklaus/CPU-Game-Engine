package engine.boot;

import engine.Engine;
import logic.Logic_dummy;

public class Boot {
	public static void main(String[] args) {
		Engine engine = new Engine(640,640,new Logic_dummy());
		engine.start();
	}
}
