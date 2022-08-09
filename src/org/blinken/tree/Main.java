package org.blinken.tree;

import org.blinken.tree.display.Display;
import org.blinken.tree.graphics.Graphics;
import org.blinken.tree.graphics.Model;
import org.blinken.tree.graphics.Transform;
import org.blinken.tree.graphics.Vertex;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Main {

    public static void main(String[] args) {
        float upscale = 4f;
        Display display = new Display("debug", 600, 600,
                (int) (64 * upscale), (int) (64 * upscale));

        Model model = new Model(new Vertex[]{
                new Vertex(new Vector3f(-0.5f, 0.5f, -0.5f)),
                new Vertex(new Vector3f(0.5f, 0.5f, -0.5f)),
                new Vertex(new Vector3f(0.5f, -0.5f, -0.5f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f)),

                new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f)),
                new Vertex(new Vector3f(0.5f, 0.5f, 0.5f)),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.5f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.5f)),
        }, new int[]{
                0, 1, 2,
                2, 3, 0,

                4, 5, 6,
                6, 7, 4,

                0, 4, 7,
                7, 3, 0,

                5, 1, 6,
                6, 1, 2,

                0, 4, 5,
                5, 1, 0,

                3, 7, 6,
                6, 2, 3
        });

        Transform transform = new Transform(
                new Vector3f(0, 0, 1.5f),
                new Vector3f(1, 1, 1),
                new Vector3f(0, 0, 0));

        Graphics graphics = display.getGraphics();
        graphics.setClearColor(new Vector4f(0,0,0,1));


        long last = System.nanoTime();
        float dt = 0;
        final float INV_FPS = 1f / 400f;
        int frameCount = 0;
        final float fpsDebugInterval = 2f;
        float fpsDebugTimer = 0;
        while (true) {
            long curr = System.nanoTime();
            float delta = (curr - last) * 1e-9f;
            last = curr;

            dt += delta;

            if (dt >= INV_FPS) {
                frameCount++;
                graphics.drawModel(model, transform);
                transform.rotateX(0.25f * dt);
                transform.rotateZ(1 * dt);
                transform.rotateY(0.5f * dt);
                display.swapBuffers();
                dt = 0;
            }

            fpsDebugTimer += delta;
            if(fpsDebugTimer >= fpsDebugInterval){
                System.out.println("FPS: " + frameCount / fpsDebugInterval);
                frameCount = 0;
                fpsDebugTimer -= fpsDebugInterval;
            }

        }

    }
}
