package com.shepherdjerred.capstone.engine.engine.graphics;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class OpenGlHelper {

  public static void unbindBuffer() {
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  public static void unbindVertexArray() {
    glBindVertexArray(0);
  }
}
