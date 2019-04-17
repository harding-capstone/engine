package com.shepherdjerred.capstone.engine.game.netty.networkEvents;

import com.shepherdjerred.capstone.engine.game.netty.ClientId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ClientDisconnectedEvent implements NetworkEvent {

  private final ClientId clientId;
}
