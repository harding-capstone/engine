package com.shepherdjerred.capstone.engine.engine.event.handler;

import com.shepherdjerred.capstone.engine.engine.event.MouseLeaveEvent;
import com.shepherdjerred.capstone.engine.engine.input.mouse.MouseTracker;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MouseLeaveEventHandler implements EventHandler<MouseLeaveEvent> {

  private final MouseTracker mouseTracker;

  @Override
  public void handle(MouseLeaveEvent mouseLeaveEvent) {
    mouseTracker.setInWindow(false);
  }
}
