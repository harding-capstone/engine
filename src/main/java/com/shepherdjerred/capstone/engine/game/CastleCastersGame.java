package com.shepherdjerred.capstone.engine.game;

import com.shepherdjerred.capstone.engine.engine.GameLogic;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.code.ClasspathFileShaderCodeLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgram;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.util.PathBasedResourceFileLocator;
import com.shepherdjerred.capstone.engine.engine.util.ResourceFileLocator;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scene.SceneManager;
import com.shepherdjerred.capstone.engine.game.scene.rendering.MainMenuSceneRenderer;
import com.shepherdjerred.capstone.engine.game.scene.MainMenuScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CastleCastersGame implements GameLogic {

  private final EventBus<Event> eventBus;
  private final TextureLoader textureLoader;
  private final FontLoader fontLoader;
  private SceneManager sceneManager;

  public CastleCastersGame(EventBus<Event> eventBus, WindowSize windowSize) {
    this.eventBus = eventBus;
    ResourceFileLocator resourceFileLocator = new PathBasedResourceFileLocator(
        "/Users/jerred/programming/capstone/engine/src/main/resources/textures/",
        "/Users/jerred/programming/capstone/engine/src/main/resources/fonts/"
    );
    this.textureLoader = new TextureLoader(resourceFileLocator);
    this.fontLoader = new FontLoader(resourceFileLocator);
  }

  @Override
  public void initialize(WindowSize windowSize) throws Exception {
    var sceneRenderer = new MainMenuSceneRenderer(eventBus,
        new ShaderProgram(new ClasspathFileShaderCodeLoader("/shaders")),
        windowSize,
        textureLoader);
    var scene = new MainMenuScene(sceneRenderer,
        eventBus,
        textureLoader,
        fontLoader,
        new WindowSize(1360, 768));
    scene.initialize();
    sceneRenderer.initialize(scene);
    this.sceneManager = new SceneManager(scene);
    sceneManager.initialize();
  }

  @Override
  public void updateGameState(float interval) {
    sceneManager.update(interval);
  }

  @Override
  public void render() {
    sceneManager.render();
  }

  @Override
  public void cleanup() {
  }
}
