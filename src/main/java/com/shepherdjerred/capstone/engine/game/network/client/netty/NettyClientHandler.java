package com.shepherdjerred.capstone.engine.game.network.client.netty;

import com.shepherdjerred.capstone.engine.game.network.event.NetworkEvent;
import com.shepherdjerred.capstone.engine.game.network.event.PacketReceivedEvent;
import com.shepherdjerred.capstone.engine.game.network.event.ServerConnectedEvent;
import com.shepherdjerred.capstone.engine.game.network.event.ServerDisconnectedEvent;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class NettyClientHandler extends ChannelDuplexHandler {

  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise)
      throws Exception {
    log.info("Source " + ctx.channel().localAddress());
    log.info("Destination " + ctx.channel().remoteAddress());
    super.write(ctx, msg, promise);
  }

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
    log.info("Received a packet: " + packet);
    eventQueue.add(new PacketReceivedEvent(packet));
  }
}
