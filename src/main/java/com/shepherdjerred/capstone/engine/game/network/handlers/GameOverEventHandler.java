package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.network.NetworkClient;
import com.shepherdjerred.capstone.engine.game.network.events.GameOverEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameOverEventHandler implements EventHandler<GameOverEvent> {
  private final NetworkClient game;


  @Override
  public void handle(GameOverEvent gameOverEvent) {
    //TODO: communicate win/loss then end session
  }

}
