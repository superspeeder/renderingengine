package org.delusion.engine.graphics.shader;

import org.delusion.engine.graphics.lighting.DirectionalLight;
import org.delusion.engine.graphics.lighting.Light;
import org.delusion.engine.graphics.lighting.Material;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class ShaderProgram {
    private int program;

    public ShaderProgram(Shader... shaders) {
        program = glCreateProgram();
        for (Shader shader : shaders) {
            glAttachShader(program,shader.getHandle());
        }

        glLinkProgram(program);

        System.out.println(glGetProgramInfoLog(program));
    }

    public ShaderProgram(List<Shader> shaders) {
        program = glCreateProgram();
        for (Shader shader : shaders) {
            glAttachShader(program,shader.getHandle());
        }

        glLinkProgram(program);
        System.out.println(glGetProgramInfoLog(program));

    }

    public void bind() {
        glUseProgram(program);
    }

    public void unbind() {
        glUseProgram(0);
    }

    private int getUniformLocation(String uniformName) {
        return glGetUniformLocation(program, uniformName);
    }

    public void uniform1f(String uniformName, float x) {
        glUniform1f(getUniformLocation(uniformName), x);
    }

    public void uniform2f(String uniformName, float x, float y) {
        glUniform2f(getUniformLocation(uniformName), x, y);
    }

    public void uniform3f(String uniformName, float x, float y, float z) {
        glUniform3f(getUniformLocation(uniformName), x, y, z);
    }

    public void uniform4f(String uniformName, float x, float y, float z, float w) {
        glUniform4f(getUniformLocation(uniformName), x, y, z, w);
    }

    public void uniform1d(String uniformName, double x) {
        glUniform1d(getUniformLocation(uniformName), x);
    }

    public void uniform2d(String uniformName, double x, double y) {
        glUniform2d(getUniformLocation(uniformName), x, y);
    }

    public void uniform3d(String uniformName, double x, double y, double z) {
        glUniform3d(getUniformLocation(uniformName), x, y, z);
    }

    public void uniform4d(String uniformName, double x, double y, double z, double w) {
        glUniform4d(getUniformLocation(uniformName), x, y, z, w);
    }

    public void uniform1i(String uniformName, int x) {
        glUniform1i(getUniformLocation(uniformName), x);
    }

    public void uniform2i(String uniformName, int x, int y) {
        glUniform2i(getUniformLocation(uniformName), x, y);
    }

    public void uniform3i(String uniformName, int x, int y, int z) {
        glUniform3i(getUniformLocation(uniformName), x, y, z);
    }

    public void uniform4i(String uniformName, int x, int y, int z, int w) {
        glUniform4i(getUniformLocation(uniformName), x, y, z, w);
    }

    public void uniformMat4(String uniformName, Matrix4f mat4) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        mat4.get(fb);
        glUniformMatrix4fv(getUniformLocation(uniformName), false, fb);
    }

    public void uniform4f(String uniformName, Vector4f vec) {
        uniform4f(uniformName, vec.x, vec.y, vec.z, vec.w);
    }

    public void uniform3f(String uniformName, Vector3f vec) {
        uniform3f(uniformName,vec.x,vec.y,vec.z);
    }

    public void uniformMaterial(String uniformName, Material material) {
        uniform3f(uniformName + ".ambient", material.getAmbient());
        uniform3f(uniformName + ".diffuse", material.getDiffuse());
        uniform3f(uniformName + ".specular", material.getSpecular());
        uniform1f(uniformName + ".shininess", material.getShininess());
    }

    public void uniformDirectionalLight(String uniformName, DirectionalLight light) {
        uniform3f(uniformName + ".ambient", light.getAmbient());
        uniform3f(uniformName + ".diffuse", light.getDiffuse());
        uniform3f(uniformName + ".specular", light.getSpecular());
        uniform3f(uniformName + ".direction", light.getDirection());
    }

    static class ShaderDef {
        String path;
        ShaderType type;

        public ShaderDef(String path, ShaderType type) {
            this.path = path;
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public ShaderType getType() {
            return type;
        }
    }

    public static class Builder {
        private final boolean fromClasspath;

        private final List<ShaderDef> shaderdefs = new ArrayList<>();

        public Builder(boolean fromClasspath) {
            this.fromClasspath = fromClasspath;
        }

        public Builder vertex(String path) {
            shaderdefs.add(new ShaderDef(path, ShaderType.VERTEX));
            return this;
        }


        public Builder fragment(String path) {
            shaderdefs.add(new ShaderDef(path, ShaderType.FRAGMENT));
            return this;
        }

        public ShaderProgram build() {
            List<Shader> shaders = new ArrayList<>();
            shaderdefs.forEach(shaderDef -> {
                try {
                    shaders.add(new Shader(shaderDef.path, fromClasspath, shaderDef.type));
                } catch (Exception ignored) {
                }
            });
            return new ShaderProgram(shaders);
        }
    }
}
