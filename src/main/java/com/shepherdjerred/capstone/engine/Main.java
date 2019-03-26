package com.shepherdjerred.capstone.engine;

import com.shepherdjerred.capstone.engine.engine.GameEngine;
import com.shepherdjerred.capstone.engine.engine.window.WindowSettings;
import com.shepherdjerred.capstone.engine.engine.window.WindowSize;
import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import com.shepherdjerred.capstone.events.handlers.EventLoggerHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

  private static EventBus<Event> eventBus = new EventBus<>();

  public static void main(String[] args) {
    try {
      init();
    } catch (Exception e) {
      log.catching(e);
      log.error(e.getStackTrace());
      System.exit(1);
    }
  }

  private static void init() {
    createEventListeners();
    var settings = getSettings();
    var logic = new CastleCastersGame(eventBus);
    var engine = new GameEngine(logic, settings, eventBus);
    engine.start();
  }

  private static void createEventListeners() {
    eventBus.registerHandler(new EventLoggerHandler<>());
  }

  private static WindowSettings getSettings() {
    return new WindowSettings("Castle Casters",
        new WindowSize(1360, 768),
        true);
  }
}
