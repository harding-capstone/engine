package com.shepherdjerred.capstone.engine;

import com.shepherdjerred.capstone.engine.engine.GameEngine;
import com.shepherdjerred.capstone.engine.engine.GameLogic;
import com.shepherdjerred.capstone.engine.game.CastleCastersGameLogic;
import com.shepherdjerred.capstone.engine.storage.Settings;
import com.shepherdjerred.capstone.engine.storage.StaticSettings;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

  public static void main(String[] args) {
    try {
      init();
    } catch (Exception e) {
      log.error(e);
      System.exit(1);
    }
  }

  private static void init() {
    var settings = getSettings();
    GameLogic logic = new CastleCastersGameLogic();
    GameEngine engine = new GameEngine(settings.getGameName(),
        600,
        480,
        settings.isVsyncEnabled(),
        logic);
    engine.start();
  }

  private static Settings getSettings() {

    return new StaticSettings("Castle Casters", true);
  }
}
