package com.shepherdjerred.capstone.engine.game.network.netty;

import com.shepherdjerred.capstone.engine.game.network.Connection;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString(exclude = "handler")
@RequiredArgsConstructor
public class NettyConnection implements Connection {

  private final QueueingChannelHandler handler;
  @Getter
  private Status status = Status.CONNECTED;

  @Override
  public void sendPacket(Packet packet) {
    handler.send(packet);
  }

  @Override
  public void disconnect() {
    handler.disconnect();
    status = Status.DISCONNECTED_BY_SERVER;
  }
}
