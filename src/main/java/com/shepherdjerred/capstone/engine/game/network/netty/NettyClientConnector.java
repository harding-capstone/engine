package com.shepherdjerred.capstone.engine.game.network.netty;

import com.shepherdjerred.capstone.engine.game.network.Connector;
import com.shepherdjerred.capstone.engine.game.network.events.networkEvents.NetworkEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NettyClientConnector implements Connector {

  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;
  private final NettyClientSettings nettyClientSettings;

  public NettyClientConnector(NettyClientSettings nettyClientSettings) {
    this.eventQueue = new ConcurrentLinkedQueue<>();
    this.nettyClientSettings = nettyClientSettings;
  }

  @Override
  public void acceptConnections() {
    new Thread(() -> {
      EventLoopGroup group = new NioEventLoopGroup();

      try {
        Bootstrap clientBootstrap = new Bootstrap();

        clientBootstrap.group(group);
        clientBootstrap.channel(NioSocketChannel.class);
        clientBootstrap.remoteAddress(new InetSocketAddress(nettyClientSettings.getHostname(), nettyClientSettings.getPort()));

        clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
          protected void initChannel(SocketChannel socketChannel) {
            socketChannel.pipeline().addLast(new ClientChannelInitializer(eventQueue));
          }
        });

        ChannelFuture channelFuture = clientBootstrap.connect().sync();
        channelFuture.channel().closeFuture().sync();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          group.shutdownGracefully().sync();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();

    new Thread(() -> {
      while(true) {
        var nettyClientDiscovery = new NettyClientDiscovery();
        nettyClientDiscovery.receiveBroadcast(nettyClientSettings, eventQueue);
      }
    });
  }

  @Override
  public NetworkEvent getNextEvent() {
    return eventQueue.remove();
  }

  @Override
  public boolean hasEvent() {
    return !eventQueue.isEmpty();
  }
}
