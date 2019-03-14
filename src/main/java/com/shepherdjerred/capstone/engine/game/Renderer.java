package com.shepherdjerred.capstone.engine.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import com.shepherdjerred.capstone.engine.engine.GameItem;
import com.shepherdjerred.capstone.engine.engine.Window;
import com.shepherdjerred.capstone.engine.engine.graphics.ClasspathShaderLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.Transformation;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Renderer {

  private ShaderProgram shaderProgram;
  private Transformation transformation;

  public void init(Window window) throws Exception {
    var shaderLoader = new ClasspathShaderLoader("/shaders");
    shaderProgram = new ShaderProgram(shaderLoader);
    shaderProgram.createVertexShader("vertex.glsl");
    shaderProgram.createFragmentShader("fragment.glsl");
    shaderProgram.link();

    shaderProgram.createUniform("projectionMatrix");
    shaderProgram.createUniform("modelMatrix");

    window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    transformation = new Transformation();
  }

  public void render(Window window, List<GameItem> gameItems) {
    clear();

    if (window.isResized()) {
      glViewport(0, 0, window.getWidth(), window.getHeight());
      window.setResized(false);
    }

    var width = window.getWidth();
    var height = window.getHeight();

    shaderProgram.bind();
    shaderProgram.setMatrixUniform("projectionMatrix",
        transformation.getProjectionMatrix(width, height));

    gameItems.forEach(gameItem -> {
      var modelMatrix = transformation.getModelMatrix(gameItem.getPosition(),
          gameItem.getRotation(),
          gameItem.getScale());
      shaderProgram.setMatrixUniform("modelMatrix", modelMatrix);
      gameItem.getMesh().render();
    });

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
