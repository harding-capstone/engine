package com.shepherdjerred.capstone.engine.game;

import com.shepherdjerred.capstone.engine.engine.GameLogic;
import com.shepherdjerred.capstone.engine.engine.audio.AudioLoader;
import com.shepherdjerred.capstone.engine.engine.audio.AudioName;
import com.shepherdjerred.capstone.engine.engine.audio.AudioPlayer;
import com.shepherdjerred.capstone.engine.engine.events.handlers.scene.SceneTransitionEventHandler;
import com.shepherdjerred.capstone.engine.engine.events.scene.SceneTransitionEvent;
import com.shepherdjerred.capstone.engine.engine.graphics.OpenGlHelper;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.ShaderProgramName;
import com.shepherdjerred.capstone.engine.engine.graphics.shader.code.ClasspathFileShaderCodeLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.resource.ByteBufferLoader;
import com.shepherdjerred.capstone.engine.engine.resource.PathResourceFileLocator;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceFileLocator;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneManager;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuAudio;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuScene;
import com.shepherdjerred.capstone.engine.game.scenes.teamintro.TeamIntroRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.teamintro.TeamIntroScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CastleCastersGame implements GameLogic {

  private final EventBus<Event> eventBus;
  private final ResourceManager resourceManager;
  private SceneManager sceneManager;
  private AudioPlayer audioPlayer;

  public CastleCastersGame(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.resourceManager = new ResourceManager();
    this.audioPlayer = new AudioPlayer(eventBus);
    registerLoaders();
  }

  private void registerLoaders() {
    ResourceFileLocator resourceFileLocator = new PathResourceFileLocator(
        "/textures/",
        "/fonts/",
        "/audio/"
    );
    var textureLoader = new TextureLoader(resourceFileLocator);
    var shaderLoader = new ShaderProgramLoader(new ClasspathFileShaderCodeLoader("/shaders/"));
    var fontLoader = new FontLoader(resourceFileLocator);
    var audioLoader = new AudioLoader(resourceFileLocator, new ByteBufferLoader());

    resourceManager.registerLoader(TextureName.class, textureLoader);
    resourceManager.registerLoader(ShaderProgramName.class, shaderLoader);
    resourceManager.registerLoader(FontName.class, fontLoader);
    resourceManager.registerLoader(AudioName.class, audioLoader);
  }

  @Override
  public void initialize(WindowSize windowSize) throws Exception {
    OpenGlHelper.enableDepthBuffer();
    OpenGlHelper.enableTransparency();
    OpenGlHelper.setClearColor();

    var scene = getTeamScene(windowSize);
    this.sceneManager = new SceneManager(eventBus, scene);
    sceneManager.initialize();

    eventBus.registerHandler(SceneTransitionEvent.class,
        new SceneTransitionEventHandler(sceneManager));

    audioPlayer.initialize();
  }

  private Scene getTeamScene(WindowSize windowSize) {
    var sceneRenderer = new TeamIntroRenderer(resourceManager, eventBus, windowSize);
    var scene = new TeamIntroScene(sceneRenderer,
        null,
        resourceManager,
        eventBus,
        windowSize);
    return scene;
  }

  private Scene getMainMenuScene(WindowSize windowSize) {
    var sceneRenderer = new MainMenuRenderer(resourceManager, eventBus, windowSize);
    var scene = new MainMenuScene(sceneRenderer,
        resourceManager,
        eventBus,
        windowSize,
        new MainMenuAudio(eventBus, resourceManager));
    return scene;
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
    sceneManager.cleanup();
    var references = resourceManager.getReferenceCounter();
    if (references.size() > 0) {
      log.warn("Resource leak(s) detected. " + references);
    } else {
      log.info("No resource leaks detected :)");
    }
    audioPlayer.cleanup();
    resourceManager.freeAll();
  }
}
