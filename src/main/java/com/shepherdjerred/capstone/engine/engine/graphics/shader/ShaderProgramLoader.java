package com.shepherdjerred.capstone.engine.engine.graphics.shader;

import com.shepherdjerred.capstone.engine.engine.graphics.shader.code.ShaderCodeLoader;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceLoader;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShaderProgramLoader implements ResourceLoader<ShaderProgramName, ShaderProgram> {

  private final ShaderCodeLoader shaderCodeLoader;

  @Override
  public ShaderProgram get(ShaderProgramName identifier) throws Exception {
    switch (identifier) {
      case DEFAULT:
        return getDefaultShaderProgram();
      case TEXT:
        return getTextShaderProgram();
      default:
        throw new UnsupportedOperationException("Unknown shader program: " + identifier);
    }
  }

  private ShaderProgram getDefaultShaderProgram() throws Exception {
    ShaderProgram defaultShaderProgram = new ShaderProgram(shaderCodeLoader);
    defaultShaderProgram.createVertexShader("vertex.glsl");
    defaultShaderProgram.createFragmentShader("fragment.glsl");
    defaultShaderProgram.link();

    defaultShaderProgram.createUniform(ShaderUniform.PROJECTION_MATRIX);
    defaultShaderProgram.createUniform(ShaderUniform.MODEL_MATRIX);
    defaultShaderProgram.createUniform(ShaderUniform.TEXTURE_SAMPLER);
    return defaultShaderProgram;
  }

  private ShaderProgram getTextShaderProgram() throws Exception {
    ShaderProgram textShaderProgram = new ShaderProgram(shaderCodeLoader);
    textShaderProgram.createVertexShader("vertex.glsl");
    textShaderProgram.createFragmentShader("textFragment.glsl");
    textShaderProgram.link();

    textShaderProgram.createUniform(ShaderUniform.PROJECTION_MATRIX);
    textShaderProgram.createUniform(ShaderUniform.MODEL_MATRIX);
    textShaderProgram.createUniform(ShaderUniform.TEXTURE_SAMPLER);
    textShaderProgram.createUniform(ShaderUniform.TEXT_COLOR);
    return textShaderProgram;
  }
}
