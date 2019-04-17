package com.shepherdjerred.capstone.engine.game.netty;

import com.shepherdjerred.capstone.engine.game.netty.networkEvents.ClientConnectedEvent;
import com.shepherdjerred.capstone.engine.game.netty.networkEvents.ClientDisconnectedEvent;
import com.shepherdjerred.capstone.engine.game.netty.networkEvents.NetworkEvent;
import com.shepherdjerred.capstone.engine.game.netty.networkEvents.PacketReceivedEvent;
import com.shepherdjerred.capstone.network.packet.packets.Packet;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Forwards channel events as events in a queue.
 */
@Log4j2
@RequiredArgsConstructor
public class QueueingChannelHandler extends ChannelInboundHandlerAdapter {

  private ChannelHandlerContext context;
  private final ClientId clientId;
  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  @Override
  public void channelActive(ChannelHandlerContext context) {
    this.context = context;
    eventQueue.add(new ClientConnectedEvent(clientId, new NettyConnection(this)));
  }

  @Override
  public void channelRead(ChannelHandlerContext context, Object message) {
    var packet = (Packet) message;
    eventQueue.add(new PacketReceivedEvent(clientId, packet));
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext context) {
    context.writeAndFlush(Unpooled.EMPTY_BUFFER)
        .addListener(ChannelFutureListener.CLOSE);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) {
    eventQueue.add(new ClientDisconnectedEvent(clientId));
  }

  public void send(Object object) {
    context.channel().writeAndFlush(object);
  }

  public void disconnect() {
    context.disconnect();
  }
}
