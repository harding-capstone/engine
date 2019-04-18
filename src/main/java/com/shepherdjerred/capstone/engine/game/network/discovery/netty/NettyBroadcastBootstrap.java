package com.shepherdjerred.capstone.engine.game.network.discovery.netty;

import com.shepherdjerred.capstone.engine.game.network.discovery.NetworkConnectionData;
import com.shepherdjerred.capstone.engine.game.network.events.network.NetworkEvent;
import com.shepherdjerred.capstone.engine.game.network.netty.ClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
public class NettyBroadcastBootstrap implements Runnable {

  private final NetworkConnectionData networkConnectionData;
  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  @Override
  public void run() {
    EventLoopGroup group = new NioEventLoopGroup();

    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(group)
          .channel(NioDatagramChannel.class)
          .option(ChannelOption.SO_BROADCAST, true)
          .handler(new ClientChannelInitializer(eventQueue));

      bootstrap.bind(networkConnectionData.getHostname(), networkConnectionData.getPort())
          .sync()
          .channel()
          .closeFuture()
          .await();
    } catch (Exception e) {
      log.error(e);
    } finally {
      group.shutdownGracefully();
    }
  }
}
