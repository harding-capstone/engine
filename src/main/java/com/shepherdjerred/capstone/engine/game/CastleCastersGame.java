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
import com.shepherdjerred.capstone.engine.engine.graphics.texture.Texture;
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

  private GameItem createWizard() throws Exception {
    var texture = new Texture(
        "/Users/jerred/IdeaProjects/capstone/engine/src/main/resources/textures/front_fire.png");

    var pos = new float[] {
        64, 64, 0,
        64, 96, 0,
        96, 64, 0,
        96, 96, 0
    };

    var tex = new float[] {
        0, .33f,
        0, .66f,
        1, .33f,
        1, .66f,
        0, .66f,
        1, .33f
    };

    var ind = new int[] {
        0, 1, 2,
        3, 1, 2
    };

    var mesh = new Mesh(pos, tex, ind, texture);
    return new GameItem(mesh);
  }

  private GameItem createWall() throws Exception {
    var texture = new Texture(
        "/Users/jerred/IdeaProjects/capstone/engine/src/main/resources/textures/wall_frost.png");

    var pos = new float[] {
        32, 32, 0,
        32, 64, 0,
        64, 32, 0,
        64, 64, 0
    };

    var tex = new float[] {
        0, 0,
        0, .2f,
        .25f, 0,
        .25f, .2f,
        0, .2f,
        .25f, 0
    };

    var ind = new int[] {
        0, 1, 2,
        3, 1, 2
    };

    var mesh = new Mesh(pos, tex, ind, texture);
    return new GameItem(mesh);
  }

  private GameItem createTexturedSquare() throws Exception {
    var texture = new Texture(
        "/Users/jerred/IdeaProjects/capstone/engine/src/main/resources/textures/grass.png");

    var pos = new float[] {
        0, 0, 0,
        0, 32, 0,
        32, 0, 0,
        32, 32, 0
    };

    var tex = new float[] {
        0, 0,
        0, 1,
        1, 0,
        1, 1,
        0, 1,
        1, 0
    };

    var ind = new int[] {
        0, 1, 2,
        3, 1, 2
    };

    var mesh = new Mesh(pos, tex, ind, texture);
    return new GameItem(mesh);
  }

  @Override
  public void init(Window window) throws Exception {
    renderer.init(window);
    gameItems.add(createTexturedSquare());
    gameItems.add(createWall());
    gameItems.add(createWizard());
  }

  @Override
  public void handleInput(Window window) {
    displyInc = 0;
    displxInc = 0;
    rotateInc = 0;
    scaleInc = 0;
    if (window.isKeyPressed(GLFW_KEY_UP)) {
      displyInc = -1;
    }
    if (window.isKeyPressed(GLFW_KEY_DOWN)) {
      displyInc = 1;
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
      float posx = itemPos.getX() + displxInc * 9;
      float posy = itemPos.getY() + displyInc * 9;
      gameItem.setPosition(new Coordinate(posx, posy, itemPos.getZ()));

      // Update scale
      float scale = gameItem.getScale();
      scale += scaleInc * 0.5f;
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
    gameItems.forEach(gameItem -> gameItem.getMesh().cleanup());
  }
}
