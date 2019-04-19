package com.shepherdjerred.capstone.engine.game;

import static com.shepherdjerred.capstone.engine.engine.map.GameMapName.GRASS;

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
import com.shepherdjerred.capstone.engine.engine.map.GameMapLoader;
import com.shepherdjerred.capstone.engine.engine.map.GameMapName;
import com.shepherdjerred.capstone.engine.engine.resource.ByteBufferLoader;
import com.shepherdjerred.capstone.engine.engine.resource.PathResourceFileLocator;
import com.shepherdjerred.capstone.engine.engine.resource.ResourceManager;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.SceneTransitioner;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.network.manager.NetworkManager;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground;
import com.shepherdjerred.capstone.engine.game.objects.background.parallax.ParallaxBackground.Type;
import com.shepherdjerred.capstone.engine.game.scenes.game.GameScene;
import com.shepherdjerred.capstone.engine.game.scenes.lobby.list.LobbyListScene;
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
  private final SceneTransitioner sceneTransitioner;
  private final NetworkManager networkManager;
  private final AudioPlayer audioPlayer;
  private WindowSize windowSize;

  public CastleCastersGame(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.resourceManager = new ResourceManager();
    this.audioPlayer = new AudioPlayer(eventBus);
    this.sceneTransitioner = new SceneTransitioner(eventBus);
    this.networkManager = new NetworkManager(eventBus);
    registerLoaders();
  }

  private void registerLoaders() {
    var loader = new ByteBufferLoader();
    var resourceFileLocator = new PathResourceFileLocator(
        "/textures/",
        "/fonts/",
        "/audio/",
        "/maps/"
    );
    var textureLoader = new TextureLoader(resourceFileLocator);
    var shaderLoader = new ShaderProgramLoader(new ClasspathFileShaderCodeLoader("/shaders/"));
    var fontLoader = new FontLoader(resourceFileLocator);
    var audioLoader = new AudioLoader(resourceFileLocator, loader);
    var mapLoader = new GameMapLoader(loader, resourceFileLocator);

    resourceManager.registerLoader(TextureName.class, textureLoader);
    resourceManager.registerLoader(ShaderProgramName.class, shaderLoader);
    resourceManager.registerLoader(FontName.class, fontLoader);
    resourceManager.registerLoader(AudioName.class, audioLoader);
    resourceManager.registerLoader(GameMapName.class, mapLoader);
  }

  @Override
  public void initialize(WindowSize windowSize) throws Exception {
    OpenGlHelper.enableDepthBuffer();
    OpenGlHelper.enableTransparency();
    OpenGlHelper.setClearColor();

    this.windowSize = windowSize;

    var scene = getTeamScene(windowSize);
    sceneTransitioner.initialize(scene);
    audioPlayer.initialize();
    networkManager.initialize();
    registerEventHandlers();
  }

  private void registerEventHandlers() {
    eventBus.registerHandler(SceneTransitionEvent.class,
        new SceneTransitionEventHandler(sceneTransitioner));
  }

  private Scene getGameScene(WindowSize windowSize) {
    return new GameScene(resourceManager, eventBus, GRASS, windowSize);
  }

  private Scene getTeamScene(WindowSize windowSize) {
    var sceneRenderer = new TeamIntroRenderer(resourceManager, eventBus, windowSize);
    return new TeamIntroScene(sceneRenderer,
        resourceManager,
        eventBus,
        windowSize);
  }

  private Scene getMainMenuScene(WindowSize windowSize) {
    return new MainMenuScene(resourceManager,
        eventBus,
        windowSize);
  }

  private Scene getLobbyListScene(WindowSize windowSize) {
    return new LobbyListScene(new ParallaxBackground(resourceManager,
        windowSize,
        Type.PURPLE_MOUNTAINS), eventBus, resourceManager, windowSize);
  }

  @Override
  public void updateGameState(float interval) {
    networkManager.update();
    sceneTransitioner.getScene().updateState(interval);
  }

  @Override
  public void render() {
    sceneTransitioner.getScene().render(windowSize);
  }

  @Override
  public void cleanup() {
    sceneTransitioner.cleanup();

    if (resourceManager.hasAllocatedResources()) {
      var references = resourceManager.getReferenceCounter();
      log.warn("Resource leak(s) detected. " + references);
    } else {
      log.info("No resource leaks detected :)");
    }

    audioPlayer.cleanup();
    resourceManager.freeAll();
  }
}
