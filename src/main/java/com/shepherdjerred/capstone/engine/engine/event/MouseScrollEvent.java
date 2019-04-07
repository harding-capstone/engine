package com.shepherdjerred.capstone.engine.engine.event;

import com.shepherdjerred.capstone.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MouseScrollEvent implements Event {

  private final int xScroll;
  private final int yScroll;
}
