package com.shepherdjerred.capstone.engine.engine.graphics.texture;

import com.shepherdjerred.capstone.engine.engine.graphics.Coordinate;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TextureSheetCoordinates {

  private final float minX;
  private final float maxX;
  private final float minY;
  private final float maxY;
  private final Coordinate topLeft;
  private final Coordinate topRight;
  private final Coordinate bottomLeft;
  private final Coordinate bottomRight;

  public TextureSheetCoordinates(float minX, float maxX, float minY, float maxY) {
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
    this.topLeft = new Coordinate(minX, minY);
    this.topRight = new Coordinate(maxX, minY);
    this.bottomLeft = new Coordinate(minX, maxY);
    this.bottomRight = new Coordinate(maxX, maxY);
  }

  // TODO better name
  public float[] firstHalf() {
    return new float[] {
        topLeft.getX(), topLeft.getY(),
        bottomLeft.getX(), bottomLeft.getY(),
        topRight.getX(), topRight.getY()
    };
  }

  // TODO better name
  public float[] secondHalf() {
    return new float[] {
        bottomRight.getX(), bottomRight.getY(),
        bottomLeft.getX(), bottomLeft.getY(),
        topRight.getX(), topRight.getY()
    };
  }

  public float[] asFloatArray() {
    return new float[] {
        topLeft.getX(), topLeft.getY(),
        bottomLeft.getX(), bottomLeft.getY(),
        topRight.getX(), topRight.getY(),
        bottomRight.getX(), bottomRight.getY(),
        bottomLeft.getX(), bottomLeft.getY(),
        topRight.getX(), topRight.getY()
    };
  }
}
