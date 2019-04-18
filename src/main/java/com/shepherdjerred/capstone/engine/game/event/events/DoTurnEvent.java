package com.shepherdjerred.capstone.engine.game.event.events;

import com.shepherdjerred.capstone.logic.turn.Turn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class DoTurnEvent {

  private final Turn turn;
}
