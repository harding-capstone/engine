package com.shepherdjerred.capstone.engine.game.network.events;

import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.logic.turn.Turn;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class TurnEvent implements Event {
  private final Turn turn;
}