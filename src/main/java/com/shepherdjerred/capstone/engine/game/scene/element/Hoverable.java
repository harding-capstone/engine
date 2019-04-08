package com.shepherdjerred.capstone.engine.game.scene.element;

public interface Hoverable extends Containable {

  void onHover();

  void onUnhover();

  boolean isHovered();
}
