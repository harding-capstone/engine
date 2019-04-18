package com.shepherdjerred.capstone.engine.game.network.discovery.netty;

import com.shepherdjerred.capstone.engine.game.network.discovery.NetworkConnectionData;
import com.shepherdjerred.capstone.engine.game.network.discovery.ServerInformation;
import com.shepherdjerred.capstone.engine.game.network.discovery.event.ServerDiscoveredEvent;
import com.shepherdjerred.capstone.engine.game.network.events.network.NetworkEvent;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
import com.shepherdjerred.capstone.network.packet.packets.ServerBroadcastPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BroadcastChannelInboundHandler extends ChannelInboundHandlerAdapter {

  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  @Override
  public void channelRead(ChannelHandlerContext context, Object message) {
    var packet = (Packet) message;
    if (packet instanceof ServerBroadcastPacket) {
      var serverBroadcastPacket = (ServerBroadcastPacket) packet;
      var serverInformation = new ServerInformation(new NetworkConnectionData(null,
          0), serverBroadcastPacket.lobbySettings);
      var serverDiscoveredEvent = new ServerDiscoveredEvent(serverInformation);

      eventQueue.add(serverDiscoveredEvent);
    }
  }
}
