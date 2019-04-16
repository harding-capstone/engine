package com.shepherdjerred.capstone.engine.engine.object;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.object.ClickableGameObject.State;
import java.util.HashMap;
import java.util.Map;

public class HoveredTextureSet {

  private final Map<State, TextureName> stateTextureMap;

  public HoveredTextureSet(TextureName defaultTexture, TextureName hovered, TextureName clicked) {
    stateTextureMap = new HashMap<>();
    stateTextureMap.put(State.INACTIVE, defaultTexture);
    stateTextureMap.put(State.HOVERED, hovered);
    stateTextureMap.put(State.CLICKED, clicked);
  }

  public TextureName getTexture(State state) {
    return stateTextureMap.get(state);
  }

  public TextureName getInactiveTexture() {
    return stateTextureMap.get(State.INACTIVE);
  }

  public TextureName getHoveredTexture() {
    return stateTextureMap.get(State.HOVERED);
  }

  public TextureName getClickedTexture() {
    return stateTextureMap.get(State.CLICKED);
  }
}
