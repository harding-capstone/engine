package com.shepherdjerred.capstone.engine.game.scene;

import com.shepherdjerred.capstone.engine.engine.Mouse;
import com.shepherdjerred.capstone.engine.engine.RenderedElement;
import com.shepherdjerred.capstone.engine.engine.Window;
import com.shepherdjerred.capstone.engine.engine.graphics.Coordinate;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.TextureLoader;
import com.shepherdjerred.capstone.engine.engine.graphics.texture.locator.PathBasedTextureFileLocator;
import com.shepherdjerred.capstone.engine.game.ui.Button;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainMenuScene implements Scene {

  private List<RenderedElement> elements;

  public MainMenuScene() {
    elements = new ArrayList<>();
  }

  @Override
  public void init(Window window) throws Exception {
    var textureLocator = new PathBasedTextureFileLocator(
        "/Users/jerred/IdeaProjects/capstone/engine/src/main/resources/textures/");
    var textureLoader = new TextureLoader(textureLocator);
    var button = new Button(textureLoader, new Coordinate(0, 0), 200, 75);
    var element = new RenderedElement(button.getMesh());
    elements.add(element);
  }

  @Override
  public void handleInput(Window window, Mouse mouse) {

  }

  @Override
  public void updateState(float interval) {

  }

  @Override
  public void render(Window window) {
    elements.forEach(element -> element.getTexturedMesh().render());
  }

  @Override
  public void cleanup() {

  }

  @Override
  public Optional<Scene> transition() {
    return Optional.empty();
  }
}
