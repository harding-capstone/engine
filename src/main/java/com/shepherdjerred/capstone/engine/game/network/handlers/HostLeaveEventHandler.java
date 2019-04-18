package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.GameClient;
import com.shepherdjerred.capstone.engine.game.network.events.HostLeaveEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HostLeaveEventHandler implements EventHandler<HostLeaveEvent> {
  private final GameClient game;


  @Override
  public void handle(HostLeaveEvent hostLeaveEvent) {
      //TODO: end session
  }

}
