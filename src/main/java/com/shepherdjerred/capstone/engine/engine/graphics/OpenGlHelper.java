package com.shepherdjerred.capstone.engine.engine.graphics;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class OpenGlHelper {

  public static void unbindTexture() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }

  public static void unbindBuffer() {
    glBindBuffer(GL_ARRAY_BUFFER, 0);
  }

  public static void unbindVertexArray() {
    glBindVertexArray(0);
  }
}
