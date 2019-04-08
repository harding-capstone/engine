package com.shepherdjerred.capstone.engine.engine.scene.attributes;

/**
 * Represents an object that can be interacted with.
 */
public interface Clickable extends Bounded {

  void onClickBegin();

  void onClickEnd();

  boolean isClicked();

}
