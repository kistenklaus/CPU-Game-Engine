package engine;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;


public class Window extends JFrame{
	
	private static final long serialVersionUID = -7975505597069675943L;
	
	public Window(int width, int height, Input input, String title) {
		super(title);
		this.setSize(width,height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.addMouseListener(input);
		this.addKeyListener(input);
		this.setVisible(true);
		
		this.setIgnoreRepaint(true);
		
		this.createBufferStrategy(2);
	}
	
	public void repaint(Frame frame) {
		BufferStrategy bs;
		if((bs=this.getBufferStrategy())!=null) {
			Graphics g = bs.getDrawGraphics();
			frame.render(g, getWidth(), getHeight());
			bs.show();
		}
		
	}
	
	
}
