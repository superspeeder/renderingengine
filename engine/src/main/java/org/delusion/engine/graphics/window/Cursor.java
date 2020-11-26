package org.delusion.engine.graphics.window;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.lwjgl.glfw.GLFW.*;

public class Cursor {
    private static Logger LOGGER = LogManager.getLogger(Cursor.class);

    private long handle;

    public Cursor(ImageGLFW image, int xhot, int yhot) {
        handle = glfwCreateCursor(image.asGLFWImage(), xhot, yhot);
        LOGGER.info("Created Custom Cursor {}", handle);
    }

    public Cursor(int shape) {
        handle = glfwCreateStandardCursor(shape);
        LOGGER.info("Created System Cursor {} ({})", stringifyShape(shape),handle);
    }

    private String stringifyShape(int shape) {
        switch (shape) {
            case GLFW_ARROW_CURSOR:
                return "Arrow";
            case GLFW_IBEAM_CURSOR:
                return "I-Beam";
            case GLFW_CROSSHAIR_CURSOR:
                return "Crosshair";
            case GLFW_HAND_CURSOR:
                return "Hand";
            case GLFW_HRESIZE_CURSOR:
                return "Horizontal Resize";
            case GLFW_VRESIZE_CURSOR:
                return "Vertical Resize";
        }
        return "Unknown System Cursor Shape";
    }

    public void destroy() {
        glfwDestroyCursor(handle);
        LOGGER.info("Destroyed Cursor {}", handle);
    }

    public long getHandle() {
        return handle;
    }
}
