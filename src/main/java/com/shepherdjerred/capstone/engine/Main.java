package com.shepherdjerred.capstone.engine;

import com.shepherdjerred.capstone.engine.engine.GameEngine;
import com.shepherdjerred.capstone.engine.engine.settings.EngineSettings;
import com.shepherdjerred.capstone.engine.engine.settings.ImmutableEngineSettings;
import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

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
    var settings = getSettings();
    EventBus<Event> eventBus = new EventBus<>();
    var logic = new CastleCastersGame(eventBus);
    var engine = new GameEngine(logic, settings, eventBus);
    engine.start();
  }

  private static EngineSettings getSettings() {
    return new ImmutableEngineSettings("Castle Casters",
        1360,
        768,
        true);
  }
}
