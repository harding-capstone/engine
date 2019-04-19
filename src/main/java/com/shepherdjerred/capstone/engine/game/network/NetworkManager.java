package com.shepherdjerred.capstone.engine.game.network;

import com.shepherdjerred.capstone.common.Constants;
import com.shepherdjerred.capstone.engine.game.network.client.NetworkClient;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.server.GameServer;
import java.net.InetSocketAddress;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NetworkManager {

  private final EventBus<Event> eventBus;
  private GameServer gameServer;
  private NetworkClient networkClient;
  private Thread serverThread;
  private Thread clientThread;

  public void createClient() {
      networkClient = new NetworkClient(eventBus);
  }

  public void updateClient() {
    networkClient.update();
  }

  public void connectClient(InetSocketAddress address) {
    new Thread(() -> {
      networkClient.connect(address);
    }, "CLIENT_THREAD");
  }

  public void createServer() {
    gameServer = new GameServer(new InetSocketAddress(Constants.GAME_PORT),
        new InetSocketAddress(Constants.DISCOVERY_PORT));
  }

}
