package com.shepherdjerred.capstone.engine.game.network.client.netty;

import com.shepherdjerred.capstone.engine.game.network.event.NetworkEvent;
import com.shepherdjerred.capstone.engine.game.network.event.PacketReceivedEvent;
import com.shepherdjerred.capstone.engine.game.network.event.ServerConnectedEvent;
import com.shepherdjerred.capstone.engine.game.network.event.ServerDisconnectedEvent;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
    eventQueue.add(new ServerConnectedEvent());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    super.channelInactive(ctx);
    eventQueue.add(new ServerDisconnectedEvent());
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    super.channelRead(ctx, msg);
    var packet = (Packet) msg;
    eventQueue.add(new PacketReceivedEvent(packet));
  }
}
