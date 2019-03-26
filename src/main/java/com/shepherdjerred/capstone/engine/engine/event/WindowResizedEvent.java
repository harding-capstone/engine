package com.shepherdjerred.capstone.engine.engine.event;

import com.shepherdjerred.capstone.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class WindowResizedEvent implements Event {

  private final int newWidth;
  private final int newHeight;
}
