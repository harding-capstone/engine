package com.shepherdjerred.capstone.engine.engine.resource;

import static com.shepherdjerred.capstone.engine.engine.graphics.font.FontName.FIRA_CODE;
import static com.shepherdjerred.capstone.engine.engine.graphics.font.FontName.M5X7;
import static com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName.*;

import com.shepherdjerred.capstone.engine.engine.audio.AudioName;
import com.shepherdjerred.capstone.engine.engine.graphics.font.FontName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
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
  private final HashMap<TextureName, String> textureFileNameHashMap;
  private final HashMap<FontName, String> fontFileNameHashMap;
  private final HashMap<AudioName, String> audioFileHashMap;

  public PathResourceFileLocator(String texturesBasePath,
      String fontsBasePath,
      String audioBasePath) {
    this.texturesBasePath = texturesBasePath;
    this.fontsBasePath = fontsBasePath;
    this.audioBasePath = audioBasePath;

    textureFileNameHashMap = new HashMap<>();
    fontFileNameHashMap = new HashMap<>();
    audioFileHashMap = new HashMap<>();

    initializeTextureMap();
    initializeFontMap();
    initializeAudioMap();
  }

  private void initializeAudioMap() {
    audioFileHashMap.put(AudioName.THEME_MUSIC, "music/theme.ogg");
    audioFileHashMap.put(AudioName.VICTORY_MUSIC, "music/victory.ogg");
    audioFileHashMap.put(AudioName.DEFEAT_MUSIC, "music/defeat.ogg");
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
        "ui/backgrounds/parallax/purple-mountains/purple-mountains-a.png");
    textureFileNameHashMap.put(PURPLE_MOUNTAINS_B,
        "ui/backgrounds/parallax/purple-mountains/purple-mountains-b.png");
    textureFileNameHashMap.put(PURPLE_MOUNTAINS_C,
        "ui/backgrounds/parallax/purple-mountains/purple-mountains-c.png");
    textureFileNameHashMap.put(PURPLE_MOUNTAINS_D,
        "ui/backgrounds/parallax/purple-mountains/purple-mountains-d.png");
    textureFileNameHashMap.put(PURPLE_MOUNTAINS_E,
        "ui/backgrounds/parallax/purple-mountains/purple-mountains-e.png");
    textureFileNameHashMap.put(PLAINS_A,
        "ui/backgrounds/parallax/plains/plains-a.png");
    textureFileNameHashMap.put(PLAINS_B,
        "ui/backgrounds/parallax/plains/plains-b.png");
    textureFileNameHashMap.put(PLAINS_C,
        "ui/backgrounds/parallax/plains/plains-c.png");
    textureFileNameHashMap.put(PLAINS_D,
        "ui/backgrounds/parallax/plains/plains-d.png");
    textureFileNameHashMap.put(PLAINS_E,
        "ui/backgrounds/parallax/plains/plains-e.png");
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

  @Override
  public String getAudioPath(AudioName audioName) {
    return audioBasePath + audioFileHashMap.get(audioName);
  }
}
