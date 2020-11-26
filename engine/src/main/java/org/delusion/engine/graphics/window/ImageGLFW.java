package org.delusion.engine.graphics.window;

import org.delusion.engine.graphics.texture.TextureData;
import org.delusion.engine.graphics.texture.TextureUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWImage;

import java.io.IOException;
import java.nio.ByteBuffer;


public class ImageGLFW {
    private TextureData imageData;

    public ImageGLFW(String path, boolean fromClasspath) throws IOException {
        imageData = TextureUtils.read(path, fromClasspath);
    }

    public GLFWImage asGLFWImage() {
        GLFWImage i = GLFWImage.create();
        ByteBuffer pb = BufferUtils.createByteBuffer(imageData.getData().capacity());
        imageData.getData().rewind();
        byte b;
        while (imageData.getData().hasRemaining()) {
            b = imageData.getData().get();
            pb.put(b);
        }

        i.set(imageData.getWidth(),imageData.getHeight(),pb);

        return i;
    }
}
