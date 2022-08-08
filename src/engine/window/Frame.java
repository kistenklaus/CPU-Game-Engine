package engine.window;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Frame {
	private BufferedImage frame;
	private int[] pixels;
	private int width, height;
	public Frame(int width, int height) {
		this.width = width;
		this.height = height;
	}
	private void paintPixels() {
		this.frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.frame.setRGB(0, 0, width, height, this.pixels, 0, width);
	}
	public void setPixel(int x, int y, int color, int alpha) {
		int pixelIndex = (height-y-1)*width+x;
		this.pixels[pixelIndex] = color-0xFFFFFF;
	}
	
	public void setRawPixels(int[] pixels) {
		this.pixels = pixels;
	}
	public int[] getPixels() {
		return this.pixels;
	}
	public void draw(Graphics g) {
		paintPixels();
		g.drawImage(frame, 0, 0, null);
	}
	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
}
