package com.shepherdjerred.capstone.engine.game.network.client.netty;

import com.shepherdjerred.capstone.engine.game.network.event.NetworkEvent;
import com.shepherdjerred.capstone.network.netty.PacketCodec;
import com.shepherdjerred.capstone.network.packet.serialization.PacketJsonSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyClientInitializer extends ChannelInitializer<NioSocketChannel> {

  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  @Override
  protected void initChannel(NioSocketChannel channel) throws Exception {
    var pipeline = channel.pipeline();
    var serializer = new PacketJsonSerializer();

    pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4));
    pipeline.addLast(new PacketCodec(serializer));
    pipeline.addLast(new NettyClientHandler(eventQueue));
    pipeline.addLast(new LoggingHandler());
  }
}
