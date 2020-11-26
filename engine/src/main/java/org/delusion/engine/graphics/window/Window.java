package org.delusion.engine.graphics.window;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4i;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private static Logger LOGGER = LogManager.getLogger(Window.class);

    private long window;
    private int width, height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    private String title;


    public Window(int width, int height, String title) {
        window = glfwCreateWindow(width,height,title,NULL,NULL);
        this.width = width;
        this.height = height;
        this.title = title;

        LOGGER.info("Created Window with size ({},{}) and title '{}'", width, height, title);
    }

    public Window(int width, int height, String title, Monitor monitor) {
        window = glfwCreateWindow(width,height,title,monitor.getMonitorID(),NULL);
        this.width = width;
        this.height = height;
        this.title = title;

        LOGGER.info("Created Window with size ({},{}) and title '{}' on monitor {}", width, height, title, monitor.getMonitorID());
    }

    public static void init() {
        if (!glfwInit()) {
            LOGGER.fatal("GLFW not initialized");
            throw new IllegalStateException("GLFW not initialized");
        }
        LOGGER.info("Initialized GLFW {}", getVersionString());
    }

    public static void terminate() {
        LOGGER.info("Terminating GLFW");
        glfwTerminate();
    }

    public static void initHint(int hint, int value) {
        glfwInitHint(hint,value);
    }

    public static String getVersionString() {
        return glfwGetVersionString();
    }

    public static void setGlfwErrorCallback(GLFWErrorCallbackI c) {
        LOGGER.info("Setting GLFW Error Callback");
        glfwSetErrorCallback(c);
    }

    public static void defaultHints() {
        LOGGER.info("Setting GLFW Default Hints");
        glfwDefaultWindowHints();
    }

    public static void hint(int hint, int value) {
        LOGGER.info("Setting GLFW Hint {} to value {}", stringifyHint(hint), stringifyHintValue(hint,value));
        glfwWindowHint(hint, value);
    }

    private static String stringifyHintValue(int hint, int value) {
        switch (hint) {
            case GLFW_RED_BITS:
            case GLFW_ACCUM_GREEN_BITS:
            case GLFW_ACCUM_RED_BITS:
            case GLFW_STENCIL_BITS:
            case GLFW_DEPTH_BITS:
            case GLFW_ALPHA_BITS:
            case GLFW_BLUE_BITS:
            case GLFW_GREEN_BITS:
            case GLFW_ACCUM_BLUE_BITS:
            case GLFW_ACCUM_ALPHA_BITS:
            case GLFW_AUX_BUFFERS:
            case GLFW_SAMPLES:
            case GLFW_REFRESH_RATE:
            case GLFW_CONTEXT_VERSION_MAJOR:
            case GLFW_CONTEXT_VERSION_MINOR:
            case GLFW_CONTEXT_REVISION:
                return Integer.toString(value);
            case GLFW_STEREO:
            case GLFW_SRGB_CAPABLE:
            case GLFW_DOUBLEBUFFER:
            case GLFW_FOCUSED:
            case GLFW_ICONIFIED:
            case GLFW_RESIZABLE:
            case GLFW_VISIBLE:
            case GLFW_DECORATED:
            case GLFW_AUTO_ICONIFY:
            case GLFW_FLOATING:
            case GLFW_MAXIMIZED:
            case GLFW_CENTER_CURSOR:
            case GLFW_TRANSPARENT_FRAMEBUFFER:
            case GLFW_HOVERED:
            case GLFW_FOCUS_ON_SHOW:
            case GLFW_OPENGL_FORWARD_COMPAT:
            case GLFW_OPENGL_DEBUG_CONTEXT:
            case GLFW_CONTEXT_NO_ERROR:
            case GLFW_SCALE_TO_MONITOR:
                return value == GLFW_TRUE ? "true" : "false";
            case GLFW_CLIENT_API:
                switch (value) {
                    case GLFW_NO_API:
                        return "No API";
                    case GLFW_OPENGL_API:
                        return "OpenGL API";
                    case GLFW_OPENGL_ES_API:
                        return "OpenGL ES API";
                }
                return "Unknown API";
            case GLFW_CONTEXT_ROBUSTNESS:
                switch (value) {
                    case GLFW_NO_ROBUSTNESS:
                        return "No Robustness";
                    case GLFW_NO_RESET_NOTIFICATION:
                        return "No Reset Notification";
                    case GLFW_LOSE_CONTEXT_ON_RESET:
                        return "Lose Context on Reset";
                }
                return "Unknown Robustness";
            case GLFW_OPENGL_PROFILE:
                switch (value) {
                    case GLFW_OPENGL_ANY_PROFILE:
                        return "Any Profile";
                    case GLFW_OPENGL_COMPAT_PROFILE:
                        return "Compatibility Profile";
                    case GLFW_OPENGL_CORE_PROFILE:
                        return "Core Profile";
                }
            case GLFW_CONTEXT_RELEASE_BEHAVIOR:
                switch (value) {
                    case GLFW_ANY_RELEASE_BEHAVIOR:
                        return "Any Release Behaviour";
                    case GLFW_RELEASE_BEHAVIOR_FLUSH:
                        return "Flush";
                    case GLFW_RELEASE_BEHAVIOR_NONE:
                        return "None";
                }
                return "Unknown Release Behaviour";
            case GLFW_CONTEXT_CREATION_API:
                switch (value) {
                    case GLFW_NATIVE_CONTEXT_API:
                        return "Native API";
                    case GLFW_EGL_CONTEXT_API:
                        return "EGL API";
                    case GLFW_OSMESA_CONTEXT_API:
                        return "OSMesa API";
                }
                return "Unknown API";
        }
        return Integer.toString(value);
    }

    private static String stringifyHint(int hint) {
        switch (hint) {
            case GLFW_RED_BITS:
                return "Red Bits";
            case GLFW_GREEN_BITS:
                return "Greeen Bits";
            case GLFW_BLUE_BITS:
                return "Blue Bits";
            case GLFW_ALPHA_BITS:
                return "Alpha Bits";
            case GLFW_DEPTH_BITS:
                return "Depth Bits";
            case GLFW_STENCIL_BITS:
                return "Stencil Bits";
            case GLFW_ACCUM_RED_BITS:
                return "Accum. Red Bits";
            case GLFW_ACCUM_GREEN_BITS:
                return "Accum. Green Bits";
            case GLFW_ACCUM_BLUE_BITS:
                return "Accum. Blue Bits";
            case GLFW_ACCUM_ALPHA_BITS:
                return "Accum. Alpha Bits";
            case GLFW_AUX_BUFFERS:
                return "Auxiliary Buffers";
            case GLFW_STEREO:
                return "Stereo Buffer";
            case GLFW_SAMPLES:
                return "Samples";
            case GLFW_SRGB_CAPABLE:
                return "sRGB Capable";
            case GLFW_REFRESH_RATE:
                return "Refresh Rate";
            case GLFW_DOUBLEBUFFER:
                return "Doublebuffer";
            case GLFW_FOCUSED:
                return "Focused";
            case GLFW_ICONIFIED:
                return "Iconified";
            case GLFW_RESIZABLE:
                return "Resizable";
            case GLFW_VISIBLE:
                return "Visible";
            case GLFW_DECORATED:
                return "Decorated";
            case GLFW_AUTO_ICONIFY:
                return "Auto Iconify";
            case GLFW_FLOATING:
                return "Floating";
            case GLFW_MAXIMIZED:
                return "Maximized";
            case GLFW_CENTER_CURSOR:
                return "Center Cursor";
            case GLFW_TRANSPARENT_FRAMEBUFFER:
                return "Transparent Framebuffer";
            case GLFW_HOVERED:
                return "Hovered";
            case GLFW_FOCUS_ON_SHOW:
                return "Focus on Show";
            case GLFW_CONTEXT_REVISION:
                return "Context Revision";
            case GLFW_CLIENT_API:
                return "Client API";
            case GLFW_CONTEXT_VERSION_MAJOR:
                return "Context Version Major";
            case GLFW_CONTEXT_VERSION_MINOR:
                return "Context Version Minor";
            case GLFW_CONTEXT_ROBUSTNESS:
                return "Context Robustness";
            case GLFW_OPENGL_FORWARD_COMPAT:
                return "Opengl Forward Compatibility";
            case GLFW_OPENGL_DEBUG_CONTEXT:
                return "Opengl Debug Context";
            case GLFW_OPENGL_PROFILE:
                return "Opengl Profile";
            case GLFW_CONTEXT_RELEASE_BEHAVIOR:
                return "Context Release Behavior";
            case GLFW_CONTEXT_NO_ERROR:
                return "Context No Error";
            case GLFW_CONTEXT_CREATION_API:
                return "Context Creation API";
            case GLFW_SCALE_TO_MONITOR:
                return "Scale To Monitor";
        }
        return "Unknown Hint (" + Integer.toString(hint) + ")";
    }

    public static void hint(int hint, String value) {
        LOGGER.info("Setting GLFW String Hint {} to \"{}\"", hint, value);
        glfwWindowHintString(hint, value);
    }

    public void destroy() {
        glfwDestroyWindow(window);
        LOGGER.info("Destroyed Window");
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
    }

    public void setShouldClose(boolean state) {
        glfwSetWindowShouldClose(window, state);
        LOGGER.info("Window set to " + (state ? "close" : "stay open"));
    }

    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(window, title);
        LOGGER.info("Window Title set to \"{}\"", title);
    }

    public void setIcon(GLFWImage.Buffer images) {
        glfwSetWindowIcon(window, images);
        LOGGER.info("Window Icons Set");
    }

    public void setIcon(List<ImageGLFW> images) {
        GLFWImage.Buffer buffer = GLFWImage.create(images.size());
        images.forEach(imageGLFW -> buffer.put(imageGLFW.asGLFWImage()));
        buffer.rewind();
        setIcon(buffer);
    }

    public Vector2i getWindowPos() {
        IntBuffer xpos = BufferUtils.createIntBuffer(1);
        IntBuffer ypos = BufferUtils.createIntBuffer(1);

        glfwGetWindowPos(window, xpos, ypos);

        xpos.rewind();
        ypos.rewind();

        return new Vector2i(xpos.get(),ypos.get());
    }

    public void setWindowPos(Vector2i pos) {
        glfwSetWindowPos(window, pos.x, pos.y);
        LOGGER.debug("Window Pos set to {}", pos);
    }

    public Vector2i getWindowSize(Vector2i size) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);

        glfwGetWindowSize(window, width, height);

        width.rewind();
        height.rewind();

        return new Vector2i(width.get(),height.get());
    }

    public void setSizeLimits(int min_x, int min_y, int max_x, int max_y) {
        glfwSetWindowSizeLimits(window, min_x, min_y, max_x, max_y);
        LOGGER.info("Window size limits set to x:[{},{}] y:[{},{}]", min_x,max_x,min_y,max_y);
    }

    public void setAspectRatio(int numer, int denom) {
        glfwSetWindowAspectRatio(window, numer, denom);
        LOGGER.info("Window aspect ratio set to {}/{}", numer,denom);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        glfwSetWindowSize(window, width, height);
        LOGGER.debug("Window Size set to {} x {}", width, height);
    }

    public Vector2i getFramebufferSize() {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);

        glfwGetFramebufferSize(window, width, height);

        width.rewind();
        height.rewind();

        return new Vector2i(width.get(),height.get());
    }

    public Vector4i getWindowFrameSize() {
        IntBuffer left = BufferUtils.createIntBuffer(1);
        IntBuffer top = BufferUtils.createIntBuffer(1);
        IntBuffer right = BufferUtils.createIntBuffer(1);
        IntBuffer bottom = BufferUtils.createIntBuffer(1);

        glfwGetWindowFrameSize(window, left, top, right, bottom);

        left.rewind();
        top.rewind();
        right.rewind();
        bottom.rewind();

        return new Vector4i(left.get(),top.get(),right.get(),bottom.get());
    }

    public Vector2f getContentScale() {
        FloatBuffer xscl = BufferUtils.createFloatBuffer(1);
        FloatBuffer yscl = BufferUtils.createFloatBuffer(1);

        glfwGetWindowContentScale(window, xscl, yscl);

        xscl.rewind();
        yscl.rewind();

        return new Vector2f(xscl.get(),yscl.get());
    }

    public float getWindowOpacity() {
        return glfwGetWindowOpacity(window);
    }

    public void setWindowOpacity(float value) {
        glfwSetWindowOpacity(window, value);
        LOGGER.info("Window opacity set to {}", value);
    }

    public void iconify() {
        glfwIconifyWindow(window);
        LOGGER.debug("Window iconified");
    }

    public void restore() {
        glfwRestoreWindow(window);
        LOGGER.debug("Window restored");
    }

    public void maximize() {
        glfwMaximizeWindow(window);
        LOGGER.debug("Window maximized");
    }

    public void show() {
        glfwShowWindow(window);
        LOGGER.debug("Window shown");
    }

    public void hide() {
        glfwHideWindow(window);
        LOGGER.debug("Window hidden");
    }

    public void focus() {
        glfwFocusWindow(window);
        LOGGER.debug("Window focused");
    }

    public void requestAttention() {
        glfwRequestWindowAttention(window);
        LOGGER.debug("Window requesting attention");
    }

    public Monitor getMonitor() {
        return Monitor.get(glfwGetWindowMonitor(window));
    }

    public void setMonitor(Monitor monitor) {
        VideoMode vm = monitor.getVideoMode();
        glfwSetWindowMonitor(window, monitor.getMonitorID(), 0, 0, vm.getWidth(), vm.getHeight(), vm.getRefreshRate());
        LOGGER.info("Window monitor set to Monitor {}", monitor.getMonitorID());
    }

    public int getAttrib(int attrib) {
        return glfwGetWindowAttrib(window, attrib);
    }

    public void setAttrib(int attrib, int value) {
        glfwSetWindowAttrib(window, attrib, value);
        LOGGER.info("Window Attrib {} set to {}", stringifyAttrib(attrib), stringifyAttribValue(attrib,value));
    }

    private String stringifyAttribValue(int attrib, int value) {
        switch (attrib) {
            case GLFW_FOCUSED:
            case GLFW_ICONIFIED:
            case GLFW_MAXIMIZED:
            case GLFW_HOVERED:
            case GLFW_VISIBLE:
            case GLFW_RESIZABLE:
            case GLFW_DECORATED:
            case GLFW_AUTO_ICONIFY:
            case GLFW_FLOATING:
            case GLFW_TRANSPARENT_FRAMEBUFFER:
            case GLFW_FOCUS_ON_SHOW:
            case GLFW_OPENGL_FORWARD_COMPAT:
            case GLFW_OPENGL_DEBUG_CONTEXT:
            case GLFW_CONTEXT_NO_ERROR:
                return value == GLFW_TRUE ? "true" : "false";
            case GLFW_CLIENT_API:
                switch (value) {
                    case GLFW_NO_API:
                        return "No API";
                    case GLFW_OPENGL_API:
                        return "OpenGL API";
                    case GLFW_OPENGL_ES_API:
                        return "OpenGL ES API";
                }
                return "Unknown API";
            case GLFW_CONTEXT_ROBUSTNESS:
                switch (value) {
                    case GLFW_NO_ROBUSTNESS:
                        return "No Robustness";
                    case GLFW_NO_RESET_NOTIFICATION:
                        return "No Reset Notification";
                    case GLFW_LOSE_CONTEXT_ON_RESET:
                        return "Lose Context on Reset";
                }
                return "Unknown Robustness";
            case GLFW_OPENGL_PROFILE:
                switch (value) {
                    case GLFW_OPENGL_ANY_PROFILE:
                        return "Any Profile";
                    case GLFW_OPENGL_COMPAT_PROFILE:
                        return "Compatibility Profile";
                    case GLFW_OPENGL_CORE_PROFILE:
                        return "Core Profile";
                }
            case GLFW_CONTEXT_RELEASE_BEHAVIOR:
                switch (value) {
                    case GLFW_ANY_RELEASE_BEHAVIOR:
                        return "Any Release Behaviour";
                    case GLFW_RELEASE_BEHAVIOR_FLUSH:
                        return "Flush";
                    case GLFW_RELEASE_BEHAVIOR_NONE:
                        return "None";
                }
                return "Unknown Release Behaviour";
            case GLFW_CONTEXT_CREATION_API:
                switch (value) {
                    case GLFW_NATIVE_CONTEXT_API:
                        return "Native API";
                    case GLFW_EGL_CONTEXT_API:
                        return "EGL API";
                    case GLFW_OSMESA_CONTEXT_API:
                        return "OSMesa API";
                }
                return "Unknown API";
        }

        return Integer.toString(value);
    }

    private String stringifyAttrib(int attrib) {
        switch (attrib) {
            case GLFW_FOCUSED:
                return "Focused";
            case GLFW_ICONIFIED:
                return "Iconified";
            case GLFW_MAXIMIZED:
                return "Maximized";
            case GLFW_HOVERED:
                return "Hovered";
            case GLFW_VISIBLE:
                return "Visible";
            case GLFW_RESIZABLE:
                return "Resizable";
            case GLFW_DECORATED:
                return "Decorated";
            case GLFW_AUTO_ICONIFY:
                return "Auto Iconify";
            case GLFW_FLOATING:
                return "Floating";
            case GLFW_TRANSPARENT_FRAMEBUFFER:
                return "Transparent Framebuffer";
            case GLFW_FOCUS_ON_SHOW:
                return "Focus on Show";
            case GLFW_CLIENT_API:
                return "Client API";
            case GLFW_CONTEXT_VERSION_MAJOR:
                return "Context Version Major";
            case GLFW_CONTEXT_VERSION_MINOR:
                return "Context Version Minor";
            case GLFW_CONTEXT_ROBUSTNESS:
                return "Context Robustness";
            case GLFW_OPENGL_FORWARD_COMPAT:
                return "Opengl Forward Compatibility";
            case GLFW_OPENGL_DEBUG_CONTEXT:
                return "Opengl Debug Context";
            case GLFW_OPENGL_PROFILE:
                return "Opengl Profile";
            case GLFW_CONTEXT_RELEASE_BEHAVIOR:
                return "Context Release Behavior";
            case GLFW_CONTEXT_NO_ERROR:
                return "Context No Error";
            case GLFW_CONTEXT_CREATION_API:
                return "Context Creation API";
        }

        return "Unknown Attribute (" + Integer.toString(attrib) + ")";
    }

    public void setPosCallback(GLFWWindowPosCallbackI c) {
        glfwSetWindowPosCallback(window, c);
        LOGGER.info("Window pos callback set");
    }

    public void setSizeCallback(GLFWWindowSizeCallbackI c) {
        glfwSetWindowSizeCallback(window, c);
        LOGGER.info("Window size callback set");
    }

    public void setCloseCallback(GLFWWindowCloseCallbackI c) {
        glfwSetWindowCloseCallback(window, c);
        LOGGER.info("Window close callback set");
    }

    public void setRefreshCallback(GLFWWindowRefreshCallbackI c) {
        glfwSetWindowRefreshCallback(window, c);
        LOGGER.info("Window refresh callback set");
    }

    public void setFocusCallback(GLFWWindowFocusCallbackI c) {
        glfwSetWindowFocusCallback(window, c);
        LOGGER.info("Window focus callback set");
    }

    public void setIconifyCallback(GLFWWindowIconifyCallbackI c) {
        glfwSetWindowIconifyCallback(window, c);
        LOGGER.info("Window iconify callback set");
    }

    public void setMaximizeCallback(GLFWWindowMaximizeCallbackI c) {
        glfwSetWindowMaximizeCallback(window, c);
        LOGGER.info("Window maximize callback set");
    }

    public void setFramebufferSizeCallback(GLFWFramebufferSizeCallbackI c) {
        glfwSetFramebufferSizeCallback(window, c);
        LOGGER.info("Window framebuffer size callback set");
    }

    public void setContentScaleCallback(GLFWWindowContentScaleCallbackI c) {
        glfwSetWindowContentScaleCallback(window, c);
        LOGGER.info("Window content scale callback set");
    }

    public static void pollEvents() {
        glfwPollEvents();
    }

    public static void waitEvents() {
        glfwWaitEvents();
    }

    public void waitEventsTimeout(double timeout) {
        glfwWaitEventsTimeout(timeout);
    }

    public void postEmptyEvent() {
        glfwPostEmptyEvent();
        LOGGER.debug("Empty Event Posted");
    }

    public int getInputMode(int mode) {
        return glfwGetInputMode(window, mode);
    }

    public void setInputMode(int mode, int value) {
        glfwSetInputMode(window, mode, value);
        LOGGER.info("Window Input Mode {} set to {}", stringifyInputMode(mode), stringifyInputModeValue(mode,value));
    }

    private String stringifyInputModeValue(int mode, int value) {
        switch (mode) {
            case GLFW_CURSOR:
                switch (value) {
                    case GLFW_CURSOR_NORMAL:
                        return "Normal";
                    case GLFW_CURSOR_DISABLED:
                        return "Disabled";
                    case GLFW_CURSOR_HIDDEN:
                        return "Hidden";
                }
            case GLFW_STICKY_KEYS:
            case GLFW_STICKY_MOUSE_BUTTONS:
            case GLFW_LOCK_KEY_MODS:
            case GLFW_RAW_MOUSE_MOTION:
                return value == GLFW_TRUE ? "true" : "false";
        }
        return Integer.toString(value);
    }

    private String stringifyInputMode(int mode) {
        switch (mode) {
            case GLFW_CURSOR:
                return "Cursor";
            case GLFW_STICKY_KEYS:
                return "Sticky Keys";
            case GLFW_STICKY_MOUSE_BUTTONS:
                return "Sticky Mouse Buttons";
            case GLFW_LOCK_KEY_MODS:
                return "Lock Key Mods";
            case GLFW_RAW_MOUSE_MOTION:
                return "Raw Mouse Motion";
        }
        return "Unknown Input Mode";
    }

    public static boolean isRawMouseMotionSupported() {
        return glfwRawMouseMotionSupported();
    }

    public static String getKeyName(int key, int scancode) {
        return glfwGetKeyName(key,scancode);
    }

    public static int getKeyScancode(int key) {
        return glfwGetKeyScancode(key);
    }

    public boolean getKey(int key) {
        return glfwGetKey(window, key) == GLFW_PRESS;
    }

    public boolean getMouseButton(int button) {
        return glfwGetMouseButton(window, button) == GLFW_PRESS;
    }

    public Vector2d getCursorPos() {
        DoubleBuffer xpos = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer ypos = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(window, xpos, ypos);

        xpos.rewind();
        ypos.rewind();

        return new Vector2d(xpos.get(),ypos.get());
    }

    public void setCursorPos(double x, double y) {
        glfwSetCursorPos(window,x,y);
        LOGGER.debug("Cursor Pos set to ({}, {})", x, y);
    }

    public void setCursor(Cursor cursor) {
        glfwSetCursor(window, cursor.getHandle());
        LOGGER.info("Cursor set to {}", cursor.getHandle());
    }

    public void setKeyCallback(GLFWKeyCallbackI c) {
        glfwSetKeyCallback(window,c);
        LOGGER.info("Key Callback set");
    }

    public void setCharCallback(GLFWCharCallbackI c) {
        glfwSetCharCallback(window,c);
        LOGGER.info("Char Callback set");
    }

    public void setCharModsCallback(GLFWCharModsCallbackI c) {
        glfwSetCharModsCallback(window, c);
        LOGGER.info("Char + Mods Callback set");
    }

    public void setMouseButtonCallback(GLFWMouseButtonCallbackI c) {
        glfwSetMouseButtonCallback(window, c);
        LOGGER.info("Mouse Button Callback set");
    }

    public void setCursorPosCallback(GLFWCursorPosCallbackI c) {
        glfwSetCursorPosCallback(window, c);
        LOGGER.info("Cursor Pos Callback set");
    }

    public void setCursorEnterCallback(GLFWCursorEnterCallbackI c) {
        glfwSetCursorEnterCallback(window, c);
        LOGGER.info("Cursor Enter Callback set");
    }

    public void setScrollCallback(GLFWScrollCallbackI c) {
        glfwSetScrollCallback(window, c);
        LOGGER.info("Scroll Callback set");
    }

    public void setDropCallback(GLFWDropCallbackI c) {
        glfwSetDropCallback(window, c);
        LOGGER.info("Path Drop Callback set");
    }

    public void setClipboard(String value) {
        glfwSetClipboardString(window,value);
        LOGGER.debug("Clipboard set to {}", value);
    }

    public String getClipboard() {
        return glfwGetClipboardString(window);
    }

    public static void setTime(double time) {
        glfwSetTime(time);
        LOGGER.debug("Time set to {}", time);
    }

    public static long getTimerValue() {
        return glfwGetTimerValue();
    }

    public static long getTimerFrequency() {
        return glfwGetTimerFrequency();
    }

    public void makeContextCurrent() {
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        LOGGER.debug("Context was made current");
    }

    public static long getCurrentContext() {
        return glfwGetCurrentContext();
    }

    public void swapBuffers() {
        glfwSwapBuffers(window);
    }

    public static void swapInterval(int interval) {
        glfwSwapInterval(interval);
        LOGGER.info("Swap Interval set to {}", interval);
    }
}
