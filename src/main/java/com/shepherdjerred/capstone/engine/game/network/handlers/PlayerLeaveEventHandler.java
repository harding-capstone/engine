package com.shepherdjerred.capstone.engine.game.network.handlers;


import com.shepherdjerred.capstone.engine.game.GameClient;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerLeaveEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerLeaveEventHandler implements EventHandler<PlayerLeaveEvent> {
  private final GameClient game;


  @Override
  public void handle(PlayerLeaveEvent playerLeaveEvent) {
    if (playerLeaveEvent.getPlayer().getUuid() == game.getMyPlayer().getUuid()) {
      //TODO: end session
    } else {
      game.removePlayer(playerLeaveEvent.getPlayer());
    }
  }

}
