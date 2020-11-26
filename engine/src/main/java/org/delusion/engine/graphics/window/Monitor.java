package org.delusion.engine.graphics.window;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWGammaRamp;
import org.lwjgl.glfw.GLFWMonitorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Monitor {
    private static Map<Long, Monitor> monitorMap = new HashMap<>();
    private final long monitor;

    public static List<Monitor> getMonitors() {
        PointerBuffer monitors = glfwGetMonitors();
        List<Monitor> monitor_list = new ArrayList<>();
        for (int i = 0; i < monitors.limit() ; i++) {
            long m = monitors.get(i);
            if (monitorMap.containsKey(m)) monitor_list.add(monitorMap.get(m));
            else {
                Monitor mo = new Monitor(m);
                monitor_list.add(mo);
                monitorMap.put(m,mo);
            }
        }
        return monitor_list;
    }

    public static Monitor getPrimaryMonitor() {
        long m = glfwGetPrimaryMonitor();
        if (monitorMap.containsKey(m)) return monitorMap.get(m);
        Monitor mo = new Monitor(m);
        monitorMap.put(m,mo);
        return mo;
    }

    public Monitor(long monitor) {
        this.monitor = monitor;
    }

    public static Monitor get(long mid) {
        if (monitorMap.containsKey(mid)) {
            return monitorMap.get(mid);
        } else {
            return monitorMap.put(mid, new Monitor(mid));
        }
    }


    public long getMonitorID() {
        return monitor;
    }

    public MonitorWorkArea getMonitorWorkArea() {
        return new MonitorWorkArea(this);
    }

    public Vector2i getMonitorPhysicalSize() {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);

        glfwGetMonitorPhysicalSize(monitor, width, height);

        width.rewind();
        height.rewind();

        return new Vector2i(width.get(),height.get());
    }

    public Vector2i getMonitorPos() {
        IntBuffer xpos = BufferUtils.createIntBuffer(1);
        IntBuffer ypos = BufferUtils.createIntBuffer(1);

        glfwGetMonitorPos(monitor, xpos, ypos);

        xpos.rewind();
        ypos.rewind();

        return new Vector2i(xpos.get(),ypos.get());
    }

    public Vector2f getMonitorContentScale() {
        FloatBuffer xscl = BufferUtils.createFloatBuffer(1);
        FloatBuffer yscl = BufferUtils.createFloatBuffer(1);

        glfwGetMonitorContentScale(monitor, xscl, yscl);

        xscl.rewind();
        yscl.rewind();

        return new Vector2f(xscl.get(),yscl.get());
    }

    public String getMonitorName() {
        return glfwGetMonitorName(monitor);
    }

    public static void setMonitorCallback(GLFWMonitorCallbackI c) {
        glfwSetMonitorCallback(c);
    }

    public List<VideoMode> getVideoModes() {
        GLFWVidMode.Buffer glfwVidModes = glfwGetVideoModes(monitor);
        List<VideoMode> vms = new ArrayList<>();

        for (GLFWVidMode vm : glfwVidModes) {
            vms.add(new VideoMode(vm));
        }
        return vms;
    }

    public VideoMode getVideoMode() {
        return new VideoMode(glfwGetVideoMode(monitor));
    }

    public void setGamma(float gamma) {
        glfwSetGamma(monitor, gamma);
    }

    public GLFWGammaRamp getGammaRamp() {
        return glfwGetGammaRamp(monitor);
    }

    public void setGammaRamp(GLFWGammaRamp ramp) {
        glfwSetGammaRamp(monitor, ramp);
    }
}
