package com.shepherdjerred.capstone.engine.game.network.client.state;

import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;

/**
 * Before the client connects to a server.
 */
public class DisconnectedClientState implements NetworkClientState {

  private final EventBus<Event> eventBus;
  private final EventHandlerFrame<Event> eventHandlerFrame;

  public DisconnectedClientState(EventBus<Event> eventBus) {
    this.eventBus =  eventBus;
    this.eventHandlerFrame = new EventHandlerFrame<>();
    createEventHandlerFrame();
  }

  private void createEventHandlerFrame() {

  }

  public void enable() {
    eventBus.registerHandlerFrame(eventHandlerFrame);
  }

  public void disable() {
    eventBus.registerHandlerFrame(eventHandlerFrame);
  }
}
