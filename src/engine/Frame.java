package engine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Frame {
	private int[] pixels;
	private int con_width,con_height;
	public Frame(int width, int height, int bgColor) {
		this.pixels = new int[width*height];
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = bgColor-0xFFFFFF;
		}
		this.con_width = width;
		this.con_height = height;
	}
	public int[] getPixels() {
		return this.pixels;
	}
	public void setPixels() {
		
	}
	public void putPixel(int x, int y, int color, float alpha) {
		int pixelIndex = x+(con_height-y-1)*con_width;
		this.pixels[pixelIndex] = color;
	}
	public void render(Graphics g, int win_width, int win_height) {
		BufferedImage container = new BufferedImage(con_width, con_height, BufferedImage.TYPE_INT_RGB);
		container.setRGB(0, 0, con_width, con_height, this.pixels, 0, con_width);
		g.drawImage(container, 0, 0, win_width, win_height, null);
	}
	public int getWidth() {
		return this.con_width;
	}
	public int getHeight() {
		return this.con_height;
	}
}
