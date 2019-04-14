package com.shepherdjerred.capstone.engine.engine.resource;

import static com.shepherdjerred.capstone.engine.engine.graphics.font.FontName.FIRA_CODE;
import static com.shepherdjerred.capstone.engine.engine.graphics.font.FontName.M5X7;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.FIRE_WIZARD_FRONT;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.FROST_WALL;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.GAME_LOGO;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.MAIN_MENU_BUTTON;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.MAIN_MENU_BUTTON_CLICKED;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.MAIN_MENU_BUTTON_HOVERED;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PLAINS_A;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PLAINS_B;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PLAINS_C;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PLAINS_D;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PLAINS_E;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PURPLE_MOUNTAINS;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PURPLE_MOUNTAINS_A;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PURPLE_MOUNTAINS_B;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PURPLE_MOUNTAINS_C;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PURPLE_MOUNTAINS_D;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.PURPLE_MOUNTAINS_E;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.RED_PLAINS;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.TEAM_LOGO;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.TERRAIN;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.UNKNOWN;

import com.shepherdjerred.capstone.engine.engine.audio.AudioName;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.map.GameMapName;
import java.util.HashMap;
import lombok.ToString;

/**
 * Returns the path to a texture file.
 */
@ToString
public class PathResourceFileLocator implements ResourceFileLocator {

  private final String texturesBasePath;
  private final String fontsBasePath;
  private final String audioBasePath;
  private final String mapBasePath;
  private final HashMap<ResourceIdentifier, String> resourcePaths;

  public PathResourceFileLocator(String texturesBasePath,
      String fontsBasePath,
      String audioBasePath,
      String mapBasePath) {
    this.texturesBasePath = texturesBasePath;
    this.fontsBasePath = fontsBasePath;
    this.audioBasePath = audioBasePath;
    this.mapBasePath = mapBasePath;

    resourcePaths = new HashMap<>();

    initializeTexturePaths();
    initializeFontPaths();
    initializeAudioPaths();
    initializeMapPaths();
  }

  private void initializeMapPaths() {
    resourcePaths.put(GameMapName.GRASS, "grass.json");
    resourcePaths.put(GameMapName.DESERT, "desert.json");
    resourcePaths.put(GameMapName.ICE, "winter.json");
  }

  private void initializeAudioPaths() {
    resourcePaths.put(AudioName.THEME_MUSIC, "music/theme.ogg");
    resourcePaths.put(AudioName.VICTORY_MUSIC, "music/victory.ogg");
    resourcePaths.put(AudioName.DEFEAT_MUSIC, "music/defeat.ogg");
  }

  private void initializeTexturePaths() {
    resourcePaths.put(FROST_WALL, "wall_frost.png");
    resourcePaths.put(FIRE_WIZARD_FRONT, "front_fire.png");
    resourcePaths.put(TERRAIN, "terrain.png");
    resourcePaths.put(MAIN_MENU_BUTTON, "ui/buttons/main-menu-default.png");
    resourcePaths.put(MAIN_MENU_BUTTON_HOVERED,
        "ui/buttons/main-menu-hovered.png");
    resourcePaths.put(MAIN_MENU_BUTTON_CLICKED,
        "ui/buttons/main-menu-active.png");
    resourcePaths.put(PURPLE_MOUNTAINS, "ui/backgrounds/purple mountains.png");
    resourcePaths.put(PURPLE_MOUNTAINS_A,
        "ui/backgrounds/parallax/purple-mountains/purple-mountains-a.png");
    resourcePaths.put(PURPLE_MOUNTAINS_B,
        "ui/backgrounds/parallax/purple-mountains/purple-mountains-b.png");
    resourcePaths.put(PURPLE_MOUNTAINS_C,
        "ui/backgrounds/parallax/purple-mountains/purple-mountains-c.png");
    resourcePaths.put(PURPLE_MOUNTAINS_D,
        "ui/backgrounds/parallax/purple-mountains/purple-mountains-d.png");
    resourcePaths.put(PURPLE_MOUNTAINS_E,
        "ui/backgrounds/parallax/purple-mountains/purple-mountains-e.png");
    resourcePaths.put(PLAINS_A,
        "ui/backgrounds/parallax/plains/plains-a.png");
    resourcePaths.put(PLAINS_B,
        "ui/backgrounds/parallax/plains/plains-b.png");
    resourcePaths.put(PLAINS_C,
        "ui/backgrounds/parallax/plains/plains-c.png");
    resourcePaths.put(PLAINS_D,
        "ui/backgrounds/parallax/plains/plains-d.png");
    resourcePaths.put(PLAINS_E,
        "ui/backgrounds/parallax/plains/plains-e.png");
    resourcePaths.put(RED_PLAINS, "ui/backgrounds/red plains.png");
    resourcePaths.put(GAME_LOGO, "logos/game logo.png");
    resourcePaths.put(TEAM_LOGO, "logos/team logo.png");
    resourcePaths.put(UNKNOWN, "unknown.png");
  }

  private void initializeFontPaths() {
    resourcePaths.put(M5X7, "m5x7.ttf");
    resourcePaths.put(FIRA_CODE, "FiraCode-Regular.ttf");
  }

  @Override
  public String getTexturePath(TextureName textureName) {
    return texturesBasePath + resourcePaths.getOrDefault(textureName, "unknown.png");
  }

  @Override
  public String getFontPath(FontName fontName) {
    return fontsBasePath + resourcePaths.get(fontName);
  }

  @Override
  public String getAudioPath(AudioName audioName) {
    return audioBasePath + resourcePaths.get(audioName);
  }
}
