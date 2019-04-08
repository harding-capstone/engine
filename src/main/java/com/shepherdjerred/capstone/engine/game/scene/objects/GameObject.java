package com.shepherdjerred.capstone.engine.game.scene.objects;

import com.shepherdjerred.capstone.engine.game.scene.SceneCoordinate;
import com.shepherdjerred.capstone.engine.game.scene.objects.rendering.ObjectRenderer;

public interface GameObject {

  SceneCoordinate getPosition();

  ObjectRenderer getRenderer();
}
