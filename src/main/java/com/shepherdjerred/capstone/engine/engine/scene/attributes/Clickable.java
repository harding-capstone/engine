package com.shepherdjerred.capstone.engine.engine.scene.attributes;

/**
 * Represents an object that can be interacted with.
 */
public interface Clickable extends Hoverable {

  void onClickBegin();

  void onClickAbort();

  void onClickEnd();

  boolean isClicked();

}
