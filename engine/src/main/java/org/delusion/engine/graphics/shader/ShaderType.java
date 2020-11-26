package org.delusion.engine.graphics.shader;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public enum ShaderType {
    VERTEX(GL_VERTEX_SHADER),
    FRAGMENT(GL_FRAGMENT_SHADER);

    private int typeid;

    ShaderType(int typeid) {

        this.typeid = typeid;
    }

    public int getTypeId() {
        return typeid;
    }
}
