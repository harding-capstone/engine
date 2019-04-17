package com.shepherdjerred.capstone.engine.game.netty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class NettyClientSettings {

  private final String hostname;
  private final int port;
  private final String ip;
}
