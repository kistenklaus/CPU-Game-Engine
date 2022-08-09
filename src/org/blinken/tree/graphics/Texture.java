package org.blinken.tree.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {

    private int height, width;
    private int[] pixels;

    public Texture(int width, int height){
        this.width = width;
        this.height = height;
        pixels = new int[width*height];
    }

    public Texture(String path){
        try {
            BufferedImage img = ImageIO.read(new File(path));
            this.width = img.getWidth();
            this.height = img.getHeight();
            pixels = img.getData().getPixels(0,0,img.getWidth(),img.getHeight(), (int[])null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPixel(int pixelIndex, int color){
        pixels[pixelIndex] = color;
    }

    public int getPixel(int pixelIndex){
        return pixels[pixelIndex];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int[] getPixels() {
        return pixels;
    }
}
