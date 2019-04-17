package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.events.StartGameEvent;
import com.shepherdjerred.capstone.engine.game.events.TurnEvent;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TurnEventHandler implements EventHandler<TurnEvent> {
  private final CastleCastersGame game;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(TurnEvent turnEvent) {
    game.makeTurn(turnEvent.getTurn());
  }

}
