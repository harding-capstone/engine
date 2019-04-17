package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.events.EditLobbyEvent;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import com.shepherdjerred.capstone.network.packet.packets.EditLobbyPacket;
import com.shepherdjerred.capstone.server.GameServer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditLobbyEventHandler implements EventHandler<EditLobbyEvent> {
  private final CastleCastersGame game;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(EditLobbyEvent editLobbyEvent) {
    //TODO:what needs to be done when lobby is changed
  }
}
