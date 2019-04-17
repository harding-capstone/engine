package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.events.HostLeaveEvent;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HostLeaveEventHandler implements EventHandler<HostLeaveEvent> {
  private final CastleCastersGame game;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(HostLeaveEvent hostLeaveEvent) {
      //TODO: end session
  }

}
