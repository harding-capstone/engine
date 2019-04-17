package com.shepherdjerred.capstone.engine.game.netty;

import com.shepherdjerred.capstone.engine.game.netty.networkEvents.NetworkEvent;

public interface Connector {

  void acceptConnections();

  NetworkEvent getNextEvent();

  boolean hasEvent();
}
