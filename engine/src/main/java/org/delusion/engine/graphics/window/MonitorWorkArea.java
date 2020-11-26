package org.delusion.engine.graphics.window;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class MonitorWorkArea {
    private int xpos, ypos, width, height;

    public MonitorWorkArea(int xpos, int ypos, int width, int height) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.width = width;
        this.height = height;
    }

    public MonitorWorkArea(Monitor m) {
        IntBuffer xpos = BufferUtils.createIntBuffer(1);
        IntBuffer ypos = BufferUtils.createIntBuffer(1);
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetMonitorWorkarea(m.getMonitorID(),xpos,ypos,width,height);

        xpos.rewind();
        ypos.rewind();
        width.rewind();
        height.rewind();
        this.xpos = xpos.get();
        this.ypos = ypos.get();
        this.width = width.get();
        this.height = height.get();
    }
}
