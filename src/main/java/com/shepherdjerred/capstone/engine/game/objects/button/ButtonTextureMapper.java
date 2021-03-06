package com.shepherdjerred.capstone.engine.game.objects.button;

import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.GENERIC_BUTTON;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.GENERIC_BUTTON_CLICKED;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.GENERIC_BUTTON_HOVERED;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.MAIN_MENU_BUTTON;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.MAIN_MENU_BUTTON_CLICKED;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.MAIN_MENU_BUTTON_HOVERED;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.UNKNOWN;
import static com.shepherdjerred.capstone.engine.game.objects.button.Button.Type.GENERIC;
import static com.shepherdjerred.capstone.engine.game.objects.button.Button.Type.HOME;

import com.shepherdjerred.capstone.engine.engine.object.ClickableTextureSet;
import com.shepherdjerred.capstone.engine.game.objects.button.Button.Type;
import java.util.HashMap;
import java.util.Map;

public class ButtonTextureMapper {

  private final Map<Type, ClickableTextureSet> typeTextureMap;

  public ButtonTextureMapper() {
    typeTextureMap = new HashMap<>();
    initializeMap();
  }

  private void initializeMap() {
    typeTextureMap.put(GENERIC,
        new ClickableTextureSet(GENERIC_BUTTON, GENERIC_BUTTON_HOVERED, GENERIC_BUTTON_CLICKED));
    typeTextureMap.put(HOME,
        new ClickableTextureSet(MAIN_MENU_BUTTON,
            MAIN_MENU_BUTTON_HOVERED,
            MAIN_MENU_BUTTON_CLICKED));
  }

  public ClickableTextureSet get(Type type) {
    return typeTextureMap.getOrDefault(type, new ClickableTextureSet(UNKNOWN, UNKNOWN, UNKNOWN));
  }
}
