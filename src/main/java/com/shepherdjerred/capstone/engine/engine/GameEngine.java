package com.shepherdjerred.capstone.engine.engine;

import com.shepherdjerred.capstone.engine.engine.input.Mouse;
import com.shepherdjerred.capstone.engine.engine.settings.EngineSettings;
import com.shepherdjerred.capstone.engine.engine.window.Window;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
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
  private final EventBus<Event> eventBus;

  public GameEngine(GameLogic gameLogic, EngineSettings engineSettings, EventBus<Event> eventBus) {
    gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
    window = new Window(eventBus,
        engineSettings.getWindowTitle(),
        engineSettings.getWindowWidth(),
        engineSettings.getWindowHeight(),
        engineSettings.isVsyncEnabled());
    this.eventBus = eventBus;
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
      runGameLoop();
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
    gameLogic.initialize(window.getWindowSize());
  }

  private void runGameLoop() throws Exception {
    float elapsedTime;
    float accumulator = 0f;
    float updateInterval = 1f / targetUpdatesPerSecond;

    boolean isRunning = true;
    while (isRunning && !window.shouldWindowClose()) {
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
    gameLogic.render();
    window.update();
  }

  private void cleanup() {
    gameLogic.cleanup();
  }
}
