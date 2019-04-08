package com.shepherdjerred.capstone.engine.game.scene.element;

public interface Clickable extends Containable {

  void onClick();

  void onRelease();

  boolean isClicked();

}
