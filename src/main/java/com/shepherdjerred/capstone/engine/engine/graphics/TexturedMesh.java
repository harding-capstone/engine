package com.shepherdjerred.capstone.engine.engine.graphics;

import static com.shepherdjerred.capstone.engine.engine.graphics.OpenGlHelper.unbindBuffer;
import static com.shepherdjerred.capstone.engine.engine.graphics.OpenGlHelper.unbindVertexArray;
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
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import org.lwjgl.system.MemoryStack;

public class TexturedMesh {

  private final int indicesLength;
  private final int glVaoId;
  private final int glPositionsVboId;
  private final int glTextureCoordinatesVboId;
  private final int glIndicesVboId;
  private final Texture texture;
  private boolean isRenderable;

  public TexturedMesh(float[] vertices, float[] textureCoordinates, int[] indices, Texture texture) {
//    Preconditions.checkArgument(vertices.length == 3);
//    Preconditions.checkArgument(textureCoordinates.length == 2);

    this.isRenderable = false;
    this.texture = texture;
    this.indicesLength = indices.length;

    glVaoId = glGenVertexArrays();
    glPositionsVboId = glGenBuffers();
    glTextureCoordinatesVboId = glGenBuffers();
    glIndicesVboId = glGenBuffers();

    bindVertexArray();
    glBindTexture(GL_TEXTURE_2D, texture.getOpenGlTextureId());

    try (var stack = MemoryStack.stackPush()) {
      var verticesBuffer = stack.mallocFloat(vertices.length);
      verticesBuffer.put(vertices).flip();
      bindGlPositionsVbo();
      glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

      var textureCoordinatesBuffer = stack.mallocFloat(textureCoordinates.length);
      bindGlTextureCoordinatesVbo();
      textureCoordinatesBuffer.put(textureCoordinates).flip();
      glBufferData(GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

      var indicesBuffer = stack.mallocInt(indices.length);
      indicesBuffer.put(indices).flip();
      bindGlIndicesBuffer();
      glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
    }

    unbindVertexArray();
    unbindBuffer();
    this.isRenderable = true;
  }

  private void bindVertexArray() {
    glBindVertexArray(glVaoId);
  }

  private void bindGlPositionsVbo() {
    glBindBuffer(GL_ARRAY_BUFFER, glPositionsVboId);
  }

  private void bindGlTextureCoordinatesVbo() {
    glBindBuffer(GL_ARRAY_BUFFER, glTextureCoordinatesVboId);
  }

  private void bindGlIndicesBuffer() {
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, glIndicesVboId);
  }

  /**
   * Draws this object.
   */
  public void render() {
    if (!isRenderable) {
      throw new IllegalStateException();
    }

    // Activate first texture bank
    glActiveTexture(GL_TEXTURE0);

    // Bind to the VAO
    glBindVertexArray(glVaoId);
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    glDrawElements(GL_TRIANGLES, indicesLength, GL_UNSIGNED_INT, 0);

    // Restore state
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);
    unbindVertexArray();
  }

  /**
   * Cleans up OpenGL resources.
   */
  public void cleanup() {
    isRenderable = false;
    glDisableVertexAttribArray(0);
    glDisableVertexAttribArray(1);

    unbindBuffer();
    glDeleteBuffers(glPositionsVboId);
    glDeleteBuffers(glTextureCoordinatesVboId);
    glDeleteBuffers(glIndicesVboId);

    texture.cleanup();

    unbindVertexArray();
    glDeleteVertexArrays(glVaoId);
  }
}
