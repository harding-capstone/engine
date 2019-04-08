package com.shepherdjerred.capstone.engine.game.scene.objects;

public interface Clickable extends Containable {

  void onClick();

  void onRelease();

  boolean isClicked();

}
