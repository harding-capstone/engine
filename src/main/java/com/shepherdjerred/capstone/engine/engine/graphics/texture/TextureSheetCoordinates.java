package com.shepherdjerred.capstone.engine.engine.graphics.texture;

import com.shepherdjerred.capstone.engine.engine.graphics.RendererCoordinate;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TextureSheetCoordinates {

  private final float minX;
  private final float maxX;
  private final float minY;
  private final float maxY;
  private final RendererCoordinate topLeft;
  private final RendererCoordinate topRight;
  private final RendererCoordinate bottomLeft;
  private final RendererCoordinate bottomRight;

  public TextureSheetCoordinates(float minX, float maxX, float minY, float maxY) {
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
    this.topLeft = new RendererCoordinate(minX, minY);
    this.topRight = new RendererCoordinate(maxX, minY);
    this.bottomLeft = new RendererCoordinate(minX, maxY);
    this.bottomRight = new RendererCoordinate(maxX, maxY);
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

  public float[] asIndexedFloatArray() {
    return new float[] {
        bottomLeft.getX(), bottomLeft.getY(),
        topLeft.getX(), topLeft.getY(),
        bottomRight.getX(), bottomRight.getY(),
        topRight.getX(), topRight.getY()
    };
  }
}
