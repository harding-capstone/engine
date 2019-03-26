package com.shepherdjerred.capstone.engine.engine;

import com.shepherdjerred.capstone.engine.engine.event.WindowResizedEvent;
import com.shepherdjerred.capstone.engine.engine.event.handler.WindowResizedEventHandler;
import com.shepherdjerred.capstone.engine.engine.input.GlfwKeyConverter;
import com.shepherdjerred.capstone.engine.engine.window.GlfwWindow;
import com.shepherdjerred.capstone.engine.engine.window.WindowSettings;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GameEngine implements Runnable {

  private static final int TARGET_FRAMES_PER_SECOND = 60;
  private static final int TARGET_UPDATES_PER_SECOND = 20;

  private final GameLogic gameLogic;
  private final GlfwWindow window;
  private final Thread gameLoopThread;
  private final Timer timer;

  private final EventBus<Event> eventBus;

  public GameEngine(GameLogic gameLogic, WindowSettings windowSettings, EventBus<Event> eventBus) {
    this.gameLogic = gameLogic;
    this.eventBus = eventBus;
    gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
    window = new GlfwWindow(windowSettings, new GlfwKeyConverter(), eventBus);
    timer = new Timer();
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
    window.initialize();
    timer.init();
    gameLogic.initialize(window.getWindowSettings().getWindowSize());
    registerEventHandlers();
  }

  private void runGameLoop() throws Exception {
    float elapsedTime;
    float accumulator = 0f;
    float updateInterval = 1f / TARGET_UPDATES_PER_SECOND;

    while (!window.shouldClose()) {
      elapsedTime = timer.getElapsedTime();
      accumulator += elapsedTime;

      handleInput();

      while (accumulator >= updateInterval) {
        updateGameState(updateInterval);
        accumulator -= updateInterval;
      }

      render();

      if (!window.getWindowSettings().isVsyncEnabled()) {
        sync();
      }
    }
  }

  private void sync() {
    float loopSlot = 1f / TARGET_FRAMES_PER_SECOND;
    double endTime = timer.getLastLoopTime() + loopSlot;
    while (timer.getTime() < endTime) {
      try {
        Thread.sleep(1);
      } catch (InterruptedException ignored) {
      }
    }
  }

  private void handleInput() {
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

  private void registerEventHandlers() {
    eventBus.registerHandler(WindowResizedEvent.class, new WindowResizedEventHandler());
  }
}
