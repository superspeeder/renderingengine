package org.delusion.engine.graphics;

import org.joml.*;

import java.lang.Math;

public class Camera {

    private Vector3f position;
    private Quaternionf rotation;
    private boolean invertZ;

    private boolean dirty;
    private Matrix4f cachedMatrix;
    private Quaternionf rotationV;
    private float rv;

    public Camera(Vector3f position, Quaternionf rotation, boolean invertZ) {
        this.position = new Vector3f(position);
        this.rotation = new Quaternionf(rotation);
        this.rotationV = new Quaternionf();
        this.invertZ = invertZ;
        dirty = true;
    }

    public Camera(Vector3f position) {
        this(position, new Quaternionf(), false);
    }

    public Camera(Quaternionf rotation) {
        this(new Vector3f(), rotation, false);
    }

    public Camera(boolean invertZ) {
        this(new Vector3f(), new Quaternionf(), invertZ);
    }

    public Camera(Vector3f position, Quaternionf rotation) {
        this(position, rotation, false);
    }

    public Camera(Quaternionf rotation, boolean invertZ) {
        this(new Vector3f(), rotation, invertZ);
    }

    public Camera(Vector3f position, boolean invertZ) {
        this(position, new Quaternionf(), invertZ);
    }

    public Camera() {
        this(new Vector3f(), new Quaternionf(),false);
    }

    public Matrix4f view() {
        if (dirty) {
            if (invertZ) {
                cachedMatrix = new Matrix4f().scale(1,1,-1).rotate(rotationV).rotate(rotation).translate(new Vector3f(position).mul(-1));
            } else {
                cachedMatrix = new Matrix4f().rotate(rotationV).rotate(rotation).translate(new Vector3f(position).mul(-1));
            }
        }
        return cachedMatrix;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void translate(float x, float y, float z) {
        markDirty();
        position.add(x, y, z);
    }

    private void markDirty() {
        dirty = true;

    }

    public void rotate(float angle, float x, float y, float z) {
        rotation.rotateAxis(angle, x, y, z);
        markDirty();
    }

    public Vector3f forward() {
        return rotation.transform(new Vector3f(0,0,1)).mul(1,1,-1);
    }

    public void translate(Vector3f diff) {
        translate(diff.x,diff.y,diff.z);
    }

    public Vector3f left() {
        return forward().rotateY((float) (-Math.PI / 2f));
    }

    public void rotate(float v, Vector3f axis) {
        rotate(v, axis.x, axis.y, axis.z);
    }

    public void rotateV(float v) {
        if (rv + v >= (Math.PI / 2f)) {
            v = (float) ((Math.PI / 2f) - rv);
        } else if (rv + v <= (-Math.PI / 2f)) {
            v = (float) ((-Math.PI / 2f) - rv);
        }

        rotationV.rotateAxis(v,1,0,0);
        rv += v;
        markDirty();
    }

    public float vrot() {
        return rv;
    }

    public Vector3f getPosition() {
        return new Vector3f(position);
    }
}
