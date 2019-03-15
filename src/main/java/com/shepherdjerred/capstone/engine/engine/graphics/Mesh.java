package com.shepherdjerred.capstone.engine.engine.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

  @Getter
  private final int vaoId;
  private final List<Integer> vboIdList;
  @Getter
  private final int numberOfVertices;
  private final Texture texture;


  public Mesh(float[] positions, float[] textureCoordinates, int[] indices, Texture texture) {
    FloatBuffer positionsBuffer = null;
    FloatBuffer textureCoordinatesBuffer = null;
    IntBuffer indicesBuffer = null;
    this.vboIdList = new ArrayList<>();
    this.texture = texture;
    try {
      numberOfVertices = indices.length;

      vaoId = glGenVertexArrays();
      glBindVertexArray(vaoId);

      // Position VBO
      positionsBuffer = MemoryUtil.memAllocFloat(positions.length);
      positionsBuffer.put(positions).flip();
      int positionVboId = glGenBuffers();
      vboIdList.add(positionVboId);
      glBindBuffer(GL_ARRAY_BUFFER, positionVboId);
      glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

      // Texture VBO
      int vboId = glGenBuffers();
      vboIdList.add(vboId);
      textureCoordinatesBuffer = MemoryUtil.memAllocFloat(textureCoordinates.length);
      textureCoordinatesBuffer.put(textureCoordinates).flip();
      glBindBuffer(GL_ARRAY_BUFFER, vboId);
      glBufferData(GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

      // Index VBO
      indicesBuffer = MemoryUtil.memAllocInt(indices.length);
      indicesBuffer.put(indices).flip();
      int indexVboId = glGenBuffers();
      glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexVboId);
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
      if (textureCoordinatesBuffer != null) {
        MemoryUtil.memFree(textureCoordinatesBuffer);
      }
    }
  }

  public void render() {
    // Activate firs texture bank
    glActiveTexture(GL_TEXTURE0);
    // Bind the texture
    glBindTexture(GL_TEXTURE_2D, texture.getId());

    // Bind to the VAO
    glBindVertexArray(vaoId);
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    glDrawElements(GL_TRIANGLES, numberOfVertices, GL_UNSIGNED_INT, 0);

    // Restore state
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    glBindVertexArray(0);
  }

  public void cleanup() {
    glDisableVertexAttribArray(0);

    // Delete the VBOs
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    vboIdList.forEach(GL15::glDeleteBuffers);

    texture.cleanup();

    // Delete the VAO
    glBindVertexArray(0);
    glDeleteVertexArrays(vaoId);
  }
}
