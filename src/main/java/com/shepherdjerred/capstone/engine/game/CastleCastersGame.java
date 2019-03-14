package com.shepherdjerred.capstone.engine.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import com.shepherdjerred.capstone.engine.engine.Coordinate;
import com.shepherdjerred.capstone.engine.engine.GameItem;
import com.shepherdjerred.capstone.engine.engine.GameLogic;
import com.shepherdjerred.capstone.engine.engine.Window;
import com.shepherdjerred.capstone.engine.engine.graphics.Mesh;
import java.util.ArrayList;
import java.util.List;

public class CastleCastersGame implements GameLogic {

  private final Renderer renderer;
  private final List<GameItem> gameItems;

  private int displxInc = 0;
  private int displyInc = 0;
  private int rotateInc = 0;
  private int scaleInc = 0;

  public CastleCastersGame() {
    renderer = new Renderer();
    gameItems = new ArrayList<>();
  }

  private Mesh squareMesh() {
    float[] positions = new float[] {
        300f, 300f, 0f,
        300f, 600f, 0f,
        600f, 300f, 0f,
        600f, 600f, 0f
    };
    float[] colours = new float[] {
        0.5f, 0.0f, 0.0f,
        0.0f, 0.5f, 0.0f,
        0.0f, 0.0f, 0.5f,
        0.0f, 0.5f, 0.5f
    };
    int[] indices = new int[] {
        0, 1, 2, 1, 2, 3
    };
    return new Mesh(positions, colours, indices);
  }

  private Mesh triforceMesh() {
    var vertices = new float[] {
        300, 0, 0, // top top
        400, 100, 0, // top right && right top
        200, 100, 0, // top left && left top
        300, 200, 0, // left right && right left
        500, 200, 0, // right right
        100, 200, 0 // left left
    };
    float[] colors = new float[9 * 3];
    float baseRed = .77f;
    float baseGreen = .66f;
    float baseBlue = .34f;

    for (int i = 0; i < 9; i++) {
      colors[i * 3] = baseRed + i * .05f;
      colors[i * 3 + 1] = baseGreen + i * .05f;
      colors[i * 3 + 2] = baseBlue + i * .05f;
    }

    int[] indices = new int[] {0, 1, 2, 2, 5, 3, 1, 3, 4};
    return new Mesh(vertices, colors, indices);
  }

  @Override
  public void init(Window window) throws Exception {
    renderer.init(window);
    gameItems.add(new GameItem(300, 300, squareMesh()));
    gameItems.add(new GameItem(200, 200, triforceMesh()));
  }

  @Override
  public void handleInput(Window window) {
    displyInc = 0;
    displxInc = 0;
    rotateInc = 0;
    scaleInc = 0;
    if (window.isKeyPressed(GLFW_KEY_UP)) {
      displyInc = 1;
    }
    if (window.isKeyPressed(GLFW_KEY_DOWN)) {
      displyInc = -1;
    }
    if (window.isKeyPressed(GLFW_KEY_LEFT)) {
      displxInc = -1;
    }
    if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
      displxInc = 1;
    }
    if (window.isKeyPressed(GLFW_KEY_A)) {
      rotateInc = -1;
    }
    if (window.isKeyPressed(GLFW_KEY_Q)) {
      rotateInc = 1;
    }
    if (window.isKeyPressed(GLFW_KEY_Z)) {
      scaleInc = -1;
    }
    if (window.isKeyPressed(GLFW_KEY_X)) {
      scaleInc = 1;
    }
  }

  @Override
  public void updateGameState(float interval) {
    for (GameItem gameItem : gameItems) {
      // Update position
      var itemPos = gameItem.getPosition();
      float posx = itemPos.getX() + displxInc * 3;
      float posy = itemPos.getY() + displyInc * 3;
      gameItem.setPosition(new Coordinate(posx, posy, itemPos.getZ()));

      // Update scale
      float scale = gameItem.getScale();
      scale += scaleInc * 0.05f;
      if (scale < 0) {
        scale = 0;
      }
      gameItem.setScale(scale);

      // Update rotation angle
      float rotation = gameItem.getRotation() + rotateInc;
      if (rotation > 360) {
        rotation = 0;
      }
      gameItem.setRotation(rotation);
    }
  }

  @Override
  public void render(Window window) {
    renderer.render(window, gameItems);
  }

  @Override
  public void cleanup() {
    renderer.cleanup();
    gameItems.forEach(gameItem -> {
      gameItem.getMesh().cleanup();
    });
  }
}
