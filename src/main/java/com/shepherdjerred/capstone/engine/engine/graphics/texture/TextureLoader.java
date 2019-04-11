package com.shepherdjerred.capstone.engine.engine.graphics.texture;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11C.GL_TEXTURE_2D;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

import com.shepherdjerred.capstone.engine.engine.resource.ResourceFileLocator;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceLoader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import lombok.ToString;
import org.lwjgl.system.MemoryStack;

/**
 * Loads a texture.
 */
@ToString
public class TextureLoader implements ResourceLoader<TextureName, Texture> {

  private final ResourceFileLocator resourceFileLocator;

  public TextureLoader(ResourceFileLocator resourceFileLocator) {
    this.resourceFileLocator = resourceFileLocator;
  }

  /**
   * Loads a texture from disk into memory.
   */
  public Texture get(TextureName textureName) {
    var textureFilePath = resourceFileLocator.getTexturePath(textureName);

    int textureWidth;
    int textureHeight;
    int numberOfChannelsInTexture;
    ByteBuffer textureBuffer;

    // Use stbi to load the texture
    try (MemoryStack stack = MemoryStack.stackPush()) {
      IntBuffer stackTextureWidth = stack.mallocInt(1);
      IntBuffer stackTextureHeight = stack.mallocInt(1);
      IntBuffer stackNumberOfChannelsInTexture = stack.mallocInt(1);

      textureBuffer = stbi_load(textureFilePath,
          stackTextureWidth,
          stackTextureHeight,
          stackNumberOfChannelsInTexture,
          4);

      if (textureBuffer == null) {
        throw new RuntimeException(
            "Texture " + textureName + " not loaded: " + stbi_failure_reason());
      }

      // Assign variables from data loaded by stbi
      textureWidth = stackTextureWidth.get();
      textureHeight = stackTextureHeight.get();
      numberOfChannelsInTexture = stackNumberOfChannelsInTexture.get();
    }

    var openGlTextureId = sendToOpenGl(textureBuffer, textureWidth, textureHeight);

    stbi_image_free(textureBuffer);

    return new Texture(textureName,
        openGlTextureId,
        textureWidth,
        textureHeight,
        numberOfChannelsInTexture);
  }

  private int sendToOpenGl(ByteBuffer texture, int textureWidth, int textureHeight) {
    // Create and bind a new OpenGL texture
    int openGlTextureId = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, openGlTextureId);

    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

    // Set texture to be resized via nearest neighbor
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

    // Send the texture data to OpenGL
    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, textureWidth, textureHeight, 0,
        GL_RGBA, GL_UNSIGNED_BYTE, texture);

    // Unbind the texture
    glBindTexture(GL_TEXTURE_2D, 0);

    return openGlTextureId;
  }
}
