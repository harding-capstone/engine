package com.shepherdjerred.capstone.engine.engine.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ShaderProgram {

  private final ShaderLoader shaderLoader;
  private final int programId;
  private int vertexShaderId;
  private int fragmentShaderId;

  public ShaderProgram(ShaderLoader shaderLoader) throws Exception {
    programId = glCreateProgram();
    if (programId == 0) {
      throw new Exception("Could not create Shader");
    }
    this.shaderLoader = shaderLoader;
  }

  private String loadShaderCode(String shaderName) throws IOException {
    return shaderLoader.loadShaderCode(shaderName);
  }

  public void createVertexShader(String shaderName) throws Exception {
    vertexShaderId = createShader(shaderName, GL_VERTEX_SHADER);
  }

  public void createFragmentShader(String shaderName) throws Exception {
    fragmentShaderId = createShader(shaderName, GL_FRAGMENT_SHADER);
  }

  private int createShader(String shaderName, int shaderType) throws Exception {
    var shaderCode = loadShaderCode(shaderName);
    int shaderId = glCreateShader(shaderType);
    if (shaderId == 0) {
      throw new Exception("Error creating shader. Type: " + shaderType);
    }

    glShaderSource(shaderId, shaderCode);
    glCompileShader(shaderId);

    if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
      throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
    }

    glAttachShader(programId, shaderId);

    return shaderId;
  }

  public void link() throws Exception {
    glLinkProgram(programId);
    if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
      throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
    }

    if (vertexShaderId != 0) {
      glDetachShader(programId, vertexShaderId);
    }
    if (fragmentShaderId != 0) {
      glDetachShader(programId, fragmentShaderId);
    }

    glValidateProgram(programId);
    if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
      log.warn("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
    }
  }

  public void bind() {
    glUseProgram(programId);
  }

  public void unbind() {
    glUseProgram(0);
  }

  public void cleanup() {
    unbind();
    if (programId != 0) {
      glDeleteProgram(programId);
    }
  }
}
