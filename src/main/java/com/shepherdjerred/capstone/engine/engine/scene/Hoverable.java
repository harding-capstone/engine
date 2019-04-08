package com.shepherdjerred.capstone.engine.engine.scene;

public interface Hoverable extends Containable {

  void onHover();

  void onUnhover();

  boolean isHovered();
}
