package com.shepherdjerred.capstone.engine.game.network.events;


import com.shepherdjerred.capstone.common.lobby.Lobby;
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
public class PlayerCreatedEvent implements Event {
  private final Player player;
  private final Lobby lobby;
}
