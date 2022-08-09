package org.blinken.tree.display;

import org.blinken.tree.graphics.Texture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import org.blinken.tree.graphics.Graphics;

public class Display {

    private static final int VERTICAL_MARGIN = 40;
    private static final int HORIZONTAL_MARGIN = 20;
    private JFrame window;
    private BufferStrategy bs;
    private int width, height;

    private Graphics graphics;

    public Display(String title, int width, int height, int graphicsWidth, int graphicsHeight) {
        this.width = width;
        this.height = height;
        window = new JFrame(title);
        window.setSize(width, height);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.createBufferStrategy(2);
        bs = window.getBufferStrategy();
        window.isUndecorated();
        graphics = new Graphics(graphicsWidth, graphicsHeight);
    }

    public Graphics getGraphics(){
        return graphics;
    }

    public void swapBuffers() {
        Graphics2D g2 = null;
        do {
            try {
                g2 = (Graphics2D) bs.getDrawGraphics();
                final int canvasWidth = width - HORIZONTAL_MARGIN * 2;
                final int canvasHeight = height - VERTICAL_MARGIN * 2;
                final int screenWidth = graphics.getScreen().getWidth();
                final int screenHeight = graphics.getScreen().getHeight();
                int horMar = HORIZONTAL_MARGIN;
                int verMar = VERTICAL_MARGIN;
                int pixelSize;
                int ratioWidth = canvasWidth / screenWidth;
                int ratioHeight = canvasHeight / screenHeight;
                if (ratioWidth > ratioHeight) {
                    horMar = (canvasWidth - (ratioHeight * screenWidth)) / 2;
                    pixelSize = ratioWidth;
                } else {
                    verMar = (canvasHeight - (ratioWidth * screenHeight)) / 2;
                    pixelSize = ratioHeight;
                }
                for (int i = 0; i < graphics.getScreen().getWidth() * graphics.getScreen().getHeight(); i++) {
                    int x = i % graphics.getScreen().getWidth();
                    int y = Math.floorDiv(i, graphics.getScreen().getWidth());
                    int xpos = horMar + x * pixelSize;
                    int ypos = verMar + y * pixelSize;
                    g2.setColor(new Color(graphics.getScreen().getPixel(i)));
                    g2.fillRect(xpos, ypos, pixelSize, pixelSize);
                }
                drawWhatEver(g2);
            } finally {
                g2.dispose();
            }
            bs.show();
        } while (bs.contentsLost());
        graphics.clear();
    }

    private void drawWhatEver(Graphics2D g2) {

    }

}
