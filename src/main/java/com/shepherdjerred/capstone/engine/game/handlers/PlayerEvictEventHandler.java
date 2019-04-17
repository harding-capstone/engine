package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerEvictEventHandler implements EventHandler<PlayerEvictEvent> {
  private final CastleCastersGame game;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(PlayerEvictEvent playerEvictEvent) {
    if (playerEvictEvent.getPlayer().getUuid() == game.getMyPlayer().getUuid()) {
      //TODO: end session
    } else {
      game.removePlayer(playerEvictEvent.getPlayer());
    }
  }

}
