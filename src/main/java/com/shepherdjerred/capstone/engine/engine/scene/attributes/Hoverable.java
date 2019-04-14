package com.shepherdjerred.capstone.engine.engine.scene.attributes;

/**
 * An object that can be hovered over.
 */
public interface Hoverable extends Collidable {

  void onHover();

  void onUnhover();

  boolean isHovered();
}
