package com.shepherdjerred.capstone.engine.game.network;

import com.shepherdjerred.capstone.common.chat.ChatHistory;
import com.shepherdjerred.capstone.common.chat.ChatMessage;
import com.shepherdjerred.capstone.common.lobby.Lobby;
import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.engine.game.network.events.EditLobbyEvent;
import com.shepherdjerred.capstone.engine.game.network.events.HostLeaveEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerChatEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.engine.game.network.events.StartGameEvent;
import com.shepherdjerred.capstone.engine.game.network.events.TurnEvent;
import com.shepherdjerred.capstone.engine.game.network.events.networkEvents.NetworkEvent;
import com.shepherdjerred.capstone.engine.game.network.events.networkEvents.PacketReceivedEvent;
import com.shepherdjerred.capstone.engine.game.network.events.networkEvents.ServerDisconnectedEvent;
import com.shepherdjerred.capstone.engine.game.network.exception.LobbyFullException;
import com.shepherdjerred.capstone.engine.game.network.handlers.EditLobbyEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.HostLeaveEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.PacketReceivedEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.PlayerChatEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.PlayerEvictEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.PlayerJoinEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.PlayerLeaveEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.ServerDisconnectedEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.StartGameEventHandler;
import com.shepherdjerred.capstone.engine.game.network.handlers.TurnEventHandler;
import com.shepherdjerred.capstone.engine.game.network.netty.NettyClientSettings;
import com.shepherdjerred.capstone.engine.game.network.netty.NettyServerConnector;
import com.shepherdjerred.capstone.engine.game.network.netty.NettyServerDiscovery;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.Getter;
import lombok.Setter;

public class NetworkClient {

  private final EventBus<Event> eventBus;
  @Getter
  @Setter
  private Connection serverConnection;
  @Getter
  @Setter
  private Lobby lobby;
  @Getter
  @Setter
  private Player myPlayer;
  @Getter
  @Setter
  private Match match;
  @Getter
  private ChatHistory chatHistory;
  private EventHandlerFrame<Event> networkHandlerFrame;
  private EventHandlerFrame<Event> eventHandlerFrame;
  private ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  public NetworkClient(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.eventQueue = new ConcurrentLinkedQueue<>();
    registerEventHandlers();
    registerNetworkEventHandlers();
  }

  public void discover() {
    new NettyServerDiscovery();
  }

  public void connect(NettyClientSettings nettyClientSettings) {
    var server = new NettyServerConnector(nettyClientSettings);
  }

  public void handleLatestEvent() {
    if (eventQueue.size() > 0) {
      var event = eventQueue.poll();
      eventBus.dispatch(event);
    }
  }

  public void makeTurn(Turn turn) {
    this.match.doTurn(turn);
  }

  public void addPlayer(Player player) throws LobbyFullException {
    if (!lobby.isFull()) {
      lobby.addPlayer(player);
    } else {
      throw new LobbyFullException();
    }
  }

  public void removePlayer(Player player) {
    lobby.removePlayer(player);
  }

  public void updateLobby(LobbySettings lobbySettings) {
    lobby = lobby.UpdateLobbySettings(lobbySettings);
  }

  public void addChatMessage(ChatMessage message) {
    chatHistory = chatHistory.addMessage(message);
  }

  private void registerNetworkEventHandlers() {
    networkHandlerFrame = new EventHandlerFrame<>();
    networkHandlerFrame.registerHandler(ServerDisconnectedEvent.class,
        new ServerDisconnectedEventHandler(this));
    eventBus.registerHandlerFrame(networkHandlerFrame);
  }

  private void registerEventHandlers() {
    eventHandlerFrame = new EventHandlerFrame<>();
    eventHandlerFrame.registerHandler(PacketReceivedEvent.class,
        new PacketReceivedEventHandler(this));
    eventHandlerFrame.registerHandler(PlayerChatEvent.class,
        new PlayerChatEventHandler(this));
    eventHandlerFrame.registerHandler(EditLobbyEvent.class,
        new EditLobbyEventHandler(this));
    eventHandlerFrame.registerHandler(HostLeaveEvent.class,
        new HostLeaveEventHandler(this));
    eventHandlerFrame.registerHandler(PlayerEvictEvent.class,
        new PlayerEvictEventHandler(this));
    eventHandlerFrame.registerHandler(PlayerJoinEvent.class,
        new PlayerJoinEventHandler(this));
    eventHandlerFrame.registerHandler(PlayerLeaveEvent.class,
        new PlayerLeaveEventHandler(this));
    eventHandlerFrame.registerHandler(StartGameEvent.class,
        new StartGameEventHandler(this));
    eventHandlerFrame.registerHandler(TurnEvent.class,
        new TurnEventHandler(this));

    eventBus.registerHandlerFrame(eventHandlerFrame);
  }

  public void dispatch(Event event) {
    eventBus.dispatch(event);
  }

  public void cleanup() {
    eventBus.removeHandlerFrame(networkHandlerFrame);
    eventBus.removeHandlerFrame(eventHandlerFrame);
  }
}
