package com.shepherdjerred.capstone.engine.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import com.shepherdjerred.capstone.engine.engine.GameItem;
import com.shepherdjerred.capstone.engine.engine.GameLogic;
import com.shepherdjerred.capstone.engine.engine.Mouse;
import com.shepherdjerred.capstone.engine.engine.Window;
import com.shepherdjerred.capstone.engine.engine.graphics.Coordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.Mesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.locator.PathBasedTextureFileLocator;
import java.util.ArrayList;
import java.util.List;

public class CastleCastersGame implements GameLogic {

  private final Renderer renderer;
  private List<GameItem> gameItems;
  private TextureLoader textureLoader;

  private int translateX = 0;
  private int translateY = 0;
  private int rotate = 0;
  private int scale = 0;

  public CastleCastersGame() {
    renderer = new Renderer();
    gameItems = new ArrayList<>();
    var textureLocator = new PathBasedTextureFileLocator(
        "/Users/jerred/IdeaProjects/capstone/engine/src/main/resources/textures/");
    textureLoader = new TextureLoader(textureLocator);
  }

  private GameItem createWizard() {
    var texture = textureLoader.loadTexture(TextureName.FIRE_WIZARD_FRONT);

    var pos = new float[] {
        0, 0, 0,
        0, 32, 0,
        32, 0, 0,
        32, 32, 0
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

  private GameItem createWall() {
    var texture = textureLoader.loadTexture(TextureName.FROST_WALL);

    var pos = new float[] {
        0, 0, 0,
        0, 32, 0,
        32, 0, 0,
        32, 32, 0
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

  private GameItem createTexturedSquare() {
    var texture = textureLoader.loadTexture(TextureName.GRASS);

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

    var wall = createWall();
    gameItems.add(wall);

    var wizard = createWizard();
    gameItems.add(wizard);

    var offsetSquare = createTexturedSquare();
    offsetSquare.setPosition(new Coordinate(200, 200, 0));
    gameItems.add(offsetSquare);
  }

  @Override
  public void handleInput(Window window, Mouse mouse) {
    translateY = 0;
    translateX = 0;
    rotate = 0;
    scale = 0;
//    translateX += mouse.getDisplVec().y;
//    translateY += mouse.getDisplVec().x;
//    if (mouse.isLeftButtonPressed()) {
//      scale += 1;
//    }
//    if (mouse.isRightButtonPressed()) {
//      scale += -1;
//    }
    if (mouse.isLeftButtonPressed()) {
      try {
        var newItem = createWizard();
        newItem.setPosition(new Coordinate((float) mouse.getCurrentPos().x,
            (float) mouse.getCurrentPos().y,
            0));
        gameItems.add(newItem);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (mouse.isRightButtonPressed()) {
      var newGameItems = new ArrayList<>(gameItems);
      for (GameItem gameItem : gameItems) {
        var mouseX = mouse.getCurrentPos().x;
        var mouseY = mouse.getCurrentPos().y;
        var itemX = gameItem.getPosition().getX();
        var itemY = gameItem.getPosition().getY();
        if (Math.abs(itemX - mouseX) < 5) {
          newGameItems.remove(gameItem);
        }
        if (Math.abs(itemY - mouseY) < 5) {
          newGameItems.remove(gameItem);
        }
      }
      gameItems = newGameItems;
    }

    if (window.isKeyPressed(GLFW_KEY_UP)) {
      translateY += -1;
    }
    if (window.isKeyPressed(GLFW_KEY_DOWN)) {
      translateY += 1;
    }
    if (window.isKeyPressed(GLFW_KEY_LEFT)) {
      translateX += -1;
    }
    if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
      translateX += 1;
    }
    if (window.isKeyPressed(GLFW_KEY_A)) {
      rotate += -1;
    }
    if (window.isKeyPressed(GLFW_KEY_Q)) {
      rotate += 1;
    }
    if (window.isKeyPressed(GLFW_KEY_Z)) {
      scale += -1;
    }
    if (window.isKeyPressed(GLFW_KEY_X)) {
      scale += 1;
    }
  }

  @Override
  public void updateGameState(float interval) {
    for (GameItem gameItem : gameItems) {
      // Update position
      var itemPos = gameItem.getPosition();
      float posx = itemPos.getX() + translateX * 9;
      float posy = itemPos.getY() + translateY * 9;
      gameItem.setPosition(new Coordinate(posx, posy, itemPos.getZ()));

      // Update scale
      float scale = gameItem.getScale();
      scale += this.scale * 0.5f;
      if (scale < 0) {
        scale = 0;
      }
      gameItem.setScale(scale);

      // Update rotation angle
      float rotation = gameItem.getRotation() + rotate;
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
