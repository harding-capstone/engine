package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerJoinEventHandler implements EventHandler<PlayerJoinEvent> {
  private final CastleCastersGame game;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(PlayerJoinEvent playerJoinEvent) {
    game.addPlayer(playerJoinEvent.getPlayer());
  }

}
