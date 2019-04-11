package com.shepherdjerred.capstone.engine.game.scenes.mainmenu;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11C.GL_SRC_ALPHA;

import com.shepherdjerred.capstone.engine.engine.events.WindowResizeEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.matrices.ProjectionMatrix;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderUniform;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.object.GameObject;
import com.shepherdjerred.capstone.engine.engine.scene.SceneRenderer;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class MainMenuRenderer implements SceneRenderer<MainMenuScene> {

  private final ResourceManager resourceManager;
  private final EventBus<Event> eventBus;
  private WindowSize windowSize;
  private ProjectionMatrix projectionMatrix;
  private ShaderProgram textShaderProgram;
  private ShaderProgram defaultShaderProgram;

  public MainMenuRenderer(ResourceManager resourceManager,
      EventBus<Event> eventBus,
      WindowSize windowSize) {
    this.resourceManager = resourceManager;
    this.eventBus = eventBus;
    this.windowSize = windowSize;
  }

  @Override
  public void render(MainMenuScene scene) {
    clearScreen();
    updateProjectionMatrix();

    defaultShaderProgram.bind();
    defaultShaderProgram.setUniform(ShaderUniform.PROJECTION_MATRIX, projectionMatrix.getMatrix());
    textShaderProgram.bind();
    textShaderProgram.setUniform(ShaderUniform.PROJECTION_MATRIX, projectionMatrix.getMatrix());

    scene.getGameObjects().forEach(element -> element.getRenderer().render(windowSize, element));
  }

  @Override
  public void initialize(MainMenuScene scene) throws Exception {
    projectionMatrix = new ProjectionMatrix(windowSize);
    createShaderProgram();
    enableTransparency();
    enableDepth();
    registerEventHandlers();

    for (GameObject gameObject : scene.getGameObjects()) {
      gameObject.getRenderer().init(gameObject);
    }
  }

  private void registerEventHandlers() {
    var windowResizeEventHandler = new EventHandler<WindowResizeEvent>() {
      @Override
      public void handle(WindowResizeEvent windowResizeEvent) {
        projectionMatrix = new ProjectionMatrix(windowResizeEvent.getNewWindowSize());
      }
    };
    eventBus.registerHandler(WindowResizeEvent.class, windowResizeEventHandler);
  }

  private void createShaderProgram() throws Exception {
    defaultShaderProgram = resourceManager.get(ShaderProgramName.DEFAULT);
    textShaderProgram = resourceManager.get(ShaderProgramName.TEXT);
  }

  private void enableDepth() {
    glEnable(GL_DEPTH);
    glDepthFunc(GL_LEQUAL);
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
    resourceManager.free(ShaderProgramName.TEXT);
    resourceManager.free(ShaderProgramName.DEFAULT);
    removeEventHandlers();
  }

  // TODO
  private void removeEventHandlers() {

  }
}
