package com.shepherdjerred.capstone.engine.game.network.discovery.netty;

import com.shepherdjerred.capstone.engine.game.network.events.network.NetworkEvent;
import com.shepherdjerred.capstone.network.netty.PacketCodec;
import com.shepherdjerred.capstone.network.packet.serialization.PacketJsonSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.DatagramChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DiscoveryChannelInitializer extends ChannelInitializer<DatagramChannel> {

  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  @Override
  protected void initChannel(DatagramChannel datagramChannel) {
    var pipeline = datagramChannel.pipeline();
    var serializer = new PacketJsonSerializer();

    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
    pipeline.addLast(new PacketCodec(serializer));
    pipeline.addLast(new DiscoveryChannelInboundHandler(eventQueue));
    pipeline.addLast(new LoggingHandler());
  }
}
