package com.shepherdjerred.capstone.engine.engine.event.handler;

import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import com.shepherdjerred.capstone.engine.engine.event.ToggleWireframeEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;

public class ToggleWireframeEventHandler implements EventHandler<ToggleWireframeEvent> {

  private boolean isWireframe = false;

  @Override
  public void handle(ToggleWireframeEvent toggleWireframeEvent) {
    isWireframe = !isWireframe;
    if (isWireframe) {
      glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    } else {
      glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }
  }
}
