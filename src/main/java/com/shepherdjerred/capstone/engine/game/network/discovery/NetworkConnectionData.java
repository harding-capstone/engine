package com.shepherdjerred.capstone.engine.game.network.discovery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class NetworkConnectionData {

  private final String hostname;
  private final int port;
}
