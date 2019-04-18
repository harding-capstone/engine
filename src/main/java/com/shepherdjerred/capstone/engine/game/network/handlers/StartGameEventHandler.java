package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.network.NetworkClient;
import com.shepherdjerred.capstone.engine.game.network.events.StartGameEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartGameEventHandler implements EventHandler<StartGameEvent> {
  private final NetworkClient game;


  @Override
  public void handle(StartGameEvent startGameEvent) {
    game.setMatch(startGameEvent.getMatch());
  }

}
