package com.shepherdjerred.capstone.engine.engine.event.handler;

import com.shepherdjerred.capstone.engine.engine.event.MouseEnterEvent;
import com.shepherdjerred.capstone.engine.engine.input.mouse.MouseTracker;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MouseEnterEventHandler implements EventHandler<MouseEnterEvent> {

  private final MouseTracker mouseTracker;

  @Override
  public void handle(MouseEnterEvent mouseEnterEvent) {
    mouseTracker.setInWindow(true);
  }
}
