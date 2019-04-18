package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.GameClient;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerEvictEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerEvictEventHandler implements EventHandler<PlayerEvictEvent> {
  private final GameClient game;


  @Override
  public void handle(PlayerEvictEvent playerEvictEvent) {
    if (playerEvictEvent.getPlayer().getUuid() == game.getMyPlayer().getUuid()) {
      //TODO: end session
    } else {
      game.removePlayer(playerEvictEvent.getPlayer());
    }
  }

}
