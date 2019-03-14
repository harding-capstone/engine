package com.shepherdjerred.capstone.engine.engine.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import lombok.Getter;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

  @Getter
  private final int vaoId;
  private final int posVboId;
  private final int idxVboId;
  private final int colorVboId;
  @Getter
  private final int numberOfVerticies;

  public Mesh(float[] positions, float[] colors, int[] indices) {
    FloatBuffer positionsBuffer = null;
    FloatBuffer colorBuffer = null;
    IntBuffer indicesBuffer = null;
    try {
      numberOfVerticies = indices.length;

      vaoId = glGenVertexArrays();
      glBindVertexArray(vaoId);

      // Position VBO
      positionsBuffer = MemoryUtil.memAllocFloat(positions.length);
      positionsBuffer.put(positions).flip();
      posVboId = glGenBuffers();
      glBindBuffer(GL_ARRAY_BUFFER, posVboId);
      glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

      // Colour VBO
      colorVboId = glGenBuffers();
      colorBuffer = MemoryUtil.memAllocFloat(colors.length);
      colorBuffer.put(colors).flip();
      glBindBuffer(GL_ARRAY_BUFFER, colorVboId);
      glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

      // Index VBO
      indicesBuffer = MemoryUtil.memAllocInt(indices.length);
      indicesBuffer.put(indices).flip();
      idxVboId = glGenBuffers();
      glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId);
      glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

      glBindBuffer(GL_ARRAY_BUFFER, 0);
      glBindVertexArray(0);
    } finally {
      if (positionsBuffer != null) {
        MemoryUtil.memFree(positionsBuffer);
      }
      if (indicesBuffer != null) {
        MemoryUtil.memFree(indicesBuffer);
      }
      if (colorBuffer != null) {
        MemoryUtil.memFree(colorBuffer);
      }
    }
  }

  public void cleanup() {
    glDisableVertexAttribArray(0);

    // Delete the VBOs
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glDeleteBuffers(posVboId);
    glDeleteBuffers(idxVboId);
    glDeleteBuffers(colorVboId);

    // Delete the VAO
    glBindVertexArray(0);
    glDeleteVertexArrays(vaoId);
  }
}
