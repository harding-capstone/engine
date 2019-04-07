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
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import com.google.common.base.Preconditions;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
import org.lwjgl.system.MemoryStack;

/**
 * A 3D object that is drawable and has a texture.
 */
public class TexturedMesh {

  private static final int VERTICES_BUFFER_INDEX = 0;
  private static final int TEXTURE_COORDINATES_BUFFER_INDEX = 1;

  private final int indicesLength;
  private final int glVaoId;
  private final int glPositionsVboId;
  private final int glTextureCoordinatesVboId;
  private final int glIndicesVboId;
  private final Texture texture;
  private boolean isRenderable;

  public TexturedMesh(float[] vertices,
      float[] textureCoordinates,
      int[] indices,
      Texture texture) {
    Preconditions.checkArgument(indices.length % 3 == 0);
    Preconditions.checkArgument(textureCoordinates.length % 2 == 0);
    Preconditions.checkArgument(indices.length / 3 == textureCoordinates.length / 6);

    this.isRenderable = false;
    this.texture = texture;
    this.indicesLength = indices.length;

    glVaoId = glGenVertexArrays();
    glPositionsVboId = glGenBuffers();
    glTextureCoordinatesVboId = glGenBuffers();
    glIndicesVboId = glGenBuffers();

    bindVertexArray();
    glBindTexture(GL_TEXTURE_2D, texture.getGlTextureId());

    try (var stack = MemoryStack.stackPush()) {
      var verticesBuffer = stack.mallocFloat(vertices.length);
      verticesBuffer.put(vertices).flip();
      bindGlPositionsVbo();
      glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(VERTICES_BUFFER_INDEX, 3, GL_FLOAT, false, 0, 0);
      glEnableVertexAttribArray(VERTICES_BUFFER_INDEX);

      var textureCoordinatesBuffer = stack.mallocFloat(textureCoordinates.length);
      bindGlTextureCoordinatesVbo();
      textureCoordinatesBuffer.put(textureCoordinates).flip();
      glBufferData(GL_ARRAY_BUFFER, textureCoordinatesBuffer, GL_STATIC_DRAW);
      glVertexAttribPointer(TEXTURE_COORDINATES_BUFFER_INDEX, 2, GL_FLOAT, false, 0, 0);
      glEnableVertexAttribArray(TEXTURE_COORDINATES_BUFFER_INDEX);

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

    glBindVertexArray(glVaoId);
    glDrawElements(GL_TRIANGLES, indicesLength, GL_UNSIGNED_INT, 0);
    unbindVertexArray();
  }

  /**
   * Cleans up OpenGL resources.
   */
  public void cleanup() {
    isRenderable = false;

    unbindBuffer();
    glDeleteBuffers(glPositionsVboId);
    glDeleteBuffers(glTextureCoordinatesVboId);
    glDeleteBuffers(glIndicesVboId);

    unbindVertexArray();
    glDeleteVertexArrays(glVaoId);
  }
}
