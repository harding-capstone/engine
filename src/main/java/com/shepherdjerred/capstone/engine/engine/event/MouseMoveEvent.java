package com.shepherdjerred.capstone.engine.engine.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MouseMoveEvent implements InputEvent {

  private final double x;
  private final double y;
}
