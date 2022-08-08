package engine;

import engine.gfx.Renderer;

public abstract class Logic {
	public abstract void init();
	public abstract void drawFrame(Renderer renderer);
	public abstract void cleanUp();
	protected int windowWidth;
	public void setWindowWidth(int windowWidth) {this.windowWidth = windowWidth;}
	protected int windowHeight;
	public void setWindowHeight(int windowHeight) {this.windowHeight = windowHeight;}
}
