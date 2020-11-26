package org.delusion.engine.graphics.primitive;

import org.delusion.engine.graphics.IndexBuffer;
import org.delusion.engine.graphics.Renderer;
import org.delusion.engine.graphics.VertexArray;
import org.delusion.engine.graphics.VertexBuffer;
import org.delusion.engine.graphics.lighting.Material;
import org.joml.*;

public class Cube {

    private static float[] cube_vbo_dat = {
            // front
            0,0,0, // blf
            1,0,0, // brf
            0,1,0, // tlf
            1,0,0, // brf
            1,1,0, // tlf
            0,1,0, // trf

            // back
            1,0,1, // brb
            0,0,1, // blb
            0,1,1, // tlb
            1,1,1, // tlb
            1,0,1, // brb
            0,1,1, // trb

            // left
            0,0,0, // blf
            0,1,1, // tlb
            0,0,1, // blb
            0,1,1, // tlb
            0,0,0, // blf
            0,1,0, // tlf

            // right
            1,1,1, // trb
            1,0,0, // brf
            1,0,1, // brb
            1,0,0, // brf
            1,1,1, // trb
            1,1,0, // trf

            // bottom
            0,0,0, // blf
            0,0,1, // blb
            1,0,1, // brb
            1,0,0, // brf
            0,0,0, // blf
            1,0,1, // brb

            // top
            0,1,0, // tlf
            1,1,1, // trb
            0,1,1, // tlb
            0,1,0, // tlf
            1,1,0, // trf
            1,1,1, // trb
    };

    private static float[] cube_vbo_norm_dat = {
            // front
            0,0,-1,
            0,0,-1,
            0,0,-1,
            0,0,-1,
            0,0,-1,
            0,0,-1,

            // back
            0,0,1,
            0,0,1,
            0,0,1,
            0,0,1,
            0,0,1,
            0,0,1,

            // left
            -1,0,0,
            -1,0,0,
            -1,0,0,
            -1,0,0,
            -1,0,0,
            -1,0,0,

            // right
            1,0,0,
            1,0,0,
            1,0,0,
            1,0,0,
            1,0,0,
            1,0,0,

            // bottom
            0,-1,0,
            0,-1,0,
            0,-1,0,
            0,-1,0,
            0,-1,0,
            0,-1,0,

            // top
            0,1,0,
            0,1,0,
            0,1,0,
            0,1,0,
            0,1,0,
            0,1,0,
    };


    private VertexBuffer vboN;

    private VertexArray vao;
    private VertexBuffer vbo;
    private Vector3f position;
    private Vector3f size;
    private Quaternionf rotation;

    private boolean dirty;
    private Matrix4f cachedModelMatrix;
    private Material material;

    public Cube(Vector3f position, Material material) {
        this(position, new Vector3f(1,1, 1), new Quaternionf(), material);
    }

    public Cube(Vector3f position, Quaternionf rotation, Material material) {
        this(position, new Vector3f(1,1, 1), rotation, material);
    }

    public Cube(Vector3f position, Vector3f size, Material material) {
        this(position, size, new Quaternionf(), material);
    }

    public Cube(Vector3f position, Vector3f size, Quaternionf rotation, Material material) {
        this.position = new Vector3f(position);
        this.size = new Vector3f(size);
        this.rotation = new Quaternionf(rotation);
        this.material = material;
        this.dirty = true;
        vao = new VertexArray();
        vao.bind();

        vbo = new VertexBuffer(cube_vbo_dat);
        vao.attribute(vbo,0,3,3, 0);

        vboN = new VertexBuffer(cube_vbo_norm_dat);
        vao.attribute(vboN,1,3,3, 0);
    }

    public Matrix4f model() {
        if (isDirty()) {
            cachedModelMatrix = new Matrix4f().translate(position).scale(size).rotate(rotation);
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

    public void rotate(float angle, float x, float y, float z) {
        rotation.rotateAxis(angle, x, y, z);
        markDirty();
    }

    public VertexArray getVAO() {
        return vao;
    }

    public void setPosition(Vector3f position) {
        this.position = new Vector3f(position);
        markDirty();
    }

    public Material getMaterial() {
        return material;
    }
}
