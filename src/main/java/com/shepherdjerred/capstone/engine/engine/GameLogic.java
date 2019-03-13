package com.shepherdjerred.capstone.engine.engine;

public interface GameLogic {

  void init() throws Exception;

  void handleInput(Window window);

  void updateGameState(float interval);

  void render(Window window);

  void cleanup();
}
