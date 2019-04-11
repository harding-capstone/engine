package com.shepherdjerred.capstone.engine.engine.graphics.font;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.stb.STBTruetype.stbtt_GetBakedQuad;

import com.google.common.base.Preconditions;
import com.shepherdjerred.capstone.engine.engine.graphics.OpenGlHelper;
import com.shepherdjerred.capstone.engine.engine.graphics.Quad;
import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureQuad;
import com.shepherdjerred.capstone.engine.engine.resource.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.system.MemoryStack;

@Getter
@ToString
@AllArgsConstructor
public class Font implements Resource {

  private final int ascent;
  private final int descent;
  private final int gap;
  private final int glTextureName;
  private final int textureWidth;
  private final int textureHeight;
  private final STBTTBakedChar.Buffer characterBuffer;

  public void bind() {
    glBindTexture(GL_TEXTURE_2D, glTextureName);
  }

  public void unbind() {
    OpenGlHelper.unbindTexture();
  }

  public FontCharacter getFontCharacter(char c, int x, int y) {
    Preconditions.checkArgument(c >= 32 && c < 128);

    try (var stack = MemoryStack.stackPush()) {
      var xBuffer = stack.mallocFloat(1);
      var yBuffer = stack.mallocFloat(1);
      STBTTAlignedQuad quad = STBTTAlignedQuad.mallocStack(stack);

      xBuffer.put(x).flip();
      yBuffer.put(y).flip();

      stbtt_GetBakedQuad(characterBuffer,
          textureWidth,
          textureHeight,
          c - 32,
          xBuffer,
          yBuffer,
          quad,
          true);

      return new FontCharacter(
          c,
          quad.x1() - quad.x0(),
          quad.y1() - quad.y0(),
          new Quad(
              new RendererCoordinate(quad.x1(), quad.y1()),
              new RendererCoordinate(quad.x0(), quad.y1()),
              new RendererCoordinate(quad.x0(), quad.y0()),
              new RendererCoordinate(quad.x1(), quad.y0())
          ),
          new TextureQuad(
              new TextureCoordinate(quad.s1(), quad.t1()),
              new TextureCoordinate(quad.s0(), quad.t1()),
              new TextureCoordinate(quad.s0(), quad.t0()),
              new TextureCoordinate(quad.s1(), quad.t0())
          ));
    }
  }

  @Override
  public void cleanup() {
    glDeleteTextures(glTextureName);
  }
}
