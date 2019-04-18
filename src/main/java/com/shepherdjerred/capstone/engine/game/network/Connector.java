package com.shepherdjerred.capstone.engine.game.network;

import com.shepherdjerred.capstone.engine.game.network.events.networkEvents.NetworkEvent;

public interface Connector {

  void acceptConnections();

  NetworkEvent getNextEvent();

  boolean hasEvent();
}
