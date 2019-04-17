package com.shepherdjerred.capstone.engine.game.scenes.game;

import com.shepherdjerred.capstone.engine.engine.events.WindowResizeEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.Color;
import com.shepherdjerred.capstone.engine.engine.graphics.OpenGlHelper;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ProjectionMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;

public class GameRenderer implements SceneRenderer<GameScene> {

  private final ResourceManager resourceManager;
  private final EventBus<Event> eventBus;
  private WindowSize windowSize;
  private ProjectionMatrix projectionMatrix;
  private ShaderProgram textShaderProgram;
  private ShaderProgram defaultShaderProgram;
  private final EventHandlerFrame<Event> eventHandlerFrame;

  public GameRenderer(ResourceManager resourceManager,
      EventBus<Event> eventBus,
      WindowSize windowSize) {
    this.resourceManager = resourceManager;
    this.eventBus = eventBus;
    this.eventHandlerFrame = new EventHandlerFrame<>();
    this.windowSize = windowSize;
  }

  @Override
  public void initialize(GameScene scene) throws Exception {
    updateProjectionMatrix();
    createShaderProgram();
    initializeEventHandlerFrame();
    OpenGlHelper.setClearColor(Color.black());
    OpenGlHelper.enableDepthBuffer();

    for (GameObject gameObject : scene.getGameObjects()) {
      gameObject.initialize();
    }

    eventBus.registerHandlerFrame(eventHandlerFrame);
  }

  private void initializeEventHandlerFrame() {
    var windowResizeEventHandler = new EventHandler<WindowResizeEvent>() {
      @Override
      public void handle(WindowResizeEvent windowResizeEvent) {
        windowSize = windowResizeEvent.getNewWindowSize();
        updateProjectionMatrix();
        // TODO recreate all renderer classes
      }
    };

    eventHandlerFrame.registerHandler(WindowResizeEvent.class, windowResizeEventHandler);
  }

  private void createShaderProgram() throws Exception {
    defaultShaderProgram = resourceManager.get(ShaderProgramName.DEFAULT);
    textShaderProgram = resourceManager.get(ShaderProgramName.TEXT);
  }

  @Override
  public void render(GameScene scene) {
    OpenGlHelper.clearScreen();
    updateProjectionMatrix();

    defaultShaderProgram.bind();
    defaultShaderProgram.setUniform(ShaderUniform.PROJECTION_MATRIX, projectionMatrix.getMatrix());
//    textShaderProgram.bind();
//    textShaderProgram.setUniform(ShaderUniform.PROJECTION_MATRIX, projectionMatrix.getMatrix());

    scene.getGameObjects().forEach(element -> element.render(windowSize));
  }

  private void updateProjectionMatrix() {
    projectionMatrix = new ProjectionMatrix(windowSize);
  }

  @Override
  public void cleanup() {
    resourceManager.free(ShaderProgramName.TEXT);
    resourceManager.free(ShaderProgramName.DEFAULT);
    eventBus.removeHandlerFrame(eventHandlerFrame);
  }
}
