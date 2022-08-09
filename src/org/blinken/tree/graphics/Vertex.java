package org.blinken.tree.graphics;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public final class Vertex {

    //actual parts of the vertex
    private final Vector3f position;

    //computable
    private Vector2f screenSpace;
    private Vector4f projectionSpace;
    private Vector3f worldSpace;

    public Vertex(Vector3f position) {
        this.position = position;
    }

    private Vertex(Vector3f position, Vector3f projectionSpace, Vector3f worldSpace, Vector2f fragPos) {
        this.position = position;
        this.screenSpace = fragPos;
    }

    public static Vertex learp(float alpha, Vertex v1, Vertex v2) {
        Vector3f worldSpace = new Vector3f(v1.worldSpace).mul(alpha).add(new Vector3f(v2.worldSpace).mul(1 - alpha));
        Vector2f screenSpace = new Vector2f(v1.getScreenSpace()).mul(alpha).add(new Vector2f(v2.getScreenSpace()).mul(1 - alpha));
        return new Vertex(null, null, worldSpace, screenSpace);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector2f getScreenSpace() {
        return screenSpace;
    }

    public void setScreenSpace(Vector2f screenSpace) {
        this.screenSpace = screenSpace;
    }

    public void setFragPos(float x, float y) {
        this.screenSpace = new Vector2f(x, y);
    }

    public Vector4f getProjectionSpace() {
        return projectionSpace;
    }

    public void setProjectionSpace(Vector4f projectionSpace) {
        this.projectionSpace = projectionSpace;
    }

    public Vector3f getWorldSpace() {
        return worldSpace;
    }

    public void setWorldSpace(Vector3f worldSpace) {
        this.worldSpace = worldSpace;
    }

    public void setWorldSpace(float x, float y, float z) {
        setWorldSpace(new Vector3f(x, y, z));
    }
}
