package boot;

import engine.Engine;
import logic.Container_dummy;

public class Boot {
	public static void main(String[] args) {
		Engine engine = new Engine(960, 640, new Container_dummy());
		engine.start();
	}
}
