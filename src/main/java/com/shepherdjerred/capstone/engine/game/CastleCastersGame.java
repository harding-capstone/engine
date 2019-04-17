package com.shepherdjerred.capstone.engine.game;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.shepherdjerred.capstone.common.chat.ChatHistory;
import com.shepherdjerred.capstone.common.chat.ChatMessage;
import com.shepherdjerred.capstone.common.player.Player;
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
import com.shepherdjerred.capstone.engine.game.network.Connection;
import com.shepherdjerred.capstone.engine.game.network.events.EditLobbyEvent;
import com.shepherdjerred.capstone.engine.game.network.events.HostLeaveEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerChatEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.engine.game.network.events.StartGameEvent;
import com.shepherdjerred.capstone.engine.game.network.events.TurnEvent;
import com.shepherdjerred.capstone.engine.game.network.handlers.EditLobbyEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.HostLeaveEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.PacketReceivedEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.PlayerChatEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.PlayerEvictEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.PlayerJoinEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.PlayerLeaveEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.StartGameEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.TurnEventHandler;
import com.shepherdjerred.capstone.engine.game.network.events.networkEvents.ServerDisconnectedEvent;
import com.shepherdjerred.capstone.engine.game.network.events.networkEvents.PacketReceivedEvent;
import com.shepherdjerred.capstone.engine.game.network.handlers.ServerDisconnectedEventHandler;
import com.shepherdjerred.capstone.engine.game.scenes.game.GameRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.game.GameScene;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuAudio;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuScene;
import com.shepherdjerred.capstone.engine.game.scenes.teamintro.TeamIntroRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.teamintro.TeamIntroScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.events.handlers.EventLoggerHandler;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CastleCastersGame implements GameLogic {

  private final EventBus<Event> eventBus;
  @Getter
  @Setter
  private Connection serverConnection;
  private final BiMap<UUID, Player> handlePlayerMap; //lobby
  private final ResourceManager resourceManager;
  private final SceneTransitioner sceneTransitioner;
  private final AudioPlayer audioPlayer;
  private WindowSize windowSize;

  public CastleCastersGame(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.handlePlayerMap = HashBiMap.create();
    this.resourceManager = new ResourceManager();
    this.audioPlayer = new AudioPlayer(eventBus);
    this.sceneTransitioner = new SceneTransitioner(eventBus);
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

    var scene = getGameScene(windowSize);

    sceneTransitioner.initialize(scene);

    eventBus.registerHandler(SceneTransitionEvent.class,
        new SceneTransitionEventHandler(sceneTransitioner));

    audioPlayer.initialize();
  }

  private Scene getGameScene(WindowSize windowSize) {
    var sceneRenderer = new GameRenderer(resourceManager, eventBus, windowSize);
    return new GameScene(resourceManager, eventBus, sceneRenderer, GameMapName.GRASS, windowSize);
  }

  private Scene getTeamScene(WindowSize windowSize) {
    var sceneRenderer = new TeamIntroRenderer(resourceManager, eventBus, windowSize);
    return new TeamIntroScene(sceneRenderer,
        resourceManager,
        eventBus,
        windowSize);
  }

  private Scene getMainMenuScene(WindowSize windowSize) {
    var sceneRenderer = new MainMenuRenderer(resourceManager, eventBus, windowSize);
    return new MainMenuScene(sceneRenderer,
        resourceManager,
        eventBus,
        windowSize,
        new MainMenuAudio(eventBus, resourceManager));
  }

  @Override
  public void updateGameState(float interval) {
    sceneTransitioner.getScene().updateState(interval);
  }

  @Override
  public void render() {
    // TODO handle resizes
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
