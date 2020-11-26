package org.delusion.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.delusion.engine.Version;
import org.delusion.engine.graphics.*;
import org.delusion.engine.graphics.lighting.DirectionalLight;
import org.delusion.engine.graphics.lighting.Light;
import org.delusion.engine.graphics.lighting.Material;
import org.delusion.engine.graphics.primitive.Cube;
import org.delusion.engine.graphics.primitive.Quad;
import org.delusion.engine.graphics.shader.ShaderProgram;
import org.delusion.engine.graphics.window.Monitor;
import org.delusion.engine.graphics.window.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GLUtil;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;

public class Game {

    private static Logger LOGGER = LogManager.getLogger(Game.class);
    private static float speed = 0.05f;

    private static Vector2f dmp = new Vector2f();
    private static DirectionalLight light;

    public static void main(String[] args) throws IOException {
        LOGGER.info("Using Version {} of Engine.", Version.VERSION);

        Window.init();

        Window.hint(GLFW_RESIZABLE, GLFW_FALSE);
        Window.hint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        Window.hint(GLFW_CENTER_CURSOR, GLFW_TRUE);
        Window.hint(GLFW_SAMPLES, 4);

        Window window = new Window(1920,1080,"Test", Monitor.getPrimaryMonitor());
        window.makeContextCurrent();
        String debugGLProperty = System.getProperty("engine.debugOpenGL");
        if (debugGLProperty != null) {
            if (debugGLProperty.equalsIgnoreCase("true")) {
                LOGGER.info("OpenGL debugging enabled. Output is STDOUT. This WILL NOT be logged to the logfile.");
                GLUtil.setupDebugMessageCallback();
            } else {
                LOGGER.info("engine.debugOpenGL property not set to true. Not enabling OpenGL Debugging.");
            }
        } else {
            LOGGER.info("engine.debugOpenGL property not found. Ignoring.");
        }

        window.setInputMode(GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
        window.setInputMode(GLFW_CURSOR, GLFW_CURSOR_HIDDEN);

        window.setCursorPosCallback((window1, xpos, ypos) -> {
            Vector2f im = new Vector2f((float)xpos - 400, (float)ypos - 400);
            dmp.set(im);
            glfwSetCursorPos(window1, 400, 400);
        });


        Window.swapInterval(1);
        window.focus();

        Renderer renderer = new Renderer();
        Camera camera = renderer.getCamera();

//        renderer.cullFront();

        ShaderProgram program = new ShaderProgram.Builder(true).vertex("/v.glsl").fragment("/f.glsl").build();
        renderer.setDefaultShader(program);

        renderer.background(0.8f,0.8f,0.8f,1.0f);
        renderer.enableMSAA();


        Material materialRed, materialGreen, materialBlue;
        materialRed = new Material(new Vector3f(0.2f,0.0f,0.0f), new Vector3f(1.0f,0.0f,0.0f), new Vector3f(1,1,1), 32.0f);
        materialGreen = new Material(new Vector3f(0.0f,0.2f,0.0f), new Vector3f(0.0f,1.0f,0.0f), new Vector3f(1,1,1), 1024.0f);
        materialBlue = new Material(new Vector3f(0.0f,0.0f,0.2f), new Vector3f(0.0f,0.0f,1.0f), new Vector3f(1,1,1), 32.0f);

        Cube cube0 = new Cube(new Vector3f(-10,-1,-11), new Vector3f(20,5,1), materialRed);
        Cube cube1 = new Cube(new Vector3f(-10, -2, -10), new Vector3f(20, 1, 20), materialGreen);
        Cube cube2 = new Cube(new Vector3f(-10,-1,10), new Vector3f(20,5,1), materialBlue);
        Cube cube3 = new Cube(new Vector3f(10,-1,-10), new Vector3f(1, 5,20), materialGreen);
        Cube cube4 = new Cube(new Vector3f(-11,-1,-10), new Vector3f(1,5,20), materialRed);

        light = new DirectionalLight(
                new Vector3f(0.8f,0.8f,0.8f),
                new Vector3f(1.0f,1.0f,1.0f),
                new Vector3f(1.0f,1.0f,1.0f),
                new Vector3f(-0.5f,-1.0f,-0.5f));

        while (!window.shouldClose()) {
            Window.pollEvents();
            renderer.clear();

            if (window.getKey(GLFW_KEY_A)) {
                camera.translate(camera.left().mul(-speed));
            }
            if (window.getKey(GLFW_KEY_D)) {
                camera.translate(camera.left().mul(speed));
            }
            if (window.getKey(GLFW_KEY_W)) {
                camera.translate(camera.forward().mul(-speed));
            }
            if (window.getKey(GLFW_KEY_S)) {
                camera.translate(camera.forward().mul(speed));
            }
            if (window.getKey(GLFW_KEY_SPACE)) {
                camera.translate(0,speed,0);
            }
            if (window.getKey(GLFW_KEY_LEFT_SHIFT)) {
                camera.translate(0,-speed,0);
            }

            camera.rotateV(-dmp.y / 400.0f);
            camera.rotate(-dmp.x / 400.0f, 0.0f, 1.0f, 0.0f);

            renderer.light(light);

//            renderer.renderCube(cube0);
//            renderer.renderCube(cube1);
//            renderer.renderCube(cube2);
//            renderer.renderCube(cube3);
//            renderer.renderCube(cube4);

            renderer.renderCubes(cube0,cube1,cube2,cube3,cube4);

            window.swapBuffers();
        }

        Window.terminate();
    }
}
