package com.shepherdjerred.capstone.engine.game.netty;


import com.shepherdjerred.capstone.network.packet.packets.Packet;

public interface Connection {

  void sendPacket(Packet packet);

  void disconnect();

  Status getStatus();

  enum Status {
    CONNECTING, CONNECTED, DISCONNECTED, DISCONNECTED_BY_SERVER
  }
}
