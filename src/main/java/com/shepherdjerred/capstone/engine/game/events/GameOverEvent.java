package com.shepherdjerred.capstone.engine.game.events;

import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.logic.match.MatchStatus.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class GameOverEvent implements Event {
  private final Status status;
}
