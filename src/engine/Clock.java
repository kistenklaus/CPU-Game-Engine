package engine;

public class Clock {
	private Engine engine;
	
	private long last;
	private double timer;
	private double inv_FPS;
	
	public Clock(Engine engine, int FPS) {
		this.engine = engine;
		this.inv_FPS = 1d/FPS;
	}
	public void init() {
		this.last = System.nanoTime();
	}
	
	public void update() {
		long curr = System.nanoTime();
		double delta= (curr-last)/1000000000d;
		last = curr;
		this.timer+=delta;
		if(timer>inv_FPS) {
			engine.refresh(timer);
			timer=0;
		}
	}
}
