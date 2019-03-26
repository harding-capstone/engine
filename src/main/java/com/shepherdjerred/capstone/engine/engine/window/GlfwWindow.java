package com.shepherdjerred.capstone.engine.engine.window;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_DEBUG_CONTEXT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.shepherdjerred.capstone.engine.engine.event.InputEvent;
import com.shepherdjerred.capstone.engine.engine.event.KeyPressedEvent;
import com.shepherdjerred.capstone.engine.engine.event.KeyReleasedEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseDownEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseUpEvent;
import com.shepherdjerred.capstone.engine.engine.event.WindowResizedEvent;
import com.shepherdjerred.capstone.engine.engine.input.GlfwKeyConverter;
import com.shepherdjerred.capstone.engine.engine.input.Keyboard.Key;
import com.shepherdjerred.capstone.engine.engine.input.Mouse;
import com.shepherdjerred.capstone.engine.engine.input.Mouse.Button;
import com.shepherdjerred.capstone.events.Event;
import com.shepherdjerred.capstone.events.EventBus;
import java.util.Optional;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Configuration;

@Log4j2
public class GlfwWindow implements Window {

  @Getter
  private WindowSettings windowSettings;
  private long windowHandle;
  private final GlfwKeyConverter keyConverter;
  private final EventBus<Event> eventBus;

  public GlfwWindow(WindowSettings windowSettings,
      GlfwKeyConverter glfwKeyConverter,
      EventBus<Event> eventBus) {
    this.windowSettings = windowSettings;
    this.keyConverter = glfwKeyConverter;
    this.eventBus = eventBus;
  }

  @Override
  public void initialize() {
    Configuration.DEBUG.set(true);

    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

    setWindowHints();
    createWindow();
    createCallbacks();
    setupOpenGl();
  }

  @Override
  public boolean shouldClose() {
    return glfwWindowShouldClose(windowHandle);
  }

  @Override
  public void swapBuffers() {
    glfwSwapBuffers(windowHandle);
  }

  @Override
  public void pollEvents() {
    glfwPollEvents();
  }

  private void setWindowHints() {
    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
  }

  private void createCallbacks() {
    glfwSetErrorCallback((error, description) -> log.error(
        "GLFW error [" + Integer.toHexString(error) + "]: " + GLFWErrorCallback.getDescription(
            description)));

    glfwSetKeyCallback(windowHandle, (window, glfwKey, scancode, action, mods) -> {
      Optional<Key> key = keyConverter.fromGlfwKey(glfwKey);

      if (key.isPresent()) {
        InputEvent event = null;

        if (action == GLFW_PRESS) {
          event = new KeyPressedEvent(key.get());
        } else if (action == GLFW_RELEASE) {
          event = new KeyReleasedEvent(key.get());
        }

        if (event != null) {
          eventBus.dispatch(event);
        }
      }
    });

    glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
      var event = new WindowResizedEvent(new WindowSize(width, height));
      eventBus.dispatch(event);
    });

    glfwSetCursorPosCallback(windowHandle, (windowHandle, x, y) -> {
      var event = new MouseMoveEvent(x, y);
      eventBus.dispatch(event);
    });

    glfwSetMouseButtonCallback(windowHandle, (windowHandle, glfwButton, action, mode) -> {
      Mouse.Button button = null;

      if (glfwButton == GLFW_MOUSE_BUTTON_LEFT) {
        button = Button.LEFT;
      } else if (glfwButton == GLFW_MOUSE_BUTTON_RIGHT) {
        button = Button.RIGHT;
      }

      if (button != null) {
        Event event = null;
        if (action == GLFW_PRESS) {
          event = new MouseDownEvent(button);
        } else if (action == GLFW_RELEASE) {
          event = new MouseUpEvent(button);
        }

        if (event != null) {
          eventBus.dispatch(event);
        }
      }
    });
  }

  private void createWindow() {
    windowHandle = glfwCreateWindow(windowSettings.getWindowSize().getWidth(),
        windowSettings.getWindowSize().getHeight(),
        windowSettings.getTitle(),
        NULL,
        NULL);
    if (windowHandle == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    glfwSetWindowPos(
        windowHandle,
        (vidmode.width() - windowSettings.getWindowSize().getWidth()) / 2,
        (vidmode.height() - windowSettings.getWindowSize().getHeight()) / 2
    );

    glfwMakeContextCurrent(windowHandle);

    if (windowSettings.isVsyncEnabled()) {
      glfwSwapInterval(1);
    }

    glfwShowWindow(windowHandle);
  }

  private void setupOpenGl() {
    GL.createCapabilities();
  }
}
