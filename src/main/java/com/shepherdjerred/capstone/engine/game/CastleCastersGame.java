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
import com.shepherdjerred.capstone.engine.game.events.EditLobbyEvent;
import com.shepherdjerred.capstone.engine.game.events.HostLeaveEvent;
import com.shepherdjerred.capstone.engine.game.events.PlayerChatEvent;
import com.shepherdjerred.capstone.engine.game.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.engine.game.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.engine.game.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.engine.game.events.StartGameEvent;
import com.shepherdjerred.capstone.engine.game.events.TurnEvent;
import com.shepherdjerred.capstone.engine.game.handlers.EditLobbyEventHandler;
import com.shepherdjerred.capstone.engine.game.handlers.HostLeaveEventHandler;
import com.shepherdjerred.capstone.engine.game.handlers.PacketReceivedEventHandler;
import com.shepherdjerred.capstone.engine.game.handlers.PlayerChatEventHandler;
import com.shepherdjerred.capstone.engine.game.handlers.PlayerEvictEventHandler;
import com.shepherdjerred.capstone.engine.game.handlers.PlayerJoinEventHandler;
import com.shepherdjerred.capstone.engine.game.handlers.PlayerLeaveEventHandler;
import com.shepherdjerred.capstone.engine.game.handlers.StartGameEventHandler;
import com.shepherdjerred.capstone.engine.game.handlers.TurnEventHandler;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.engine.game.netty.networkEvents.ClientDisconnectedEvent;
import com.shepherdjerred.capstone.engine.game.netty.networkEvents.PacketReceivedEvent;
import com.shepherdjerred.capstone.engine.game.handlers.ClientDisconnectedEventHandler;
import com.shepherdjerred.capstone.engine.game.scenes.game.GameRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.game.GameScene;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuAudio;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.mainmenu.MainMenuScene;
import com.shepherdjerred.capstone.engine.game.scenes.teamintro.TeamIntroRenderer;
import com.shepherdjerred.capstone.engine.game.scenes.teamintro.TeamIntroScene;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
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
  private final ConnectorHub connectorHub;
  private final BiMap<UUID, Player> handlePlayerMap;
  private final ResourceManager resourceManager;
  private final SceneTransitioner sceneTransitioner;
  private final AudioPlayer audioPlayer;
  @Getter
  @Setter
  private Player myPlayer;
  private WindowSize windowSize;
  @Getter
  @Setter
  private Match match;
  @Getter
  private ChatHistory chatHistory;

  public CastleCastersGame(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.connectorHub = new ConnectorHub(eventBus);
    this.handlePlayerMap = HashBiMap.create();
    this.resourceManager = new ResourceManager();
    this.audioPlayer = new AudioPlayer(eventBus);
    this.sceneTransitioner = new SceneTransitioner(eventBus);
    this.chatHistory = new ChatHistory();
    registerLoaders();
    registerNetworkEventHandlers();
    registerEventHandlers();
  }

  public Player getPlayerByClientId(UUID clientId) {
    return handlePlayerMap.get(clientId);
  }

  public void makeTurn(Turn turn) {
    this.match.doTurn(turn);
  }

  public void addPlayer(Player player) {
    handlePlayerMap.put(player.getUuid(), player);
  }

  public void removePlayer(Player player) {
    handlePlayerMap.remove(player.getUuid(), player);
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

  public void addChatMessage(ChatMessage message) {
    chatHistory = chatHistory.addMessage(message);
  }

  private void registerNetworkEventHandlers() {
    eventBus.registerHandler(new EventLoggerHandler<>());
    eventBus.registerHandler(ClientDisconnectedEvent.class,
        new ClientDisconnectedEventHandler(this, connectorHub));
    eventBus.registerHandler(PacketReceivedEvent.class,
        new PacketReceivedEventHandler(this));
  }

  private void registerEventHandlers() {
    eventBus.registerHandler(PacketReceivedEvent.class,
        new PacketReceivedEventHandler(this));
    eventBus.registerHandler(PlayerChatEvent.class,
        new PlayerChatEventHandler(this, connectorHub));
    eventBus.registerHandler(EditLobbyEvent.class,
        new EditLobbyEventHandler(this, connectorHub));
    eventBus.registerHandler(HostLeaveEvent.class,
        new HostLeaveEventHandler(this, connectorHub));
    eventBus.registerHandler(PlayerEvictEvent.class,
        new PlayerEvictEventHandler(this, connectorHub));
    eventBus.registerHandler(PlayerJoinEvent.class,
        new PlayerJoinEventHandler(this, connectorHub));
    eventBus.registerHandler(PlayerLeaveEvent.class,
        new PlayerLeaveEventHandler(this, connectorHub));
    eventBus.registerHandler(StartGameEvent.class,
        new StartGameEventHandler(this, connectorHub));
    eventBus.registerHandler(TurnEvent.class,
        new TurnEventHandler(this, connectorHub));
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

  public void dispatch(Event event) {
    eventBus.dispatch(event);
  }
}
