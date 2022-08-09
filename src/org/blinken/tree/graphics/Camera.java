package org.blinken.tree.graphics;

import org.joml.Matrix4f;

public class Camera {

    private Matrix4f projection;

    public Camera(float aspect, float near, float far) {
        projection = new Matrix4f()
                .perspective(20, aspect, near, far);
    }

    public Matrix4f getProjection() {
        return projection;
    }
}
