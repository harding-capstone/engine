package com.shepherdjerred.capstone.engine.engine.scene;

public interface Clickable extends Containable {

  void onClick();

  void onRelease();

  boolean isClicked();

}
