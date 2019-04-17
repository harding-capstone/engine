package com.shepherdjerred.capstone.engine.game.network.events.networkEvents;

import com.shepherdjerred.capstone.engine.game.network.Connection;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ServerConnectedEvent implements NetworkEvent {

  private final Connection connection;
}
