package com.shepherdjerred.capstone.engine.engine.scene.attributes;

/**
 * An object that can be hovered over.
 */
public interface Hoverable extends Bounded {

  void onHover();

  void onUnhover();

  boolean isHovered();
}
