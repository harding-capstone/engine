package com.shepherdjerred.capstone.engine.game.scene;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.glPolygonMode;

import com.shepherdjerred.capstone.engine.engine.Mouse;
import com.shepherdjerred.capstone.engine.engine.RenderedElement;
import com.shepherdjerred.capstone.engine.engine.Window;
import com.shepherdjerred.capstone.engine.engine.graphics.Coordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.TexturedMesh;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureName;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureSheet;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.locator.PathBasedTextureFileLocator;
import com.shepherdjerred.capstone.engine.game.Renderer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class TileDemoScene implements Scene {

  private Optional<Scene> nextScene;
  private final Renderer renderer;
  private List<RenderedElement> renderedElements;
  private TextureLoader textureLoader;

  public TileDemoScene() {
    nextScene = Optional.empty();
    renderer = new Renderer();
    renderedElements = new ArrayList<>();
    var textureLocator = new PathBasedTextureFileLocator(
        "/Users/jerred/IdeaProjects/capstone/engine/src/main/resources/textures/");
    textureLoader = new TextureLoader(textureLocator);
  }

  private int translateX = 0;
  private int translateY = 0;
  private int rotate = 0;
  private int scale = 0;

  @Override
  public void init(Window window) throws Exception {
    renderer.init(window);
    var width = window.getWidth();
    var height = window.getHeight();
    for (int i = width; i > 0; i -= 32) {
      for (int j = height; j > 0; j -= 32) {
        var tile = createFromTexturedSheet();
        tile.setPosition(new Coordinate((float) i, (float) j));
        renderedElements.add(tile);
      }
    }
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
        var newItem = createFromTexturedSheet();
        newItem.setPosition(new Coordinate((float) mouse.getCurrentPos().x,
            (float) mouse.getCurrentPos().y,
            0));
        renderedElements.add(newItem);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    if (mouse.isRightButtonPressed()) {
      var newGameItems = new ArrayList<>(renderedElements);
      for (RenderedElement renderedElement : renderedElements) {
        var mouseX = mouse.getCurrentPos().x;
        var mouseY = mouse.getCurrentPos().y;
        var itemX = renderedElement.getPosition().getX();
        var itemY = renderedElement.getPosition().getY();
        if (Math.abs(itemX - mouseX) < 10 && Math.abs(itemY - mouseY) < 10) {
          newGameItems.remove(renderedElement);
          renderedElement.getTexturedMesh().cleanup();
        }
      }
      renderedElements = newGameItems;
    }

    if (window.isKeyPressed(GLFW_KEY_T)) {
      nextScene = Optional.of(new MainMenuScene());
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
    if (window.isKeyPressed(GLFW_KEY_W)) {
      glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    } else {
      glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
    }
  }

  @Override
  public void updateState(float interval) {
    for (RenderedElement renderedElement : renderedElements) {
      // Update position
      var itemPos = renderedElement.getPosition();
      float posx = itemPos.getX() + translateX * 9;
      float posy = itemPos.getY() + translateY * 9;
      renderedElement.setPosition(new Coordinate(posx, posy, itemPos.getZ()));

      // Update scale
      float scale = renderedElement.getScale();
      scale += this.scale * 0.5f;
      if (scale < 0) {
        scale = 0;
      }
      renderedElement.setScale(scale);

      // Update rotation angle
      float rotation = renderedElement.getRotation() + rotate;
      if (rotation > 360) {
        rotation = 0;
      }
      renderedElement.setRotation(rotation);
    }

  }

  @Override
  public void render(Window window) {
    renderer.render(window, renderedElements);
  }

  @Override
  public void cleanup() {
    renderer.cleanup();
    renderedElements.forEach(renderedElement -> renderedElement.getTexturedMesh().cleanup());
  }

  @Override
  public Optional<Scene> transition() {
    return nextScene;
  }


  private float[] getDefaultPos() {
    return new float[] {
        0, 0, 0,
        0, 32, 0,
        32, 0, 0,
        32, 32, 0
    };
  }

  private int[] getDefaultInd() {
    return new int[] {
        0, 1, 2,
        3, 1, 2
    };
  }

  private RenderedElement createFromTexturedSheet() {
    var texture = textureLoader.loadTexture(TextureName.TERRAIN);
    var textureSheet = new TextureSheet(texture, 16);

    int randomX = ThreadLocalRandom.current()
        .nextInt(0, textureSheet.getNumberOfHorizonalTextures());
    int randomY = ThreadLocalRandom.current()
        .nextInt(0, textureSheet.getNumberOfVerticalTextures());

    var texCoords = textureSheet.getCoordinatesForTexture(new Coordinate(randomX, randomY));

    var pos = getDefaultPos();
    var ind = getDefaultInd();

    var mesh = new TexturedMesh(pos, texCoords.asFloatArray(), ind, texture);
    return new RenderedElement(mesh);
  }
}
