package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.GameClient;
import com.shepherdjerred.capstone.engine.game.network.events.PlayerChatEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerChatEventHandler implements EventHandler<PlayerChatEvent> {

  private final GameClient game;


  @Override
  public void handle(PlayerChatEvent playerChatEvent) {
    game.addChatMessage(playerChatEvent.getChatMessage());
  }
}
