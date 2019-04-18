package com.shepherdjerred.capstone.engine.game.network.events;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.events.Event;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class EditLobbyEvent implements Event {
  private final LobbySettings lobbySettings;
  private final Player player;
}
