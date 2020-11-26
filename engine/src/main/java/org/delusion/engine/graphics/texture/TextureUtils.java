package org.delusion.engine.graphics.texture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.*;

public class TextureUtils {
    private static Logger LOGGER = LogManager.getLogger(TextureUtils.class);

    public static TextureData read(String path, boolean fromClasspath) throws IOException {
        if (fromClasspath) {
            return readFromClasspath(path);
        }
        return readFromFile(path);
    }

    public static TextureData readFromFile(String path) {
        LOGGER.info("Reading texture data from file {}", path);

        IntBuffer x = BufferUtils.createIntBuffer(1);
        IntBuffer y = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer data = stbi_load(path,x,y,channels,0);
        LOGGER.debug(stbi_failure_reason());

        x.rewind();
        y.rewind();
        channels.rewind();

        return new TextureData(x.get(),y.get(),channels.get(),data);
    }

    public static TextureData readFromClasspath(String path) throws IOException {
        LOGGER.info("Reading texture data from resource in classpath at {}", path);
        InputStream is = TextureUtils.class.getResourceAsStream(path);

        byte[] dat =  is.readAllBytes();

        ByteBuffer texbytes = BufferUtils.createByteBuffer(dat.length);

        StringBuilder e = new StringBuilder();

        for (byte b : dat) {
            texbytes.put(b);
            e.append((char)b);
        }
        LOGGER.debug(e.toString());

        texbytes.rewind();

        IntBuffer x = BufferUtils.createIntBuffer(1);
        IntBuffer y = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        ByteBuffer texdata = stbi_load_from_memory(texbytes, x,y,channels,0);
        LOGGER.debug(stbi_failure_reason());

        x.rewind();
        y.rewind();

        return new TextureData(x.get(),y.get(),channels.get(),texdata);
    }
}
