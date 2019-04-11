package com.shepherdjerred.capstone.engine.engine.events.handlers;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11C.GL_SRC_ALPHA;

import com.shepherdjerred.capstone.engine.engine.events.ToggleBlendingEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;

public class ToggleBlendingEventHandler implements EventHandler<ToggleBlendingEvent> {

  private boolean isBlendingEnabled = true;

  @Override
  public void handle(ToggleBlendingEvent toggleBlendingEvent) {
    isBlendingEnabled = !isBlendingEnabled;
    if (isBlendingEnabled) {
      glEnable(GL_BLEND);
      glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    } else {
      glDisable(GL_BLEND);
    }
  }
}
