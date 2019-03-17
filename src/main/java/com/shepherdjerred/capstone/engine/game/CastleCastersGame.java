package com.shepherdjerred.capstone.engine.game;

import com.shepherdjerred.capstone.engine.engine.GameLogic;
import com.shepherdjerred.capstone.engine.engine.Mouse;
import com.shepherdjerred.capstone.engine.engine.Window;
import com.shepherdjerred.capstone.engine.game.scene.Scene;
import com.shepherdjerred.capstone.engine.game.scene.TileDemoScene;

public class CastleCastersGame implements GameLogic {

  private Scene scene;

  public CastleCastersGame() {
    scene = new TileDemoScene();
  }

  @Override
  public void init(Window window) throws Exception {
    scene.init(window);
  }

  @Override
  public void handleInput(Window window, Mouse mouse) {
    scene.handleInput(window, mouse);
  }

  @Override
  public void updateGameState(float interval) {
    scene.updateState(interval);
  }

  @Override
  public void render(Window window) throws Exception {
    if (scene.transition().isPresent()) {
      scene.cleanup();
      scene = scene.transition().get();
      scene.init(window);
      scene.render(window);
    } else {
      scene.render(window);
    }
  }

  @Override
  public void cleanup() {
    scene.cleanup();
  }
}
