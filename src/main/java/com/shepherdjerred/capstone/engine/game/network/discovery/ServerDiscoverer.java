package com.shepherdjerred.capstone.engine.game.network.discovery;

import com.shepherdjerred.capstone.events.Event;
import java.util.Optional;

public interface ServerDiscoverer extends Runnable {

  void discoverServers();

  Optional<Event> getEvent();
}
