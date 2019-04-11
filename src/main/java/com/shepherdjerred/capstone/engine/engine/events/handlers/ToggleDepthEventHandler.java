package com.shepherdjerred.capstone.engine.engine.events.handlers;

import static org.lwjgl.opengl.GL11.GL_DEPTH;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import com.shepherdjerred.capstone.engine.engine.events.ToggleDepthEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ToggleDepthEventHandler implements EventHandler<ToggleDepthEvent> {

  private boolean isDepthEnabled = true;

  @Override
  public void handle(ToggleDepthEvent toggleDepthEvent) {
    isDepthEnabled = !isDepthEnabled;
    if (isDepthEnabled) {
      glEnable(GL_DEPTH);
    } else {
      glDisable(GL_DEPTH);
    }
    log.info("Depth: " + isDepthEnabled);
  }
}