package com.shepherdjerred.capstone.engine.engine.graphics.texture;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Texture {

  private final TextureName textureName;
  private final int openGlTextureId;
  private final int width;
  private final int height;
  private final int numberOfChannels;

  public void bind() {
    glBindTexture(GL_TEXTURE_2D, openGlTextureId);
  }

  public void cleanup() {
    glDeleteTextures(openGlTextureId);
  }
}
