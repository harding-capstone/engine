package com.shepherdjerred.capstone.engine.game.scene.objects;

public interface Hoverable extends Containable {

  void onHover();

  void onUnhover();

  boolean isHovered();
}
