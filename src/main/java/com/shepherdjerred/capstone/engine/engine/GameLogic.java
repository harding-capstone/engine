package com.shepherdjerred.capstone.engine.engine;

public interface GameLogic {

  void init(Window window) throws Exception;

  void handleInput(Window window, Mouse mouse);

  void updateGameState(float interval);

  void render(Window window) throws Exception;

  void cleanup();
}
