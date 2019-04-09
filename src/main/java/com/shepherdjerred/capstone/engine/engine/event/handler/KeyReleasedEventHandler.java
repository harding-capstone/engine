package com.shepherdjerred.capstone.engine.engine.event.handler;

import static com.shepherdjerred.capstone.engine.engine.input.keyboard.Key.B;
import static com.shepherdjerred.capstone.engine.engine.input.keyboard.Key.W;

import com.shepherdjerred.capstone.engine.engine.event.KeyReleasedEvent;
import com.shepherdjerred.capstone.engine.engine.event.ToggleBlendingEvent;
import com.shepherdjerred.capstone.engine.engine.event.ToggleWireframeEvent;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class KeyReleasedEventHandler implements EventHandler<KeyReleasedEvent> {

  private final EventBus<Event> eventBus;

  @Override
  public void handle(KeyReleasedEvent keyReleasedEvent) {
    var key = keyReleasedEvent.getKey();
    if (key == W) {
      eventBus.dispatch(new ToggleWireframeEvent());
    }
    if (key == B) {
      eventBus.dispatch(new ToggleBlendingEvent());
    }
  }
}
