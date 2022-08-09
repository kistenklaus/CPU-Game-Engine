package org.blinken.tree.graphics;

import org.joml.Vector4f;

public class Graphics {

    private int width, height;
    private Texture screen;
    private int clearColor;
    private Camera camera;

    public Graphics(int width, int height) {
        this.width = width;
        this.height = height;
        screen = new Texture(width, height);
        camera = new Camera(width / (float) height, 0.1f, 10);
    }

    public void setPixel(int x, int y, float r, float g, float b, float a) {
        int color = rgbaToInt(r, g, b, a);
        setPixels(x, y, color);
    }

    public int rgbaToInt(float r, float g, float b, float a) {
        if (r > 1) r = 1;
        if (g > 1) g = 1;
        if (b > 1) b = 1;
        if (a > 1) a = 1;
        int bb = (int) (255 * b);
        int gb = ((int) (255 * g) << 8);
        int rb = ((int) (255 * r) << 16);
        int ab = ((int) (255 * a) << 24);
        return rb | gb | bb | ab;
    }

    public void setPixels(int x, int y, int color) {
        int pixelIndex = x + y * width;
        screen.setPixel(pixelIndex, color);
    }

    public void setPixel(float x, float y, float r, float g, float b, float a) {
        int xi = (int) (width / 2.0f + x * width / 2f);
        int yi = (int) (height / 2.0f + y * height / 2f);
        setPixel(xi, yi, r, g, b, a);
    }

    public void drawModel(Model model, Transform transform) {
        //transform all vertices
        final Vertex[] vertices = model.getVertices();

        //position = model * vertices
        for (Vertex vertex : vertices) {
            //VERTEX_SHADER
            Vector4f worldSpace = transform.getModelMatrix().transform(
                    new Vector4f(vertex.getPosition(), 1.0f));
            vertex.setWorldSpace(worldSpace.x, worldSpace.y, worldSpace.z);

            Vector4f projSpace = camera.getProjection().transform(new Vector4f(worldSpace));
            vertex.setProjectionSpace(projSpace);

            //PERSPECTIVE_DIVISION
            float w = vertex.getProjectionSpace().w;
            vertex.getProjectionSpace().div(w, w, w, w);

            //Screen Space Transform
            vertex.setFragPos(width / 2f + vertex.getProjectionSpace().x * width / 2f,
                    height / 2f + vertex.getProjectionSpace().y * height / 2f);
        }
        //ELEMENTS (INDEXING)
        int[] indices = model.getIndices();
        for (int i = 0; i + 2 < indices.length; i += 3) {
            Vertex f1 = vertices[indices[i]];
            Vertex f2 = vertices[indices[i + 1]];
            Vertex f3 = vertices[indices[i + 2]];
            drawTriangle(f1, f2, f3);
        }
    }

    /**
     * @param v1 vectors in screen space [0, width]
     * @param v2
     * @param v3
     */
    public void drawTriangle(Vertex v1, Vertex v2, Vertex v3) {
        if (v1.getScreenSpace().y > v2.getScreenSpace().y) {
            Vertex temp = v1;
            v1 = v2;
            v2 = temp;
        }

        if (v2.getScreenSpace().y > v3.getScreenSpace().y) {
            Vertex temp = v2;
            v2 = v3;
            v3 = temp;
        }

        if (v1.getScreenSpace().y > v2.getScreenSpace().y) {
            Vertex temp = v1;
            v1 = v2;
            v2 = temp;
        }
        //f1 is the lowest f2 is in the middle and f3 is at the top
        if (v1.getScreenSpace().y == v2.getScreenSpace().y) {
            //draw topTriangle
            //f2 is right
            if (v2.getScreenSpace().x < v1.getScreenSpace().x) {
                Vertex temp = v1;
                v1 = v2;
                v2 = temp;
            }
            rasterizeFlatBottomTriangle(v1, v2, v3);
        } else if (v2.getScreenSpace().y == v3.getScreenSpace().y) {
            //draw bottomTriangle
            //f3 is right
            if (v3.getScreenSpace().x < v2.getScreenSpace().x) {
                Vertex temp = v3;
                v3 = v2;
                v2 = temp;
            }
            rasterizeFlatTopTriangle(v2, v3, v1);
        } else {
            //find middle point
            float dyf2 = v3.getProjectionSpace().y - v2.getProjectionSpace().y;
            float dyf1 = v3.getProjectionSpace().y - v1.getProjectionSpace().y;
            float ratio = dyf2 / dyf1;
            float dx = v3.getProjectionSpace().x - v1.getProjectionSpace().y;
            float mx = v3.getProjectionSpace().x + ratio * dx;
            //(1-ratio) * f3 + ratio * f1
            Vertex f2m = Vertex.learp(ratio, v1, v3);
            //right middle = f2m
            if (f2m.getScreenSpace().x < v2.getScreenSpace().x) {
                Vertex temp = f2m;
                f2m = v2;
                v2 = temp;
            }

            rasterizeFlatBottomTriangle(v2, f2m, v3);
            rasterizeFlatTopTriangle(v2, f2m, v1);
        }
    }

    private void rasterizeFlatBottomTriangle(Vertex lBottom, Vertex rBottom, Vertex top) {
        float m0 = (lBottom.getScreenSpace().x - top.getScreenSpace().x)
                / (rBottom.getScreenSpace().y - top.getScreenSpace().y);
        float m1 = (rBottom.getScreenSpace().x - top.getScreenSpace().x)
                / (rBottom.getScreenSpace().y - top.getScreenSpace().y);

        int yStart = (int) Math.ceil(lBottom.getScreenSpace().y - 0.5f);
        int yEnd = (int) Math.ceil(top.getScreenSpace().y - 0.5f);

        for (int y = Math.max(0,yStart); y < Math.min(height,yEnd); y++) {
            float px0 = m0 * (y + 0.5f - lBottom.getScreenSpace().y) + lBottom.getScreenSpace().x;
            float px1 = m1 * (y + 0.5f - rBottom.getScreenSpace().y) + rBottom.getScreenSpace().x;

            int xStart = (int) Math.ceil(px0 - 0.5f);
            int xEnd = (int) Math.ceil(px1 - 0.5f);

            for (int x = Math.max(0,xStart); x < Math.min(width,xEnd); x++) {
                setPixel(x, y, 1, 0, 0, 1);
            }
        }

    }

    private void rasterizeFlatTopTriangle(Vertex lTop, Vertex rTop, Vertex bottom) {
        float m0 = (bottom.getScreenSpace().x - lTop.getScreenSpace().x)
                / (bottom.getScreenSpace().y - lTop.getScreenSpace().y);
        float m1 = (bottom.getScreenSpace().x - rTop.getScreenSpace().x)
                / (bottom.getScreenSpace().y - rTop.getScreenSpace().y);

        int yStart = (int) Math.ceil(bottom.getScreenSpace().y - 0.5f);
        int yEnd = (int) Math.ceil(lTop.getScreenSpace().y - 0.5f);

        for (int y = Math.max(0,yStart); y < Math.min(height,yEnd); y++) {
            float px0 = m0 * (y + 0.5f - lTop.getScreenSpace().y) + lTop.getScreenSpace().x;
            float px1 = m1 * (y + 0.5f - rTop.getScreenSpace().y) + rTop.getScreenSpace().x;

            int xStart = (int) Math.ceil(px0 - 0.5f);
            int xEnd = (int) Math.ceil(px1 - 0.5f);
            for (int x = Math.max(0,xStart); x < Math.min(width,xEnd); x++) {
                setPixel(x, y, 1, 0, 0, 1);
            }
        }

    }

    public Texture getScreen() {
        return screen;
    }

    public void setClearColor(Vector4f clearColor) {
        this.clearColor = rgbaToInt(clearColor.x, clearColor.y, clearColor.z, clearColor.z);
    }

    public void clear() {
        for (int i = 0; i < screen.getWidth() * screen.getHeight(); i++) {
            screen.setPixel(i, clearColor);
        }
    }
}
