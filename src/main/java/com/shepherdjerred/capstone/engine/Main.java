package com.shepherdjerred.capstone.engine;

import com.shepherdjerred.capstone.engine.engine.GameEngine;
import com.shepherdjerred.capstone.engine.engine.GameLogic;
import com.shepherdjerred.capstone.engine.game.CastleCastersGame;
import com.shepherdjerred.capstone.engine.settings.EngineSettings;
import com.shepherdjerred.capstone.engine.settings.ImmutableEngineSettings;
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
//    Configuration.DEBUG.set(true);
    var settings = getSettings();
    GameLogic logic = new CastleCastersGame();
    GameEngine engine = new GameEngine(logic, settings);
    engine.start();
  }

  private static EngineSettings getSettings() {
    return new ImmutableEngineSettings("Castle Casters", 1024, 768, true, false);
  }
}
