package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.network.NetworkClient;
import com.shepherdjerred.capstone.engine.game.network.events.networkEvents.ServerConnectedEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerConnectedEventHandler implements EventHandler<ServerConnectedEvent> {

  private final NetworkClient game;

  @Override
  public void handle(ServerConnectedEvent serverConnectedEvent) {
    game.setServerConnection(serverConnectedEvent.getConnection());
  }
}
