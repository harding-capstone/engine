package com.shepherdjerred.capstone.engine.game.network.exception;

public class LobbyFullException extends Exception {
  public LobbyFullException() {
    super("Lobby is full");
  }
}
