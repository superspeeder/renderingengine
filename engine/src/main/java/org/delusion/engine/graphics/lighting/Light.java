package org.delusion.engine.graphics.lighting;

import org.joml.Vector3f;
import org.joml.Vector4f;

public abstract class Light {
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;

    public Light(Vector3f ambient, Vector3f diffuse, Vector3f specular) {
        setAmbient(ambient);
        setDiffuse(diffuse);
        setSpecular(specular);
    }

    public Vector3f getAmbient() {
        return new Vector3f(ambient);
    }

    public void setAmbient(Vector3f ambient) {
        this.ambient = new Vector3f(ambient);
    }

    public Vector3f getDiffuse() {
        return new Vector3f(diffuse);
    }

    public void setDiffuse(Vector3f diffuse) {
        this.diffuse = new Vector3f(diffuse);
    }

    public Vector3f getSpecular() {
        return new Vector3f(specular);
    }

    public void setSpecular(Vector3f specular) {
        this.specular = new Vector3f(specular);
    }
}
