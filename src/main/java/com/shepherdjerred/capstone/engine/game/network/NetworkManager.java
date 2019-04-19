package com.shepherdjerred.capstone.engine.game.network;

import com.shepherdjerred.capstone.engine.game.network.client.NetworkClient;
import com.shepherdjerred.capstone.server.GameServer;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NetworkManager {

  private GameServer gameServer;
  private NetworkClient networkClient;
}
