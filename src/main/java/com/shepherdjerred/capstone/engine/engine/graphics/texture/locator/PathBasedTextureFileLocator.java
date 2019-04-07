package com.shepherdjerred.capstone.engine.engine.graphics.texture.locator;

import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.*;

import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import java.util.HashMap;
import lombok.ToString;

/**
 * Returns the path to a texture file.
 */
@ToString
public class PathBasedTextureFileLocator implements TextureFileLocator {

  private final String basePath;
  private final HashMap<TextureName, String> fileHashMap;

  public PathBasedTextureFileLocator(String basePath) {
    this.basePath = basePath;
    this.fileHashMap = new HashMap<>();
    initializeMap();
  }

  private void initializeMap() {
    fileHashMap.put(FROST_WALL, "wall_frost.png");
    fileHashMap.put(FIRE_WIZARD_FRONT, "front_fire.png");
    fileHashMap.put(TERRAIN, "terrain.png");
    fileHashMap.put(MAIN_MENU_BUTTON, "ui/mainMenu_button_big.png");
  }

  @Override
  public String getTexturePath(TextureName textureName) {
    return basePath + fileHashMap.get(textureName);
  }
}
