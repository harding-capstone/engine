package com.shepherdjerred.capstone.engine.engine.graphics.font;

import static org.lwjgl.opengl.GL11.GL_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.stb.STBTruetype.stbtt_BakeFontBitmap;
import static org.lwjgl.stb.STBTruetype.stbtt_GetFontVMetrics;
import static org.lwjgl.stb.STBTruetype.stbtt_InitFont;

import com.shepherdjerred.capstone.engine.engine.util.ResourceFileLocator;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.system.MemoryStack;

@Log4j2
@AllArgsConstructor
public class FontLoader {

  private final ResourceFileLocator fileLocator;

  public Font load(FontName fontName) throws Exception {
    ByteBuffer fontDataBuffer;
    var filePath = fileLocator.getFontPath(fontName);

    FileInputStream inputStream = new FileInputStream(filePath);
    FileChannel fileChannel = inputStream.getChannel();
    fontDataBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
    fileChannel.close();
    inputStream.close();

    var info = STBTTFontinfo.create();
    if (!stbtt_InitFont(info, fontDataBuffer)) {
      throw new Exception("Unable to create font");
    }

    int ascent;
    int descent;
    int gap;

    try (var stack = MemoryStack.stackPush()) {
      var ascentBuffer = stack.mallocInt(1);
      var descentBuffer = stack.mallocInt(1);
      var gapBuffer = stack.mallocInt(1);

      stbtt_GetFontVMetrics(info, ascentBuffer, descentBuffer, gapBuffer);

      ascent = ascentBuffer.get();
      descent = descentBuffer.get();
      gap = gapBuffer.get();
    }

    var bitmapWidth = Math.round(512);
    var bitmapHeight = Math.round(512);
    var characters = STBTTBakedChar.malloc(96);
    var bitmapBuffer = BufferUtils.createByteBuffer(bitmapWidth * bitmapHeight);
    stbtt_BakeFontBitmap(fontDataBuffer,
        24,
        bitmapBuffer,
        bitmapWidth,
        bitmapHeight,
        32,
        characters);

    var glTextureName = glGenTextures();
    glBindTexture(GL_TEXTURE_2D, glTextureName);
    glTexImage2D(GL_TEXTURE_2D,
        0,
        GL_ALPHA,
        bitmapWidth,
        bitmapHeight,
        0,
        GL_ALPHA,
        GL_UNSIGNED_BYTE,
        bitmapBuffer);

    return new Font(ascent, descent, gap, glTextureName);
  }
}
