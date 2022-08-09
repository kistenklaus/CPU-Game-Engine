package org.blinken.tree.graphics;


import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {

    private final Vector3f position;
    private final Vector3f scale;
    private final Vector3f rotation;

    private final Matrix4f modelMatrix;

    public Transform(Vector3f position, Vector3f scale, Vector3f rotation){
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
        modelMatrix = new Matrix4f();
        calculateModelMatrix();
    }

    private void calculateModelMatrix(){
        modelMatrix.identity();

        modelMatrix.translate(position);
        modelMatrix.scale(scale);
        modelMatrix.rotate(rotation.x, new Vector3f(1,0,0));
        modelMatrix.rotate(rotation.y, new Vector3f(0,1,0));
        modelMatrix.rotate(rotation.z,new Vector3f(0,0,1));

    }


    public void rotateZ(float alpha){
        rotation.z += alpha;
        calculateModelMatrix();
    }

    public void rotateY(float alpha) {
        rotation.y += alpha;
        calculateModelMatrix();
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }


    public void rotateX(float alpha) {
        rotation.x += alpha;
        calculateModelMatrix();
    }
}
