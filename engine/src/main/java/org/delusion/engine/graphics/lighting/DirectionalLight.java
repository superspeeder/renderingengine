package org.delusion.engine.graphics.lighting;

import org.joml.Vector3f;

public class DirectionalLight extends Light {
    private Vector3f direction;

    public DirectionalLight(Vector3f ambient, Vector3f diffuse, Vector3f specular, Vector3f direction) {
        super(ambient, diffuse, specular);
        setDirection(direction);
    }

    public Vector3f getDirection() {
        return new Vector3f(direction);
    }

    public void setDirection(Vector3f direction) {
        this.direction = new Vector3f(direction);
    }
}
