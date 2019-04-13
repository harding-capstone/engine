package com.shepherdjerred.capstone.engine.game.scenes.singleplayer;

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
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SinglePlayerRenderer implements SceneRenderer<SinglePlayerScene> {

  private final ResourceManager resourceManager;
  private final EventBus<Event> eventBus;
  private WindowSize windowSize;
  private ProjectionMatrix projectionMatrix;
  private ShaderProgram defaultShaderProgram;

  public SinglePlayerRenderer(ResourceManager resourceManager,
      EventBus<Event> eventBus,
      WindowSize windowSize) {
    this.resourceManager = resourceManager;
    this.eventBus = eventBus;
    this.windowSize = windowSize;
  }

  @Override
  public void render(SinglePlayerScene scene) {
    OpenGlHelper.clearScreen();
    updateProjectionMatrix();

    defaultShaderProgram.bind();
    defaultShaderProgram.setUniform(ShaderUniform.PROJECTION_MATRIX, projectionMatrix.getMatrix());

    scene.getGameObjects().forEach(element -> element.getRenderer().render(windowSize, element));
  }

  @Override
  public void initialize(SinglePlayerScene scene) throws Exception {
    updateProjectionMatrix();
    createShaderProgram();
    registerEventHandlers();
    OpenGlHelper.setClearColor(Color.black());

    for (GameObject gameObject : scene.getGameObjects()) {
      gameObject.getRenderer().init(gameObject);
    }
  }

  private void registerEventHandlers() {
    var windowResizeEventHandler = new EventHandler<WindowResizeEvent>() {
      @Override
      public void handle(WindowResizeEvent windowResizeEvent) {
        windowSize = windowResizeEvent.getNewWindowSize();
        updateProjectionMatrix();
      }
    };

    eventBus.registerHandler(WindowResizeEvent.class, windowResizeEventHandler);
  }

  private void createShaderProgram() throws Exception {
    defaultShaderProgram = resourceManager.get(ShaderProgramName.DEFAULT);
  }

  private void updateProjectionMatrix() {
    projectionMatrix = new ProjectionMatrix(windowSize);
  }

  @Override
  public void cleanup() {
    resourceManager.free(ShaderProgramName.DEFAULT);
    removeEventHandlers();
  }

  // TODO
  private void removeEventHandlers() {

  }
}
