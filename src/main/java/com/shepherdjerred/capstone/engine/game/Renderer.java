package com.shepherdjerred.capstone.engine.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import com.shepherdjerred.capstone.engine.engine.Window;
import com.shepherdjerred.capstone.engine.engine.graphics.ClasspathShaderLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.ShaderProgram;
import lombok.extern.log4j.Log4j2;
import org.joml.Matrix4f;

@Log4j2
public class Renderer {

  private ShaderProgram shaderProgram;
  private Matrix4f projectionMatrix;

  public void init(Window window) throws Exception {
    var shaderLoader = new ClasspathShaderLoader("/shaders");
    shaderProgram = new ShaderProgram(shaderLoader);
    shaderProgram.createVertexShader("vertex.glsl");
    shaderProgram.createFragmentShader("fragment.glsl");
    shaderProgram.link();

    shaderProgram.createUniform("projectionMatrix");

    var fov = (float) Math.toRadians(60);
    var aspectRatio = (float) window.getWidth() / window.getHeight();
//    projectionMatrix = new Matrix4f().perspective(fov, aspectRatio, 0.01f, 1000);
    projectionMatrix = new Matrix4f().ortho2D(0, window.getWidth(), window.getHeight(), 0);
    log.info(projectionMatrix);
  }

  public void render(Window window, Mesh mesh) {
    clear();

    if (window.isResized()) {
      glViewport(0, 0, window.getWidth(), window.getHeight());
      window.setResized(false);
    }

    shaderProgram.bind();
    // TODO should this be done every render? probably not
    shaderProgram.setMatrixUniform("projectionMatrix", projectionMatrix);

    // Bind to the VAO
    glBindVertexArray(mesh.getVaoId());
    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);
    glDrawElements(GL_TRIANGLES, mesh.getNumberOfVerticies(), GL_UNSIGNED_INT, 0);

    // Restore state
    glDisableVertexAttribArray(0);
    glBindVertexArray(0);

    shaderProgram.unbind();
  }

  public void clear() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  public void cleanup() {
    if (shaderProgram != null) {
      shaderProgram.cleanup();
    }
  }
}
