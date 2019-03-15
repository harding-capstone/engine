package com.shepherdjerred.capstone.engine.engine.graphics.texture;

import com.shepherdjerred.capstone.engine.engine.graphics.Coordinate;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TextureSheet {

  private final Texture texture;
  private final int individualTextureResolution;
  @Getter
  private final int numberOfHorizonalTextures;
  @Getter
  private final int numberOfVerticalTextures;

  public TextureSheet(Texture texture, int individualTextureResolution) {
    this.texture = texture;
    this.individualTextureResolution = individualTextureResolution;
    numberOfHorizonalTextures = texture.getWidth() / individualTextureResolution;
    numberOfVerticalTextures = texture.getHeight() / individualTextureResolution;
  }

  public TextureSheetCoordinates getCoordinatesForTexture(Coordinate coordinate) {
    var textureWidth = texture.getWidth();
    var textureHeight = texture.getHeight();

    var x = coordinate.getX();
    var y = coordinate.getY();

    float rawMinX = x * individualTextureResolution;
    float rawMaxX = x * individualTextureResolution + individualTextureResolution;
    float rawMinY = y * individualTextureResolution;
    float rawMaxY = y * individualTextureResolution + individualTextureResolution;

    float minX = 1 / (textureWidth / rawMinX);
    float maxX = 1 / (textureWidth / rawMaxX);
    float minY = 1 / (textureHeight / rawMinY);
    float maxY = 1 / (textureHeight / rawMaxY);

    return new TextureSheetCoordinates(minX, maxX, minY, maxY);
  }
}
