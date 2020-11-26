package org.delusion.engine.graphics.lighting;

/*
vec3 ambient;
vec3 diffuse;
vec3 specular;
float shininess;
 */

import org.joml.Vector3f;

public class Material {

    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private float shininess;

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

    public Material(Vector3f ambient, Vector3f diffuse, Vector3f specular, float shininess) {
        this.ambient = new Vector3f(ambient);
        this.diffuse = new Vector3f(diffuse);
        this.specular = new Vector3f(specular);
        this.shininess = shininess;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
}
