package com.shepherdjerred.capstone.engine.game;

import com.shepherdjerred.capstone.engine.engine.GameLogic;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.code.ClasspathFileShaderCodeLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.locator.PathBasedTextureFileLocator;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.locator.TextureFileLocator;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.rendering.MainMenuSceneRenderer;
import com.shepherdjerred.capstone.engine.game.scene.rendering.SceneRenderer;
import com.shepherdjerred.capstone.engine.game.scene.MainMenuScene;
import com.shepherdjerred.capstone.engine.game.scene.Scene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CastleCastersGame implements GameLogic {

  private final EventBus<Event> eventBus;
  private final TextureFileLocator textureFileLocator;
  private final TextureLoader textureLoader;
  private Scene scene;
  private SceneRenderer sceneRenderer;


  public CastleCastersGame(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.textureFileLocator = new PathBasedTextureFileLocator(
        "/Users/jerred/programming/capstone/engine/src/main/resources/textures/");
    this.textureLoader = new TextureLoader(textureFileLocator);

    scene = new MainMenuScene(eventBus);
    scene.initialize();
  }

  @Override
  public void initialize(WindowSize windowSize) throws Exception {
    sceneRenderer = new MainMenuSceneRenderer(eventBus,
        new ShaderProgram(new ClasspathFileShaderCodeLoader("/shaders")),
        windowSize,
        textureLoader);

    sceneRenderer.initialize(scene);
  }

  @Override
  public void updateGameState(float interval) {
    scene.updateState(interval);
  }

  @Override
  public void render() {
    sceneRenderer.render(scene);
  }

  @Override
  public void cleanup() {
    sceneRenderer.cleanup();
  }
}
