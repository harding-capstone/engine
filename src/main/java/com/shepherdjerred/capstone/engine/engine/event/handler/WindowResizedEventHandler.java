package com.shepherdjerred.capstone.engine.engine.event.handler;

import static org.lwjgl.opengl.GL11.glViewport;

import com.shepherdjerred.capstone.engine.engine.event.WindowResizedEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;

public class WindowResizedEventHandler implements EventHandler<WindowResizedEvent> {

  @Override
  public void handle(WindowResizedEvent windowResizedEvent) {
    var width = windowResizedEvent.getNewWidth();
    var height = windowResizedEvent.getNewHeight();
    glViewport(0, 0, width, height);
  }
}
