package com.shepherdjerred.capstone.engine.game.network.client.state;

import com.shepherdjerred.capstone.engine.game.network.client.NetworkClient;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;

public abstract class AbstractNetworkClientState implements NetworkClientState {

  protected final NetworkClient networkClient;
  protected final EventBus<Event> eventBus;
  private final EventHandlerFrame<Event> eventHandlerFrame;

  public AbstractNetworkClientState(EventBus<Event> eventBus, NetworkClient networkClient) {
    this.eventBus = eventBus;
    this.eventHandlerFrame = createEventHandlerFrame();
    this.networkClient = networkClient;
  }

  protected abstract EventHandlerFrame<Event> createEventHandlerFrame();

  @Override
  public void enable() {
    eventBus.registerHandlerFrame(eventHandlerFrame);
  }

  @Override
  public void disable() {
    eventBus.removeHandlerFrame(eventHandlerFrame);
  }
}
