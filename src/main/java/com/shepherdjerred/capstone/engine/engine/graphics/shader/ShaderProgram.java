package com.shepherdjerred.capstone.engine.engine.graphics.shader;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

@Log4j2
public class ShaderProgram {

  private final Map<String, Integer> uniforms;
  private final ShaderLoader shaderLoader;
  private final int programId;
  private int vertexShaderId;
  private int fragmentShaderId;

  public ShaderProgram(ShaderLoader shaderLoader) {
    programId = glCreateProgram();
    if (programId == 0) {
      throw new RuntimeException("Could not create Shader");
    }
    this.shaderLoader = shaderLoader;
    uniforms = new HashMap<>();
  }

  public void createUniform(String name) {
    int uniformLocation = glGetUniformLocation(programId, name);
    if (uniformLocation < 0) {
      throw new RuntimeException("Could not create uniform:" + name);
    }
    uniforms.put(name, uniformLocation);
  }

  public void setUniform(String uniformName, int value) {
    glUniform1i(uniforms.get(uniformName), value);
  }

  public void setUniform(String uniformName, Matrix4f value) {
    // Dump the matrix into a float buffer
    try (MemoryStack stack = MemoryStack.stackPush()) {
      FloatBuffer fb = stack.mallocFloat(16);
      value.get(fb);
      glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
    }
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
      throw new RuntimeException("Error creating shader. Type: " + shaderType);
    }

    glShaderSource(shaderId, shaderCode);
    glCompileShader(shaderId);

    if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
      throw new RuntimeException(
          "Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
    }

    glAttachShader(programId, shaderId);

    return shaderId;
  }

  public void link() {
    glLinkProgram(programId);
    if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
      throw new RuntimeException(
          "Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
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
