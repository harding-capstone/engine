package com.shepherdjerred.capstone.engine.game.event.events;

import com.shepherdjerred.capstone.common.player.Player;
import com.shepherdjerred.capstone.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class PlayerJoinEvent implements Event {
  private final Player player;
}
