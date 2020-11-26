package org.delusion.engine.graphics;

import org.delusion.engine.graphics.lighting.DirectionalLight;
import org.delusion.engine.graphics.primitive.Cube;
import org.delusion.engine.graphics.primitive.Quad;
import org.delusion.engine.graphics.shader.ShaderProgram;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL46.*;

public class Renderer {

    private Matrix4f projection = new Matrix4f().perspective(1.02974426f, 16.0f / 9.0f, 0.01f, 100f, false);

    private Camera camera;

    private ShaderProgram defaultShader;
    private boolean hasDefaultShader;
    private boolean isProjectionDirty = true;
    private DirectionalLight light;

    public Renderer() {
        this(new Camera(true));
    }

    public Renderer(Camera camera) {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_FRAMEBUFFER_SRGB);
        this.camera = camera;
    }

    public void cullFront() {
        glCullFace(GL_FRONT);
    }

    public void cullBack() {
        glCullFace(GL_BACK);
    }

    public void cullBoth() {
        glCullFace(GL_FRONT_AND_BACK);
    }

    public void renderQuad(Quad quad) {
        bindAndUpdateDefaultShader(quad);
        drawElements(quad.getVAO(),6);
        unbindDefaultShader();
    }

    public void renderCube(Cube cube) {
        bindAndUpdateDefaultShader(cube);
        draw(cube.getVAO(), 216);
        unbindDefaultShader();
    }

    private void bindAndUpdateDefaultShader(Cube cube) {
        if (hasDefaultShader) {
            defaultShader.bind();
            if (isProjectionDirty) {
                updateProjection(defaultShader, "projection");
            }

            if (camera.isDirty()) {
                updateView(defaultShader, "view");
            }

            defaultShader.uniformMat4("model", cube.model());

            defaultShader.uniform3f("viewPos", camera.getPosition());
            defaultShader.uniformDirectionalLight("light", light);
            defaultShader.uniformMaterial("material", cube.getMaterial());

        }
    }

    private void bindAndUpdateDefaultShader(Quad quad) {
        if (hasDefaultShader) {
            defaultShader.bind();
            if (isProjectionDirty) {
                updateProjection(defaultShader, "projection");
            }

            if (camera.isDirty()) {
                updateView(defaultShader, "view");
            }

            defaultShader.uniformMat4("model", quad.model());
            defaultShader.uniform4f("uColor", quad.getColor());
            defaultShader.uniform3f("viewPos", camera.getPosition());
        }
    }

    private void updateProjection(ShaderProgram program, String uniformName) {
        program.uniformMat4(uniformName, projection);
        isProjectionDirty = false;
    }

    private void updateView(ShaderProgram program, String uniformName) {
        program.uniformMat4(uniformName, camera.view());
    }


    private void unbindDefaultShader() {
        if (hasDefaultShader) defaultShader.unbind();
    }

    public void background(float r, float g, float b, float a) {
        glClearColor(r, g, b, a);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    }

    public void draw(VertexArray vao, int count) {
        vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, count);
        vao.unbind();
    }

    public void drawElements(VertexArray vao, int count) {
        vao.bind();
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0L);
        vao.unbind();
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setDefaultShader(ShaderProgram program) {
        this.hasDefaultShader = true;
        this.defaultShader = program;
    }

    public void light(DirectionalLight light) {
        this.light = light;
    }

    public void enableMSAA() {
        glEnable(GL_MULTISAMPLE);
    }

    public void renderCubes(Cube... cubes) {
        bindAndUpdateDefaultShader();
        for (Cube cube : cubes) {
            updateDefaultShader_onlyModel(cube);
            draw(cube.getVAO(), 216);
        }

        unbindDefaultShader();

    }

    private void bindAndUpdateDefaultShader() {
        if (hasDefaultShader) {
            defaultShader.bind();
            if (isProjectionDirty) {
                updateProjection(defaultShader, "projection");
            }

            if (camera.isDirty()) {
                updateView(defaultShader, "view");
            }

            defaultShader.uniform3f("viewPos", camera.getPosition());
            defaultShader.uniformDirectionalLight("light", light);
        }
    }

    private void updateDefaultShader_onlyModel(Cube cube) {
        defaultShader.uniformMaterial("material", cube.getMaterial());
        defaultShader.uniformMat4("model", cube.model());
    }
}
