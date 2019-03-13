package com.shepherdjerred.capstone.engine.engine.graphics;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ToString
@AllArgsConstructor
public class ClasspathShaderLoader implements ShaderLoader {

  private final String basePath;

  @Override
  public String loadShaderCode(String shaderName) throws IOException {
    var stream = this.getClass().getResourceAsStream(basePath + "/" + shaderName);
    if (stream == null) {
      throw new FileNotFoundException(shaderName);
    }
    var bytes = stream.readAllBytes();
    stream.close();
    return new String(bytes, StandardCharsets.UTF_8);
  }
}
