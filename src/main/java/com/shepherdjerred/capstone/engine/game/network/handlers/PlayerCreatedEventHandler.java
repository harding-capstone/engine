package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.common.Game;
import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.GameClient;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerCreatedEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerCreatedEventHandler implements EventHandler<PlayerCreatedEvent> {
  private final GameClient game;


  @Override
  public void handle(PlayerCreatedEvent playerCreatedEvent) {
    game.setMyPlayer(playerCreatedEvent.getPlayer());
    game.setLobby(playerCreatedEvent.getLobby());
  }

}
