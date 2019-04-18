package com.shepherdjerred.capstone.engine.game.network;

import com.shepherdjerred.capstone.engine.game.network.events.network.NetworkEvent;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Communicates between a client and a server.
 */
public class NetworkClient {

  private final EventBus<Event> eventBus;
  private ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  public NetworkClient(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.eventQueue = new ConcurrentLinkedQueue<>();
  }

  public void handleLatestEvent() {
    if (eventQueue.size() > 0) {
      var event = eventQueue.poll();
      eventBus.dispatch(event);
    }
  }
}
