package com.shepherdjerred.capstone.engine.game.handlers;

import com.shepherdjerred.capstone.engine.engine.events.input.MouseButtonUpEvent;
import com.shepherdjerred.capstone.engine.engine.scene.Scene;
import com.shepherdjerred.capstone.engine.engine.scene.attributes.Clickable;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MouseUpClickableHandler implements EventHandler<MouseButtonUpEvent> {

  private final Scene scene;

  @Override
  public void handle(MouseButtonUpEvent mouseButtonUpEvent) {
    scene.getGameObjects().forEach(element -> {
      if (element instanceof Clickable) {
        if (((Clickable) element).isChecked()) {
          ((Clickable) element).onClickEnd();
        }
      }
    });
  }
}
