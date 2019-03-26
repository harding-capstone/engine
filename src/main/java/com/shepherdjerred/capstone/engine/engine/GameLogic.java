package com.shepherdjerred.capstone.engine.engine;

import com.shepherdjerred.capstone.engine.engine.input.Mouse;
import com.shepherdjerred.capstone.engine.engine.window.Window;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;

public interface GameLogic {

  void initialize(WindowSize windowSize) throws Exception;

  void handleInput(Window window, Mouse mouse);

  void updateGameState(float interval);

  void render() throws Exception;

  void cleanup();
}
