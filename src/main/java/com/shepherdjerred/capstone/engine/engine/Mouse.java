package com.shepherdjerred.capstone.engine.engine;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

import lombok.Getter;
import org.joml.Vector2d;
import org.joml.Vector2f;

public class Mouse {

  @Getter
  private final Vector2d previousPos;
  @Getter
  private final Vector2d currentPos;
  @Getter
  private final Vector2f displVec;
  private boolean inWindow;
  @Getter
  private boolean leftButtonPressed;
  @Getter
  private boolean rightButtonPressed;

  public Mouse() {
    previousPos = new Vector2d(-1, -1);
    currentPos = new Vector2d(0, 0);
    displVec = new Vector2f();
    inWindow = false;
    leftButtonPressed = false;
    rightButtonPressed = false;
  }

  public void init(Window window) {
    glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xpos, ypos) -> {
      currentPos.x = xpos;
      currentPos.y = ypos;
    });
    glfwSetCursorEnterCallback(window.getWindowHandle(),
        (windowHandle, entered) -> inWindow = entered);
    glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
      leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
      rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
    });
  }

  public void updatePosition() {
    displVec.x = 0;
    displVec.y = 0;
    if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
      double deltax = currentPos.x - previousPos.x;
      double deltay = currentPos.y - previousPos.y;
      boolean rotateX = deltax != 0;
      boolean rotateY = deltay != 0;
      if (rotateX) {
        displVec.y = (float) deltax;
      }
      if (rotateY) {
        displVec.x = (float) deltay;
      }
    }
    previousPos.x = currentPos.x;
    previousPos.y = currentPos.y;
  }
}
