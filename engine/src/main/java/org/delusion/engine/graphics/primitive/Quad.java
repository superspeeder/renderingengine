package org.delusion.engine.graphics.primitive;

import org.delusion.engine.graphics.IndexBuffer;
import org.delusion.engine.graphics.Renderer;
import org.delusion.engine.graphics.VertexArray;
import org.delusion.engine.graphics.VertexBuffer;
import org.joml.*;

public class Quad {

    private static float[] quad_vbo_dat = {
            -0.5f,-0.5f,-0.5f,
            -0.5f,0.5f,-0.5f,
            0.5f,-0.5f,-0.5f,
            0.5f,0.5f,-0.5f
    };


    private static int[] quad_ebo_dat = {
            0,2,1, // bl br tl
            1,2,3 // tl br tr
    };
    private static float[] quad_vbo_norm_dat = {
            0,0,-1,
            0,0,-1,
            0,0,-1,
            0,0,-1
    };

    private IndexBuffer ebo;
    private VertexBuffer vbo;
    private VertexBuffer vboN;
    private Vector3f position;
    private Vector2f size;
    private Quaternionf rotation;
    private Vector4f color;
    private VertexArray vao;

    private boolean dirty;
    private Matrix4f cachedModelMatrix;

    public Quad(Vector3f position) {
        this(position, new Vector2f(1,1), new Quaternionf(), new Vector4f(0,0,0,1));
    }

    public Quad(Vector3f position, Quaternionf rotation) {
        this(position, new Vector2f(1,1), rotation, new Vector4f(0,0,0,1));
    }

    public Quad(Vector3f position, Vector2f size) {
        this(position, size, new Quaternionf(), new Vector4f(0,0,0,1));
    }

    public Quad(Vector3f position, Vector2f size, Quaternionf rotation) {
        this(position, size, rotation, new Vector4f(0,0,0,1));
    }

    public Quad(Vector3f position, Vector4f color) {
        this(position, new Vector2f(1,1), new Quaternionf(), color);
    }

    public Quad(Vector3f position, Quaternionf rotation, Vector4f color) {
        this(position, new Vector2f(1,1), rotation, color);
    }

    public Quad(Vector3f position, Vector2f size, Vector4f color) {
        this(position, size, new Quaternionf(), color);
    }

    public Quad(Vector3f position, Vector2f size, Quaternionf rotation, Vector4f color) {
        this.position = new Vector3f(position);
        this.size = new Vector2f(size);
        this.rotation = new Quaternionf(rotation);
        this.color = new Vector4f(color);
        this.dirty = true;
        vao = new VertexArray();
        vao.bind();

        vbo = new VertexBuffer(quad_vbo_dat);
        vao.attribute(vbo,0,3,3, 0);

        vboN = new VertexBuffer(quad_vbo_norm_dat);
        vao.attribute(vboN,1,3,3, 0);

        ebo = new IndexBuffer(quad_ebo_dat);
        vao.elementBuffer(ebo);
    }

    public Matrix4f model() {
        if (isDirty()) {
            cachedModelMatrix = new Matrix4f().translate(position).scale(size.x, 1f, size.y).rotate(rotation);
            dirty = false;
        }
        return cachedModelMatrix;
    }


    private void markDirty() {
        dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public Vector4f getColor() {
        return color;
    }

    public void rotate(float angle, float x, float y, float z) {
        rotation.rotateAxis(angle, x, y, z);
        markDirty();
    }

    public VertexArray getVAO() {
        return vao;
    }
}
