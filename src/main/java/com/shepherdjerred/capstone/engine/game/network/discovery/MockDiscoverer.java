package com.shepherdjerred.capstone.engine.game.network.discovery;

import com.shepherdjerred.capstone.events.Event;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MockDiscoverer implements ServerDiscoverer {

  private final ConcurrentLinkedQueue<Event> eventQueue;

  @Override
  public void discoverServers() {
  }

  @Override
  public void stop() {

  }

  @Override
  public void run() {
    discoverServers();
  }

  @Override
  public Optional<Event> getEvent() {
    if (eventQueue.size() > 0) {
      return Optional.of(eventQueue.poll());
    } else {
      return Optional.empty();
    }
  }
}
