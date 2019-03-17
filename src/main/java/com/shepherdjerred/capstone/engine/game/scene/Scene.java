package com.shepherdjerred.capstone.engine.game.scene;

import com.shepherdjerred.capstone.engine.engine.Mouse;
import com.shepherdjerred.capstone.engine.engine.Window;
import java.util.Optional;

public interface Scene {

  void init(Window window) throws Exception;

  void handleInput(Window window, Mouse mouse);

  void updateState(float interval);

  void render(Window window);

  void cleanup();

  Optional<Scene> transition();
}
