package org.delusion.engine.graphics.window;

import org.lwjgl.glfw.GLFWVidMode;

public class VideoMode {
    private int redBits, greenBits, blueBits, refreshRate, width, height;

    public VideoMode(GLFWVidMode vm) {
        redBits = vm.redBits();
        greenBits = vm.greenBits();
        blueBits = vm.blueBits();
        refreshRate = vm.refreshRate();
        width = vm.width();
        height = vm.height();
    }

    public int getRedBits() {
        return redBits;
    }

    public int getGreenBits() {
        return greenBits;
    }

    public int getBlueBits() {
        return blueBits;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
