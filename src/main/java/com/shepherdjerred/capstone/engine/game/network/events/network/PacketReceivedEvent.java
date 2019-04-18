package com.shepherdjerred.capstone.engine.game.network.events.network;

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

  private final Packet packet;
}

