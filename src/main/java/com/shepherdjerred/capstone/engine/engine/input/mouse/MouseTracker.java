package com.shepherdjerred.capstone.engine.engine.input.mouse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MouseTracker {

  private boolean isInWindow;

  private MouseCoordinate mousePosition;
}
