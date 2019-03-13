package com.shepherdjerred.capstone.engine.engine;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GameEngine implements Runnable {

  private final int targetFps = 60;
  private final int targetUps = 20;
  private final Window window;
  private final Thread gameLoopThread;
  private final Timer timer;
  private final GameLogic gameLogic;

  public GameEngine(String windowTitle,
      int windowWidth,
      int windowHeight,
      boolean isVsyncEnabled,
      GameLogic gameLogic) {
    gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
    window = new Window(windowTitle, windowWidth, windowHeight, isVsyncEnabled);
    this.gameLogic = gameLogic;
    timer = new Timer();
  }

  public void start() {
    String osName = System.getProperty("os.name");
    if (osName.contains("Mac")) {
      gameLoopThread.run();
    } else {
      gameLoopThread.start();
    }
  }

  @Override
  public void run() {
    try {
      initialize();
      gameLoop();
    } catch (Exception e) {
      log.error(e);
    }
  }

  private void initialize() throws Exception {
    window.init();
    timer.init();
    gameLogic.init();
  }

  private void gameLoop() {
    float elapsedTime;
    float accumulator = 0f;
    float interval = 1f / targetUps;

    boolean running = true;
    while (running && !window.windowShouldClose()) {
      elapsedTime = timer.getElapsedTime();
      accumulator += elapsedTime;

      input();

      while (accumulator >= interval) {
        update(interval);
        accumulator -= interval;
      }

      render();

      if (!window.isVsyncEnabled()) {
        sync();
      }
    }
  }

  private void sync() {
    float loopSlot = 1f / targetFps;
    double endTime = timer.getLastLoopTime() + loopSlot;
    while (timer.getTime() < endTime) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException ignored) {
      }
    }
  }

  private void input() {
    gameLogic.input(window);
  }

  private void update(float interval) {
    gameLogic.update(interval);
  }

  private void render() {
    gameLogic.render(window);
    window.update();
  }
}
