package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.events.GameOverEvent;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameOverEventHandler implements EventHandler<GameOverEvent> {
  private final CastleCastersGame game;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(GameOverEvent gameOverEvent) {
    //TODO: communicate win/loss then end session
  }

}
