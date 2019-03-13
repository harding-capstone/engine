package com.shepherdjerred.capstone.engine.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;

import com.shepherdjerred.capstone.engine.engine.GameLogic;
import com.shepherdjerred.capstone.engine.engine.Window;
import com.shepherdjerred.capstone.engine.engine.graphics.Mesh;

public class CastleCastersGame implements GameLogic {

  private int direction = 0;
  private float color = 0.0f;
  private final Renderer renderer;
  private Mesh mesh;

  public CastleCastersGame() {
    renderer = new Renderer();
  }

  @Override
  public void init() throws Exception {
    renderer.init();

    var vertices = new float[] {
        0f, .5f, 0f,
        .25f, .25f, 0f,
        -.25f, .25f, 0f,
        .25f, .25f, 0f,
        .5f, 0f, 0f,
        0f, 0f, 0f,
        -.25f, .25f, 0f,
        0f, 0f, 0f,
        -.5f, 0f, 0f
    };
    float[] colors = new float[] {
        0.5f, 0.0f, 0.0f,
        0.0f, 0.5f, 0.0f,
        0.0f, 0.0f, 0.5f,
        0.0f, 0.5f, 0.5f,
        0.5f, 0.0f, 0.0f,
        0.0f, 0.5f, 0.0f,
        0.0f, 0.0f, 0.5f,
        0.0f, 0.5f, 0.5f,
        0.5f, 0.0f, 0.0f,
    };
    int[] indices = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
    mesh = new Mesh(vertices, colors, indices);
  }

  @Override
  public void handleInput(Window window) {
    if (window.isKeyPressed(GLFW_KEY_UP)) {
      direction = 1;
    } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
      direction = -1;
    } else {
      direction = 0;
    }
  }

  @Override
  public void updateGameState(float interval) {
    color += direction * 0.01f;
    if (color > 1) {
      color = 1.0f;
    } else if (color < 0) {
      color = 0.0f;
    }
  }

  @Override
  public void render(Window window) {

    window.setClearColor(color, color, color, 0.0f);
    renderer.render(window, mesh);
  }

  @Override
  public void cleanup() {
    renderer.cleanup();
    mesh.cleanup();
  }
}
