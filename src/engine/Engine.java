package engine;

public class Engine implements Runnable{
	private Window window;
	private Renderer renderer;
	private Input input;
	private Clock clock;
	private Container container;
	private Thread thread;
	
	private boolean running;
	
	public Engine(int win_width, int win_height, Container container) {
		this.container = container;
		this.clock = new Clock(this, 60);
		this.input = new Input(this);
		this.window = new Window(win_width, win_height, input, "CPU_Graphic-Engine");
		this.renderer = new Renderer(container.getCONTAINER_WIDTH(), container.getCONTAINER_HEIGHT());
		this.running = false;
		this.container.init();
	}

	public void start() {
		if(!this.running) {
			this.thread = new Thread(this);
			this.thread.start();
			this.running = true;
		}
	}
	public void stop() {
		System.out.println(this.running);
		if(this.running) {
			this.running = false;
			try {
				this.thread.join(3000);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	@Override
	public void run() {
		clock.init();
		while(this.running) {
			clock.update();
		}
		cleanUp();
	}
	
	public void refresh(double fD) {
		this.renderer.createFrame();
		this.container.tick(fD);
		this.container.render(this.renderer);
		
		this.window.repaint(renderer.getFrame());
	}
	
	private void cleanUp() {
		System.out.println("Cleaned");
		System.exit(1);
		
	}
	
}
