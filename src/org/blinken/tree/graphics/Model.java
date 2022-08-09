package org.blinken.tree.graphics;


public class Model {

    private final int[] indices;
    private final Vertex[] vertices;

    public Model(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public int[] getIndices() {
        return indices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }
}



