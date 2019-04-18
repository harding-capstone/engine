package com.shepherdjerred.capstone.engine.game;

import com.google.common.collect.BiMap;
import com.shepherdjerred.capstone.common.chat.ChatHistory;
import com.shepherdjerred.capstone.common.chat.ChatMessage;
import com.shepherdjerred.capstone.common.lobby.Lobby;
import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.engine.game.network.Connection;
import com.shepherdjerred.capstone.engine.game.network.events.EditLobbyEvent;
import com.shepherdjerred.capstone.engine.game.network.events.HostLeaveEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerChatEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.engine.game.network.events.StartGameEvent;
import com.shepherdjerred.capstone.engine.game.network.events.TurnEvent;
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
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.logic.match.Match;
import com.shepherdjerred.capstone.logic.turn.Turn;
import lombok.Getter;
import lombok.Setter;

public class GameClient {
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

  public GameClient(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    registerEventHandlers();
    registerNetworkEventHandlers();
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
    var frame = new EventHandlerFrame<Event>();
    frame.registerHandler(ServerDisconnectedEvent.class,
        new ServerDisconnectedEventHandler(this));
    eventBus.registerHandlerFrame(frame);
  }

  private void registerEventHandlers() {
    var frame = new EventHandlerFrame<Event>();
    frame.registerHandler(PacketReceivedEvent.class,
        new PacketReceivedEventHandler(this));
    frame.registerHandler(PlayerChatEvent.class,
        new PlayerChatEventHandler(this));
    frame.registerHandler(EditLobbyEvent.class,
        new EditLobbyEventHandler(this));
    frame.registerHandler(HostLeaveEvent.class,
        new HostLeaveEventHandler(this));
    frame.registerHandler(PlayerEvictEvent.class,
        new PlayerEvictEventHandler(this));
    frame.registerHandler(PlayerJoinEvent.class,
        new PlayerJoinEventHandler(this));
    frame.registerHandler(PlayerLeaveEvent.class,
        new PlayerLeaveEventHandler(this));
    frame.registerHandler(StartGameEvent.class,
        new StartGameEventHandler(this));
    frame.registerHandler(TurnEvent.class,
        new TurnEventHandler(this));

    eventBus.registerHandlerFrame(frame);
  }

  public void dispatch(Event event) {
    eventBus.dispatch(event);
  }

}
