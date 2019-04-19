package com.shepherdjerred.capstone.engine.game.network.manager;

import com.shepherdjerred.capstone.common.Constants;
import com.shepherdjerred.capstone.engine.game.network.client.GameClient;
import com.shepherdjerred.capstone.engine.game.network.discovery.ServerDiscoverer;
import com.shepherdjerred.capstone.engine.game.network.discovery.netty.NettyServerDiscoverer;
import com.shepherdjerred.capstone.engine.game.network.manager.event.ConnectServerEvent;
import com.shepherdjerred.capstone.engine.game.network.manager.event.StartClientEvent;
import com.shepherdjerred.capstone.engine.game.network.manager.event.StartDiscoveryEvent;
import com.shepherdjerred.capstone.engine.game.network.manager.event.StartServerEvent;
import com.shepherdjerred.capstone.engine.game.network.manager.event.StopClientEvent;
import com.shepherdjerred.capstone.engine.game.network.manager.event.StopDiscoveryEvent;
import com.shepherdjerred.capstone.engine.game.network.manager.event.StopServerEvent;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventHandlerFrame;
import com.shepherdjerred.capstone.server.GameServer;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class NetworkManager {

  private final EventBus<Event> eventBus;
  private final EventHandlerFrame<Event> eventHandlerFrame;
  private GameServer gameServer = null;
  private GameClient gameClient = null;
  private ServerDiscoverer discoverer = null;
  private Thread serverThread;
  private Thread clientThread;
  private Thread discoveryThread;

  public NetworkManager(EventBus<Event> eventBus) {
    this.eventBus = eventBus;
    this.eventHandlerFrame = new EventHandlerFrame<>();
    initializeEventHandlerFrame();
  }

  private void initializeEventHandlerFrame() {
    eventHandlerFrame.registerHandler(StartClientEvent.class, (event) -> {
      createClient();
    });
    eventHandlerFrame.registerHandler(StartServerEvent.class, (event) -> {
      createServer();
    });
    eventHandlerFrame.registerHandler(StopClientEvent.class, (event) -> {
      gameClient.shutdown();
      clientThread.stop();
    });
    eventHandlerFrame.registerHandler(StopServerEvent.class, (event) -> {
      serverThread.stop();
    });
    eventHandlerFrame.registerHandler(ConnectServerEvent.class, (event) -> {
      connectClient(event.getAddress());
    });
    eventHandlerFrame.registerHandler(StartDiscoveryEvent.class, (event) -> {
      startDiscovery();
    });
    eventHandlerFrame.registerHandler(StopDiscoveryEvent.class, (event) -> {
      stopDiscovery();
    });
  }

  public void initialize() {
    eventBus.registerHandlerFrame(eventHandlerFrame);
  }

  public void cleanup() {
    eventBus.removeHandlerFrame(eventHandlerFrame);
  }

  private void createClient() {
    gameClient = new GameClient(eventBus);
  }

  public void update() {
    if (gameClient != null) {
      gameClient.update();
    }
    if (discoverer != null) {
      var event = discoverer.getEvent();
      event.ifPresent(eventBus::dispatch);
    }
  }

  private void connectClient(SocketAddress address) {
    clientThread = new Thread(() -> gameClient.connect(address), "CLIENT_NETWORK");
    clientThread.start();
  }

  private void createServer() {
    gameServer = new GameServer(new InetSocketAddress(Constants.GAME_PORT),
        new InetSocketAddress(Constants.DISCOVERY_PORT));
  }

  private void startDiscovery() {
    discoverer = new NettyServerDiscoverer();
    discoveryThread = new Thread(discoverer, "DISCOVERY");
    discoveryThread.start();
  }

  private void stopDiscovery() {
    discoverer.stop();
    discoveryThread.stop();
  }

}
