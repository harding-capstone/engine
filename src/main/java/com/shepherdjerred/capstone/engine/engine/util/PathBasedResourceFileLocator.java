package com.shepherdjerred.capstone.engine.engine.util;

import static com.shepherdjerred.capstone.engine.engine.graphics.font.FontName.FIRA_CODE;
import static com.shepherdjerred.capstone.engine.engine.graphics.font.FontName.M5X7;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.*;

import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import java.util.HashMap;
import lombok.ToString;

/**
 * Returns the path to a texture file.
 */
@ToString
public class PathBasedResourceFileLocator implements ResourceFileLocator {

  private final String texturesBasePath;
  private final String fontsBasePath;
  private final HashMap<TextureName, String> textureFileNameHashMap;
  private final HashMap<FontName, String> fontFileNameHashMap;

  public PathBasedResourceFileLocator(String texturesBasePath, String fontsBasePath) {
    this.texturesBasePath = texturesBasePath;
    this.fontsBasePath = fontsBasePath;
    textureFileNameHashMap = new HashMap<>();
    fontFileNameHashMap = new HashMap<>();
    initializeTextureMap();
    initializeFontMap();
  }

  private void initializeTextureMap() {
    textureFileNameHashMap.put(FROST_WALL, "wall_frost.png");
    textureFileNameHashMap.put(FIRE_WIZARD_FRONT, "front_fire.png");
    textureFileNameHashMap.put(TERRAIN, "terrain.png");
    textureFileNameHashMap.put(MAIN_MENU_BUTTON, "ui/buttons/main-menu-default.png");
    textureFileNameHashMap.put(MAIN_MENU_BUTTON_HOVERED,
        "ui/buttons/main-menu-hovered.png");
    textureFileNameHashMap.put(MAIN_MENU_BUTTON_CLICKED,
        "ui/buttons/main-menu-active.png");
    textureFileNameHashMap.put(PURPLE_MOUNTAINS, "ui/backgrounds/purple mountains.png");
    textureFileNameHashMap.put(PURPLE_MOUNTAINS_A,
        "ui/backgrounds/parallax/purple-mountains/Purple montains BGa.png");
    textureFileNameHashMap.put(PURPLE_MOUNTAINS_B,
        "ui/backgrounds/parallax/purple-mountains/Purple montains BGb.png");
    textureFileNameHashMap.put(PURPLE_MOUNTAINS_C,
        "ui/backgrounds/parallax/purple-mountains/Purple montains BGc.png");
    textureFileNameHashMap.put(PURPLE_MOUNTAINS_D,
        "ui/backgrounds/parallax/purple-mountains/Purple montains BGd.png");
    textureFileNameHashMap.put(PURPLE_MOUNTAINS_E,
        "ui/backgrounds/parallax/purple-mountains/Purple montains BGe.png");
    textureFileNameHashMap.put(RED_PLAINS, "ui/backgrounds/red plains.png");
    textureFileNameHashMap.put(GAME_LOGO, "logos/game logo.png");
    textureFileNameHashMap.put(TEAM_LOGO, "logos/team logo.png");
    textureFileNameHashMap.put(UNKNOWN, "unknown.png");
  }

  private void initializeFontMap() {
    fontFileNameHashMap.put(M5X7, "m5x7.ttf");
    fontFileNameHashMap.put(FIRA_CODE, "FiraCode-Regular.ttf");
  }

  @Override
  public String getTexturePath(TextureName textureName) {
    return texturesBasePath + textureFileNameHashMap.getOrDefault(textureName, "unknown.png");
  }

  @Override
  public String getFontPath(FontName fontName) {
    return fontsBasePath + fontFileNameHashMap.get(fontName);
  }
}
