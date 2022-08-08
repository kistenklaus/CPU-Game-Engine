package engine;

public abstract class Container {
	protected final int CON_WIDTH, CON_HEIGHT;
	public Container(int con_width, int con_height) {
		this.CON_WIDTH = con_width;
		this.CON_HEIGHT = con_height;
	}
	public abstract void init();
	public abstract void tick(double fD);
	public abstract void render(Renderer renderer);
	public abstract void cleanUp();
	public int getCONTAINER_WIDTH() {
		return this.CON_WIDTH;
	}
	public int getCONTAINER_HEIGHT() {
		return this.CON_HEIGHT;
	}
}
