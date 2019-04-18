package com.shepherdjerred.capstone.engine.game.network.discovery.netty;


import com.shepherdjerred.capstone.common.Constants;
import com.shepherdjerred.capstone.engine.game.network.discovery.ServerDiscoverer;
import com.shepherdjerred.capstone.engine.game.network.events.network.NetworkEvent;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NettyServerDiscoverer implements ServerDiscoverer, Runnable {

  private final EventBus<Event> eventBus;
  private final ConcurrentLinkedQueue<NetworkEvent> eventQueue;

  public NettyServerDiscoverer(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.eventQueue = new ConcurrentLinkedQueue<>();
  }

  @Override
  public void discoverServers() {
    log.info("Discovering servers");
    new NettyDiscoveryBootstrap(new InetSocketAddress(Constants.DISCOVERY_PORT),
        eventQueue).run();
  }

  public void handleLatestEvent() {
    if (eventQueue.size() > 0) {
      var event = eventQueue.poll();
      eventBus.dispatch(event);
    }
  }

  @Override
  public void run() {
    final int ticksPerSecond = 10;
    final int millisecondsPerSecond = 1000;
    final int sleepMilliseconds = millisecondsPerSecond / ticksPerSecond;

    discoverServers();

    while (true) {
      handleLatestEvent();
      try {
        Thread.sleep(sleepMilliseconds);
      } catch (InterruptedException e) {
        log.error(e);
      }
    }
  }
}
