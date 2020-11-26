package org.delusion.engine.graphics.shader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.delusion.engine.utils.FileUtils;

import java.io.IOException;
import static org.lwjgl.opengl.GL46.*;

public class Shader {
    private static Logger LOGGER = LogManager.getLogger(Shader.class);

    private int handle;

    public Shader(String path, boolean fromClasspath, ShaderType type) throws IOException {
        String src = FileUtils.read(path, fromClasspath);
        LOGGER.info("Reading shader from path {} in scope {} of type {}", path, fromClasspath ? "classpath" : "external", type.name().toLowerCase());

        handle = glCreateShader(type.getTypeId());

        glShaderSource(handle,src);
        glCompileShader(handle);
    }

    public void destroy() {
        glDeleteShader(handle);
    }

    public int getHandle() {
        return handle;
    }
}
