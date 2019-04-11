package com.shepherdjerred.capstone.engine.engine.input.keyboard;

import static com.shepherdjerred.capstone.engine.engine.input.keyboard.Key.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_B;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.util.Optional;

/**
 * Converts GLFW integer keycodes to a Key enum.
 */
public class GlfwKeyCodeConverter {

  public Optional<Key> fromGlfwKey(int glfwKey) {
    Key key = null;

    switch (glfwKey) {
      case GLFW_KEY_A:
        key = A;
        break;
      case GLFW_KEY_B:
        key = B;
        break;
      case GLFW_KEY_D:
        key = D;
        break;
      case GLFW_KEY_W:
        key = W;
        break;
      case GLFW_KEY_ESCAPE:
        key = ESCAPE;
        break;
    }

    if (key == null) {
      return Optional.empty();
    } else {
      return Optional.of(key);
    }
  }
}
