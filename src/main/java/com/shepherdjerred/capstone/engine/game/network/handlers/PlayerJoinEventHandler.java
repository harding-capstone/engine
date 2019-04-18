package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.network.NetworkClient;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerJoinEvent;
import com.shepherdjerred.capstone.engine.game.network.exception.LobbyFullException;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerJoinEventHandler implements EventHandler<PlayerJoinEvent> {
  private final NetworkClient game;


  @Override
  public void handle(PlayerJoinEvent playerJoinEvent) {
    try {
      game.addPlayer(playerJoinEvent.getPlayer());
    } catch (LobbyFullException e) {
      e.printStackTrace();
    }
  }

}
