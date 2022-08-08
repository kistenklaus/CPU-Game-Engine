package engine.window;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window extends JFrame {
	private static final long serialVersionUID = 6851444850395667430L;
	
	private BufferStrategy bufferStrat;
	private int width, height;
	public Window(int width, int height, WindowListener windowListener, String title) {
		super(title);
		Dimension dim = new Dimension(width, height);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);
		this.setPreferredSize(dim);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(false);
		this.setVisible(true);
		this.createBufferStrategy(2);
		this.width = width;
		this.height = height;
		this.addWindowListener(windowListener);
	}
	public Frame introduceFrame(){
		return new Frame(width, height);
	}
	
	public void paintFrame(Frame frame) {
			this.bufferStrat = this.getBufferStrategy();
			Graphics g = this.bufferStrat.getDrawGraphics();
			frame.draw(g);
			g.dispose();
			this.bufferStrat.show();
	}
	
}
