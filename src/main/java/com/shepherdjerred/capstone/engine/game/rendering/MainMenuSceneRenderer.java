package com.shepherdjerred.capstone.engine.game.rendering;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11C.GL_SRC_ALPHA;

import com.shepherdjerred.capstone.engine.engine.event.WindowResizedEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ModelMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ProjectionMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ClasspathShaderCodeLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.MainMenuScene;
import com.shepherdjerred.capstone.engine.game.scene.element.ButtonRenderer;
import com.shepherdjerred.capstone.engine.game.scene.element.ButtonSceneElement;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandler;

public class MainMenuSceneRenderer implements SceneRenderer<MainMenuScene> {

  private final EventBus<Event> eventBus;
  private ShaderProgram shaderProgram;
  private WindowSize windowSize;
  private final TextureLoader textureLoader;
  private ProjectionMatrix projectionMatrix;

  public MainMenuSceneRenderer(EventBus<Event> eventBus,
      ShaderProgram shaderProgram,
      WindowSize windowSize,
      TextureLoader textureLoader) {
    this.eventBus = eventBus;
    this.shaderProgram = shaderProgram;
    this.windowSize = windowSize;
    this.textureLoader = textureLoader;

  }

  @Override
  public void render(MainMenuScene scene) {
    clearScreen();

    updateProjectionMatrix();

    shaderProgram.bind();
    shaderProgram.setUniform(ShaderUniform.TEXTURE_SAMPLER, 0);
    shaderProgram.setUniform(ShaderUniform.PROJECTION_MATRIX, projectionMatrix.getMatrix());

    var buttonRenderer = new ButtonRenderer(textureLoader);

    scene.getSceneElements().forEach(element -> {
      buttonRenderer.init((ButtonSceneElement) element);
      var modelMatrix = new ModelMatrix(new RendererCoordinate(0, 0, 0), 0, 1);
      shaderProgram.setUniform(ShaderUniform.MODEL_MATRIX, modelMatrix.getMatrix());
      buttonRenderer.render((ButtonSceneElement) element);
    });

    shaderProgram.unbind();
  }

  @Override
  public void initialize(MainMenuScene scene) throws Exception {
    projectionMatrix = new ProjectionMatrix(windowSize);
    createShaderProgram();
    enableTransparency();
  }

  private void registerEventHandlers() {
    var windowResizeEventHandler = new EventHandler<WindowResizedEvent>() {
      @Override
      public void handle(WindowResizedEvent windowResizedEvent) {
        projectionMatrix = new ProjectionMatrix(windowResizedEvent.getNewWindowSize());
      }
    };
    eventBus.registerHandler(WindowResizedEvent.class, windowResizeEventHandler);
  }

  private void createShaderProgram() throws Exception {
    var shaderLoader = new ClasspathShaderCodeLoader("/shaders/");
    shaderProgram = new ShaderProgram(shaderLoader);
    shaderProgram.createVertexShader("vertex.glsl");
    shaderProgram.createFragmentShader("fragment.glsl");
    shaderProgram.link();

    shaderProgram.createUniform(ShaderUniform.PROJECTION_MATRIX);
    shaderProgram.createUniform(ShaderUniform.MODEL_MATRIX);
    shaderProgram.createUniform(ShaderUniform.TEXTURE_SAMPLER);
  }

  private void enableTransparency() {
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  private void updateProjectionMatrix() {
    projectionMatrix = new ProjectionMatrix(windowSize);
  }

  public void clearScreen() {
    glClearColor(0, 0, 0, 0);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  }

  @Override
  public void cleanup() {
    if (shaderProgram != null) {
      shaderProgram.cleanup();
    }
  }

  private void deregisterEventHandlers() {

  }
}
