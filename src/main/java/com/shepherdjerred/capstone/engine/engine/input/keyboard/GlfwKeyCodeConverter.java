package com.shepherdjerred.capstone.engine.engine.input.keyboard;

import static com.shepherdjerred.capstone.engine.engine.input.keyboard.Key.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

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