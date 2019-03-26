package com.shepherdjerred.capstone.engine.engine.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MouseDownEvent implements InputEvent {

  private final int x;
  private final int y;
}
