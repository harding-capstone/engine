package com.shepherdjerred.capstone.engine.game.network.discovery;

import com.shepherdjerred.capstone.common.lobby.LobbySettings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents the information a server sends to a client to identify a game.
 */
@Getter
@ToString
@AllArgsConstructor
public class ServerInformation {

  private final NetworkConnectionData networkConnectionData;
  private final LobbySettings lobbySettings;
}
