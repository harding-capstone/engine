package com.shepherdjerred.capstone.engine.game.netty;

import com.shepherdjerred.capstone.engine.game.netty.networkEvents.NetworkEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NettyClientDiscovery {

  public void receiveBroadcast(NettyClientSettings nettyClientSettings, ConcurrentLinkedQueue<NetworkEvent> eventQueue) {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group)
          .channel(NioDatagramChannel.class)
          .option(ChannelOption.SO_BROADCAST, true)
          .handler(new ClientChannelInitializer(eventQueue));

      b.bind(nettyClientSettings.getPort()).sync().channel().closeFuture().await();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully();
    }
  }
}
