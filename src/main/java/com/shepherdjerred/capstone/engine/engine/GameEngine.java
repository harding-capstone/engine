package com.shepherdjerred.capstone.engine.engine;

import com.shepherdjerred.capstone.engine.engine.event.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.engine.event.WindowResizeEvent;
import com.shepherdjerred.capstone.engine.engine.event.handler.MouseMoveEventHandler;
import com.shepherdjerred.capstone.engine.engine.event.handler.WindowResizedEventHandler;
import com.shepherdjerred.capstone.engine.engine.input.mouse.MouseTracker;
import com.shepherdjerred.capstone.engine.engine.window.GlfwWindow;
import com.shepherdjerred.capstone.engine.engine.window.Window;
import com.shepherdjerred.capstone.engine.engine.window.WindowSettings;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventLoggerHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class GameEngine {

  private static final int TARGET_FRAMES_PER_SECOND = 60;
  private static final int TARGET_UPDATES_PER_SECOND = 20;

  private final GameLogic gameLogic;
  private final Window window;
  private final EventBus<Event> eventBus;
  private final MouseTracker mouseTracker;
  private final GameLoop gameLoop;

  public GameEngine(GameLogic gameLogic, WindowSettings windowSettings, EventBus<Event> eventBus) {
    this.gameLogic = gameLogic;
    this.eventBus = eventBus;
    this.mouseTracker = new MouseTracker();
    window = new GlfwWindow(windowSettings, mouseTracker, eventBus);
    gameLoop = new GameLoop(gameLogic,
        window,
        eventBus,
        mouseTracker,
        TARGET_FRAMES_PER_SECOND,
        TARGET_UPDATES_PER_SECOND);
  }

  public void run() throws Exception {
    initialize();
    gameLoop.start();
  }

  private void initialize() throws Exception {
    window.initialize();
    gameLogic.initialize(window.getWindowSettings().getWindowSize());
    registerEventHandlers();
  }

  private void registerEventHandlers() {
    eventBus.registerHandler(new EventLoggerHandler<>());
    eventBus.registerHandler(WindowResizeEvent.class, new WindowResizedEventHandler());
    eventBus.registerHandler(MouseMoveEvent.class, new MouseMoveEventHandler(mouseTracker));
  }
}
