package com.shepherdjerred.capstone.engine.engine;

import static org.lwjgl.opengl.GL11.glGetError;

import com.shepherdjerred.capstone.engine.engine.graphics.ErrorConverter;
import com.shepherdjerred.capstone.engine.engine.input.mouse.MouseTracker;
import com.shepherdjerred.capstone.engine.engine.window.Window;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GameLoop implements Runnable {

  private final GameLogic gameLogic;
  private final Window window;
  private final Timer timer;
  private final EventBus<Event> eventBus;
  private final MouseTracker mouseTracker;
  private final Thread gameLoopThread;
  private final int targetFramesPerSecond;
  private final int targetUpdatesPerSecond;

  public GameLoop(GameLogic gameLogic,
      Window window,
      EventBus<Event> eventBus,
      MouseTracker mouseTracker,
      int targetFramesPerSecond,
      int targetUpdatesPerSecond) {
    this.gameLogic = gameLogic;
    this.window = window;
    this.timer = new Timer();
    this.eventBus = eventBus;
    this.mouseTracker = mouseTracker;
    gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
    this.targetFramesPerSecond = targetFramesPerSecond;
    this.targetUpdatesPerSecond = targetUpdatesPerSecond;
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

  private void updateGameState(float interval) {
    gameLogic.updateGameState(interval);
  }

  private void render() throws Exception {
    gameLogic.render();
    window.swapBuffers();
    window.pollEvents();
  }

  private void cleanup() {
    gameLogic.cleanup();
  }

  @Override
  public void run() {
    try {
      runGameLoop();
    } catch (Exception e) {
      log.catching(e);
    } finally {
      cleanup();
    }
  }

  private void runGameLoop() throws Exception {
    float elapsedTime;
    float accumulator = 0f;
    float updateInterval = 1f / targetUpdatesPerSecond;

    while (!window.shouldClose()) {
      elapsedTime = timer.getElapsedTime();
      accumulator += elapsedTime;

      while (accumulator >= updateInterval) {
        updateGameState(updateInterval);
        accumulator -= updateInterval;
      }

      render();

      if (!window.getWindowSettings().isVsyncEnabled()) {
        sync();
      }

      printOpenGlErrors();
    }
  }

  public void start() {
    if (isOperatingSystemMacOs()) {
      gameLoopThread.run();
    } else {
      gameLoopThread.start();
    }
  }

  private void printOpenGlErrors() {
    int errCode = glGetError();
    if (errCode != 0) {
      var converter = new ErrorConverter();
      log.error("OpenGL error: " + converter.convert(errCode));
    }
  }

  private boolean isOperatingSystemMacOs() {
    return System.getProperty("os.name").contains("Mac");
  }
}
