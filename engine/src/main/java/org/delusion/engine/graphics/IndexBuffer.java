package org.delusion.engine.graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.opengl.GL46.*;

public class IndexBuffer {
    private static Logger LOGGER = LogManager.getLogger(IndexBuffer.class);

    private int buffer;
    private int[] data;
    private int mode = GL_STATIC_DRAW;

    @Override
    public String toString() {
        return "<IndexBuffer " + buffer + ">";
    }

    public IndexBuffer() {
        buffer = glCreateBuffers();
        bind();
        unbind();
        LOGGER.info("Created Empty EBO {}", this);
    }

    public IndexBuffer(int[] indicies) {
        buffer = glCreateBuffers();
        setData(indicies);
        unbind();
        LOGGER.info("Created EBO {}", this);
    }

    public IndexBuffer(int[] indicies, int mode) {
        buffer = glCreateBuffers();
        setData(indicies, mode);
        unbind();
        LOGGER.info("Created EBO {}", this);
    }

    public void push() {
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, mode);
        LOGGER.info("{} Repushed Data", this);
    }

    public void setData(int[] indicies) {
        data = indicies;
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicies, mode);
        LOGGER.info("{} Pushed New Data", this);
    }

    public void setData(int[] indicies, int mode) {
        data = indicies;
        this.mode = mode;
        bind();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicies, mode);
        LOGGER.info("{} Pushed New Data", this);
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer);
    }

    public void unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int bufferID() {
        return buffer;
    }
}