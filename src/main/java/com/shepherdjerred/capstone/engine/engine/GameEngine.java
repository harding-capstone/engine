package com.shepherdjerred.capstone.engine.engine;

import com.shepherdjerred.capstone.engine.settings.EngineSettings;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GameEngine implements Runnable {

  private final int targetFramesPerSecond = 60;
  private final int targetUpdatesPerSecond = 20;
  private final Window window;
  private final Thread gameLoopThread;
  private final Timer timer;
  private final GameLogic gameLogic;
  private final Mouse mouse;

  public GameEngine(GameLogic gameLogic, EngineSettings engineSettings) {
    gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
    window = new Window(engineSettings.getWindowTitle(),
        engineSettings.getWindowWidth(),
        engineSettings.getWindowHeight(),
        engineSettings.isVsyncEnabled(),
        engineSettings.isWireframeEnabled());
    this.gameLogic = gameLogic;
    timer = new Timer();
    mouse = new Mouse();
  }

  public void start() {
    if (isOperatingSystemMacOs()) {
      gameLoopThread.run();
    } else {
      gameLoopThread.start();
    }
  }

  private boolean isOperatingSystemMacOs() {
    return System.getProperty("os.name").contains("Mac");
  }

  @Override
  public void run() {
    try {
      initialize();
      gameLoop();
    } catch (Exception e) {
      log.catching(e);
    } finally {
      cleanup();
    }
  }

  private void initialize() throws Exception {
    window.init();
    timer.init();
    mouse.init(window);
    gameLogic.init(window);
  }

  private void gameLoop() throws Exception {
    float elapsedTime;
    float accumulator = 0f;
    float updateInterval = 1f / targetUpdatesPerSecond;

    boolean isRunning = true;
    while (isRunning && !window.windowShouldClose()) {
      elapsedTime = timer.getElapsedTime();
      accumulator += elapsedTime;

      handleInput();

      while (accumulator >= updateInterval) {
        updateGameState(updateInterval);
        accumulator -= updateInterval;
      }

      render();

      if (!window.isVsyncEnabled()) {
        sync();
      }
    }
  }

  private void sync() {
    float loopSlot = 1f / targetFramesPerSecond;
    double endTime = timer.getLastLoopTime() + loopSlot;
    while (timer.getTime() < endTime) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException ignored) {
      }
    }
  }

  private void handleInput() {
    mouse.updatePosition();
    gameLogic.handleInput(window, mouse);
  }

  private void updateGameState(float interval) {
    gameLogic.updateGameState(interval);
  }

  private void render() throws Exception {
    gameLogic.render(window);
    window.update();
  }

  private void cleanup() {
    gameLogic.cleanup();
  }
}
