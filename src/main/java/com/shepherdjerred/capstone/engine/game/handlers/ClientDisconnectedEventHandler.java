package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.engine.game.netty.networkEvents.ClientDisconnectedEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientDisconnectedEventHandler implements EventHandler<ClientDisconnectedEvent> {

  private final CastleCastersGame game;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(ClientDisconnectedEvent clientDisconnectedEvent) {
    //TODO: game over
  }
}
