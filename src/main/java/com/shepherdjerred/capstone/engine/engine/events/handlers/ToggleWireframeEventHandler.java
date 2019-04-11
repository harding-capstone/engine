package com.shepherdjerred.capstone.engine.engine.events.handlers;

import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import com.shepherdjerred.capstone.engine.engine.events.ToggleWireframeEvent;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
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
    log.info("Wireframe: " + isWireframe);
  }
}
