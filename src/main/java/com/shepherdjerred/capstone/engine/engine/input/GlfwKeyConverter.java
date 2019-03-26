package com.shepherdjerred.capstone.engine.engine.input;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import com.shepherdjerred.capstone.engine.engine.input.Keyboard.Key;
import java.util.Optional;

public class GlfwKeyConverter {

  public Optional<Key> fromGlfwKey(int glfwKey) {
    Key key = null;
    switch (glfwKey) {
      case GLFW_KEY_A:
        key = Key.A;
        break;
      case GLFW_KEY_ESCAPE:
        key = Key.ESCAPE;
        break;
    }
    if (key == null) {
      return Optional.empty();
    } else {
      return Optional.of(key);
    }
  }
}
