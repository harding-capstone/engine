package com.shepherdjerred.capstone.engine.game.objects.button;

import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.GENERIC_BUTTON;
import static com.shepherdjerred.capstone.engine.game.objects.button.Button.Type.GENERIC;

import com.shepherdjerred.capstone.engine.engine.object.HoveredTextureSet;
import com.shepherdjerred.capstone.engine.game.objects.button.Button.Type;
import java.util.HashMap;
import java.util.Map;

public class ButtonTextureMapper {

  private final Map<Type, HoveredTextureSet> typeTextureMap;

  public ButtonTextureMapper() {
    typeTextureMap = new HashMap<>();
    initializeMap();
  }

  private void initializeMap() {
    typeTextureMap.put(GENERIC,
        new HoveredTextureSet(GENERIC_BUTTON, GENERIC_BUTTON, GENERIC_BUTTON));
  }

  public HoveredTextureSet get(Type type) {
    return typeTextureMap.get(type);
  }
}
