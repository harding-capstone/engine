package com.shepherdjerred.capstone.engine.game.network.client.state;

import com.shepherdjerred.capstone.engine.game.network.client.NetworkClient;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;

public class PostMatchState extends AbstractNetworkClientState {

  public PostMatchState(EventBus<Event> eventBus,
      NetworkClient networkClient) {
    super(eventBus, networkClient);
  }

  @Override
  protected EventHandlerFrame<Event> createEventHandlerFrame() {
    return null;
  }
}
