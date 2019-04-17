package com.shepherdjerred.capstone.engine.game.netty.networkEvents;

import com.shepherdjerred.capstone.engine.game.netty.ClientId;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class PacketReceivedEvent implements NetworkEvent {

  private final ClientId clientId;
  private final Packet packet;
}

