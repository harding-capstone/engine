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

  private Mesh triangleMesh() {
    float[] positions = new float[]{
        -0.5f,  0.5f, -.05f,
        -0.5f, -0.5f, -.05f,
        0.5f, -0.5f, -.05f,
        0.5f,  0.5f, -.05f,
    };
    float[] colours = new float[]{
        0.5f, 0.0f, 0.0f,
        0.0f, 0.5f, 0.0f,
        0.0f, 0.0f, 0.5f,
        0.0f, 0.5f, 0.5f,
    };
    int[] indices = new int[]{
        0, 1, 3, 3, 1, 2,
    };
    return new Mesh(positions, colours, indices);
  }

  private Mesh triforceMesh() {
    var vertices = new float[] {
        300f, 500f, 0f, // top top
        400f, 400f, 0f, // top right && right top
        200, 400f, 0f, // top left && left top
        300f, 300f, 0f, // left right && right left
        500f, 300f, 0f, // right right
        100, 300f, 0f // left left
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
    mesh = triforceMesh();
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
