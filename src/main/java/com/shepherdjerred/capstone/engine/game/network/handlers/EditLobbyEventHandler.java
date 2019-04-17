package com.shepherdjerred.capstone.engine.game.network.handlers;

import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.game.GameClient;
import com.shepherdjerred.capstone.engine.game.network.events.EditLobbyEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditLobbyEventHandler implements EventHandler<EditLobbyEvent> {
  private final GameClient game;

  @Override
  public void handle(EditLobbyEvent editLobbyEvent) {
    game.updateLobby(editLobbyEvent.getLobbySettings());
  }
}
