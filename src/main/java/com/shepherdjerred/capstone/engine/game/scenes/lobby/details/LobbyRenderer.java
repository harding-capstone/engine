package com.shepherdjerred.capstone.engine.game.scenes.lobby.details;

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
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LobbyRenderer implements SceneRenderer<LobbyScene> {

  private final ResourceManager resourceManager;
  private final EventBus<Event> eventBus;
  private WindowSize windowSize;
  private ProjectionMatrix projectionMatrix;
  private ShaderProgram defaultShaderProgram;

  public LobbyRenderer(ResourceManager resourceManager,
      EventBus<Event> eventBus,
      WindowSize windowSize) {
    this.resourceManager = resourceManager;
    this.eventBus = eventBus;
    this.windowSize = windowSize;
  }

  @Override
  public void render(LobbyScene scene) {
    OpenGlHelper.clearScreen();
    updateProjectionMatrix();

    defaultShaderProgram.bind();
    defaultShaderProgram.setUniform(ShaderUniform.PROJECTION_MATRIX, projectionMatrix.getMatrix());

    scene.getGameObjects().forEach(element -> element.render(windowSize));
  }

  @Override
  public void initialize(LobbyScene scene) throws Exception {
    updateProjectionMatrix();
    createShaderProgram();
    OpenGlHelper.setClearColor(Color.black());

    for (GameObject gameObject : scene.getGameObjects()) {
      gameObject.initialize();
    }
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