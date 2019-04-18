package com.shepherdjerred.capstone.engine.game.network.client;

import com.shepherdjerred.capstone.engine.game.network.client.netty.NettyClientBootstrap;
import com.shepherdjerred.capstone.engine.game.network.client.state.DisconnectedClientState;
import com.shepherdjerred.capstone.engine.game.network.client.state.NetworkClientState;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.net.SocketAddress;

/**
 * Hooks into an EventBus and sends/receives events over a network.
 */
public class NetworkClient {

  private NetworkClientState clientState;
  private final EventBus<Event> eventBus;
  private NettyClientBootstrap bootstrap;

  public NetworkClient(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.bootstrap = new NettyClientBootstrap();
    clientState = new DisconnectedClientState(eventBus);
  }

  public void connect(SocketAddress socketAddress) {
    bootstrap.connect(socketAddress);
  }

  public void shutdown() {
    bootstrap.cleanup();
  }

  public void update() {
    var event = bootstrap.getLatestEvent();
    event.ifPresent(eventBus::dispatch);
  }
}
