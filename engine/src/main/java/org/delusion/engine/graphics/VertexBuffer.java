package org.delusion.engine.graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.opengl.GL46.*;

public class VertexBuffer {
    private static Logger LOGGER = LogManager.getLogger(VertexBuffer.class);

    private int buffer;

    @Override
    public String toString() {
        return "<VertexBuffer " + buffer + ">";
    }

    private float[] data;
    private int mode = GL_STATIC_DRAW;

    public VertexBuffer() {
        buffer = glCreateBuffers();
        bind();
        unbind();
        LOGGER.info("Created Empty VBO {}", buffer);
    }

    public VertexBuffer(float[] vertices) {
        buffer = glCreateBuffers();
        setData(vertices);
        unbind();
        LOGGER.info("Created VBO {}", buffer);
    }

    public VertexBuffer(float[] vertices, int mode) {
        buffer = glCreateBuffers();
        setData(vertices, mode);
        unbind();
        LOGGER.info("Created VBO {}", buffer);
    }

    public void push() {
        bind();
        glBufferData(GL_ARRAY_BUFFER, data, mode);
        LOGGER.info("{} Repushed Data", this);
    }

    public void setData(float[] vertices) {
        data = vertices;
        bind();
        glBufferData(GL_ARRAY_BUFFER, vertices, mode);
        LOGGER.info("{} Pushed New Data", this);
    }

    public void setData(float[] vertices, int mode) {
        data = vertices;
        this.mode = mode;
        bind();
        glBufferData(GL_ARRAY_BUFFER, vertices, mode);
        LOGGER.info("{} Pushed New Data", this);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, buffer);
    }

    public void unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
