package com.shepherdjerred.capstone.engine.game;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11C.GL_SRC_ALPHA;

import com.shepherdjerred.capstone.engine.engine.RenderedElement;
import com.shepherdjerred.capstone.engine.engine.Window;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ClasspathShaderCodeLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.Matrices;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import java.util.List;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Renderer {

  private ShaderProgram shaderProgram;
  private Matrices matrices;

  public void init(Window window) throws Exception {
    var shaderLoader = new ClasspathShaderCodeLoader("/shaders/");
    shaderProgram = new ShaderProgram(shaderLoader);
    shaderProgram.createVertexShader("vertex.glsl");
    shaderProgram.createFragmentShader("fragment.glsl");
    shaderProgram.link();

    shaderProgram.createUniform(ShaderUniform.PROJECTION_MATRIX);
    shaderProgram.createUniform(ShaderUniform.MODEL_MATRIX);
    shaderProgram.createUniform(ShaderUniform.TEXTURE_SAMPLER);

    window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    matrices = new Matrices();

    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  public void render(Window window, List<RenderedElement> renderedElements) {
    clear();

    if (window.isResized()) {
      glViewport(0, 0, window.getWidth(), window.getHeight());
      window.setResized(false);
    }

    var width = window.getWidth();
    var height = window.getHeight();

    shaderProgram.bind();
    shaderProgram.setUniform(ShaderUniform.TEXTURE_SAMPLER, 0);
    shaderProgram.setUniform(ShaderUniform.PROJECTION_MATRIX,
        matrices.getProjectionMatrix(width, height));

    renderedElements.forEach(renderedElement -> {
      var modelMatrix = matrices.getModelMatrix(renderedElement.getPosition(),
          renderedElement.getRotation(),
          renderedElement.getScale());
      shaderProgram.setUniform(ShaderUniform.MODEL_MATRIX, modelMatrix);
      renderedElement.getTexturedMesh().render();
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
