package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.network.NetworkClient;
import com.shepherdjerred.capstone.engine.game.network.events.networkEvents.ServerDisconnectedEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServerDisconnectedEventHandler implements EventHandler<ServerDisconnectedEvent> {

  private final NetworkClient game;

  @Override
  public void handle(ServerDisconnectedEvent serverDisconnectedEvent) {
    //TODO: game over
  }
}
