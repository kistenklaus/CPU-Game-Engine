package engine;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import engine.gfx.Renderer;
import engine.window.Frame;
import engine.window.Window;

public class Engine implements Runnable{
	private Window window;
	private Renderer renderer;
	private Thread engineThread;
	private Logic logic;
	private boolean running;
	public Engine(int width, int height, Logic logic) {
		this.running = false;
		this.window = new Window(width,height,new WindowEventHandler(this),"CPU_Graphic-Engine");
		this.renderer = new Renderer(width,height);
		this.logic = logic;
		this.logic.setWindowWidth(width);
		this.logic.setWindowHeight(height);
		this.logic.init();
	}
	
	public void start() {
		if (!this.running) {
			this.engineThread = new Thread(this);
			this.running = true;
			this.engineThread.start();
		}
	}

	public void stop() {
		if(this.running) {
			this.running = false;
			try {
				this.engineThread.join(500);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	@Override
	public void run() {
		long lastFrame = System.nanoTime();
		
		while(this.running) {
			Frame frame = this.window.introduceFrame();
			renderer.setFrame(frame);
			renderer.clearPixels(0x000000);
			this.logic.drawFrame(this.renderer);
			this.window.paintFrame(frame);
			
			
			long currFrame = System.nanoTime();
			double delta = (currFrame-lastFrame)/1000000000d;
			lastFrame = currFrame;
			@SuppressWarnings("unused")
			int fps = (int) (1/delta);
//			System.out.println(fps);
			
			
		}
		cleanUp();
	}
	private void cleanUp() {
		this.logic.cleanUp();
	}
	
	
	private class WindowEventHandler implements WindowListener{
		private Engine engine;
		public WindowEventHandler(Engine engine) {
			this.engine = engine;
		}
		public void windowActivated(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}
		public void windowClosing(WindowEvent arg0) {
			this.engine.stop();
		}
		public void windowDeactivated(WindowEvent arg0) {}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowOpened(WindowEvent arg0) {}
	}
}
