package com.shepherdjerred.capstone.engine.game.network.discovery;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import com.shepherdjerred.capstone.engine.game.network.discovery.event.ServerDiscoveredEvent;
import com.shepherdjerred.capstone.events.Event;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MockDiscoverer implements ServerDiscoverer {

  private final ConcurrentLinkedQueue<Event> eventQueue;

  @Override
  public void discoverServers() {
    eventQueue.add(new ServerDiscoveredEvent(new ServerInformation(new InetSocketAddress(9999),
        new LobbySettings("Play Quoridor", null, null, null, false))));
    eventQueue.add(new ServerDiscoveredEvent(new ServerInformation(new InetSocketAddress(9999),
        new LobbySettings("Hey there!", null, null, null, false))));
    eventQueue.add(new ServerDiscoveredEvent(new ServerInformation(new InetSocketAddress(9999),
        new LobbySettings("Some Lobby", null, null, null, false))));
    eventQueue.add(new ServerDiscoveredEvent(new ServerInformation(new InetSocketAddress(9999),
        new LobbySettings("Lorem Ipsum", null, null, null, false))));
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
