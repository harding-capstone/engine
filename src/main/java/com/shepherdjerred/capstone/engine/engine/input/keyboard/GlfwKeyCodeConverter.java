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
      case GLFW_KEY_C:
        key = C;
        break;
      case GLFW_KEY_D:
        key = D;
        break;
      case GLFW_KEY_E:
        key = E;
        break;
      case GLFW_KEY_F:
        key = F;
        break;
      case GLFW_KEY_G:
        key = G;
        break;
      case GLFW_KEY_H:
        key = H;
        break;
      case GLFW_KEY_I:
        key = I;
        break;
      case GLFW_KEY_J:
        key = J;
        break;
      case GLFW_KEY_K:
        key = K;
        break;
      case GLFW_KEY_L:
        key = L;
        break;
      case GLFW_KEY_M:
        key = M;
        break;
      case GLFW_KEY_N:
        key = N;
        break;
      case GLFW_KEY_O:
        key = O;
        break;
      case GLFW_KEY_P:
        key = P;
        break;
      case GLFW_KEY_Q:
        key = Q;
        break;
      case GLFW_KEY_R:
        key = R;
        break;
      case GLFW_KEY_S:
        key = S;
        break;
      case GLFW_KEY_T:
        key = T;
        break;
      case GLFW_KEY_U:
        key = U;
        break;
      case GLFW_KEY_V:
        key = V;
        break;
      case GLFW_KEY_W:
        key = W;
        break;
      case GLFW_KEY_X:
        key = X;
        break;
      case GLFW_KEY_Y:
        key = Y;
        break;
      case GLFW_KEY_Z:
        key = Z;
        break;
      case GLFW_KEY_SPACE:
        key = SPACE;
        break;
      case GLFW_KEY_ONE:
        key = ONE;
        break;
      case GLFW_KEY_TWO:
        key = TWO;
        break;
      case GLFW_KEY_THREE:
        key = THREE;
        break;
      case GLFW_KEY_FOUR:
        key = FOUR;
        break;
      case GLFW_KEY_FIVE:
        key = FIVE;
        break;
      case GLFW_KEY_SIX:
        key = SIX;
        break;
      case GLFW_KEY_SEVEN:
        key = SEVEN;
        break;
      case GLFW_KEY_EIGHT:
        key = EIGHT;
        break;
      case GLFW_KEY_NINE:
        key = NINE;
        break;
      case GLFW_KEY_ZERO:
        key = ZERO;
        break;
      case GLFW_KEY_UP:
        key = UP;
        break;
      case GLFW_KEY_DOWN:
        key = DOWN;
        break;
      case GLFW_KEY_LEFT:
        key = LEFT;
        break;
      case GLFW_KEY_RIGHT:
        key = RIGHT;
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
