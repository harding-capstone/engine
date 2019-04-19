package com.shepherdjerred.capstone.engine.game.network.discovery.netty;

import com.shepherdjerred.capstone.engine.game.network.discovery.ServerInformation;
import com.shepherdjerred.capstone.engine.game.network.discovery.event.ServerDiscoveredEvent;
import com.shepherdjerred.capstone.engine.game.network.event.NetworkEvent;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
import com.shepherdjerred.capstone.network.packet.packets.ServerBroadcastPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class DiscoveryChannelInboundHandler extends ChannelInboundHandlerAdapter {

  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  @Override
  public void channelRead(ChannelHandlerContext context, Object message) {
    log.info(message);
    var packet = (Packet) message;
    if (packet instanceof ServerBroadcastPacket) {
      var serverBroadcastPacket = (ServerBroadcastPacket) packet;
      var remote = context.channel().remoteAddress();

      var serverInformation = new ServerInformation(remote, serverBroadcastPacket.getLobby());
      var serverDiscoveredEvent = new ServerDiscoveredEvent(serverInformation);

      eventQueue.add(serverDiscoveredEvent);
    }
  }
}
