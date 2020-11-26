package org.delusion.engine.graphics.texture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;

public class TextureData {
    private static Logger LOGGER = LogManager.getLogger(TextureData.class);

    private int width, height, channels;
    private ByteBuffer data;

    public TextureData(int width, int height, int channels, ByteBuffer data) {
        LOGGER.info("Consolidating TextureData with size {} x {} and {} channels", width, height, channels);
        this.width = width;
        this.height = height;
        this.channels = channels;
        this.data = data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getChannels() {
        return channels;
    }

    public ByteBuffer getData() {
        return data;
    }
}
