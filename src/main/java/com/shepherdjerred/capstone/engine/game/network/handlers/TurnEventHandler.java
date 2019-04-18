package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.GameClient;
import com.shepherdjerred.capstone.engine.game.network.events.TurnEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TurnEventHandler implements EventHandler<TurnEvent> {
  private final GameClient game;


  @Override
  public void handle(TurnEvent turnEvent) {
    game.makeTurn(turnEvent.getTurn());
  }

}
