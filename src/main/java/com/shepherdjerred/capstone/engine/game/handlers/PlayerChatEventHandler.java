package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.events.PlayerChatEvent;
import com.shepherdjerred.capstone.engine.game.netty.ConnectorHub;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerChatEventHandler implements EventHandler<PlayerChatEvent> {

  private final CastleCastersGame game;
  private final ConnectorHub connectorHub;

  @Override
  public void handle(PlayerChatEvent playerChatEvent) {
    game.addChatMessage(playerChatEvent.getChatMessage());
  }
}
