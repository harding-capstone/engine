package com.shepherdjerred.capstone.engine.game.network.client;

import com.shepherdjerred.capstone.engine.game.network.client.netty.NettyClientBootstrap;
import com.shepherdjerred.capstone.engine.game.network.client.state.PreLobbyState;
import com.shepherdjerred.capstone.engine.game.network.client.state.NetworkClientState;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
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
    clientState = new PreLobbyState(eventBus, this);
    clientState.enable();
  }

  public void sendPacket(Packet packet) {
    bootstrap.send(packet);
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
