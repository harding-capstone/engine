package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerLeaveEventHandler implements EventHandler<PlayerLeaveEvent> {
  private final CastleCastersGame game;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(PlayerLeaveEvent playerLeaveEvent) {
    if (playerLeaveEvent.getPlayer().getUuid() == game.getMyPlayer().getUuid()) {
      //TODO: end session
    } else {
      game.removePlayer(playerLeaveEvent.getPlayer());
    }
  }

}
