package org.delusion.engine.graphics.window;

import org.lwjgl.glfw.GLFWJoystickCallbackI;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Joystick {
    private int id;

    public Joystick(int id) {
        this.id = id;
    }

    public boolean isPresent() {
        return glfwJoystickPresent(id);
    }

    public List<Float> getAxes() {
        List<Float> vals = new ArrayList<>();

        FloatBuffer fb = glfwGetJoystickAxes(id);
        for (int i = 0 ; i < fb.limit() ; i++) {
            vals.add(fb.get());
        }
        return vals;
    }

    public List<Byte> getButtons() {
        List<Byte> vals = new ArrayList<>();

        ByteBuffer bb = glfwGetJoystickButtons(id);
        for (int i = 0 ; i < bb.limit() ; i++) {
            vals.add(bb.get());
        }
        return vals;
    }

    public List<Byte> getHats() {
        List<Byte> vals = new ArrayList<>();

        ByteBuffer bb = glfwGetJoystickHats(id);
        for (int i = 0 ; i < bb.limit() ; i++) {
            vals.add(bb.get());
        }
        return vals;
    }

    public String getName() {
        return glfwGetJoystickName(id);
    }

    public String getGUID() {
        return glfwGetJoystickGUID(id);
    }

    public boolean isGamepad() {
        return glfwJoystickIsGamepad(id);
    }

    public static void setJoystickCallback(GLFWJoystickCallbackI c) {
        glfwSetJoystickCallback(c);
    }
}
