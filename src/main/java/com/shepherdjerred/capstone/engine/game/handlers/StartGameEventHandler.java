package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.events.StartGameEvent;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.StartGamePacket;
import com.shepherdjerred.capstone.server.GameServer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartGameEventHandler implements EventHandler<StartGameEvent> {
  private final CastleCastersGame game;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(StartGameEvent startGameEvent) {
    game.setMatch(startGameEvent.getMatch());
  }

}
