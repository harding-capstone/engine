package com.shepherdjerred.capstone.engine.engine.event.handler;

import com.shepherdjerred.capstone.engine.engine.event.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.engine.input.mouse.MouseTracker;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MouseMoveEventHandler implements EventHandler<MouseMoveEvent> {

  private final MouseTracker mouseTracker;

  @Override
  public void handle(MouseMoveEvent mouseMoveEvent) {
    mouseTracker.setMousePosition(mouseMoveEvent.getNewMousePosition());
  }
}
