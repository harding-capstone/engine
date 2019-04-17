package com.shepherdjerred.capstone.engine.game.netty;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Identifier for a client connection.
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ClientId {

  private final UUID uuid;
}
