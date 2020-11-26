package org.delusion.engine.graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.opengl.GL46.*;

public class VertexArray {
    private static Logger LOGGER = LogManager.getLogger(VertexArray.class);

    private int handle;

    @Override
    public String toString() {
        return "<VertexArray " + handle + ">";
    }

    public VertexArray() {
        handle = glCreateVertexArrays();
        LOGGER.info("Created VAO {}", handle);
    }

    public void attribute(VertexBuffer buffer, int index, int size, int stride, long pointer) {
        LOGGER.info("Attached VBO {} as attribute (ind: {}, size: {}, stride: {}, pointer: {})", buffer, index, size, stride, pointer);

        bind();
        buffer.push();
        glVertexAttribPointer(index, size, GL_FLOAT, false, stride * Float.BYTES, pointer);
        glEnableVertexAttribArray(index);
        unbind();
        buffer.unbind();
    }

    public void bind() {
        glBindVertexArray(handle);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public void elementBuffer(IndexBuffer ebo) {
        glVertexArrayElementBuffer(handle, ebo.bufferID());
    }
}
