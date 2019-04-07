package com.shepherdjerred.capstone.engine.engine.window;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
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
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
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
import com.shepherdjerred.capstone.engine.engine.event.MouseButtonDownEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseButtonUpEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseEnterEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseLeaveEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseMoveEvent;
import com.shepherdjerred.capstone.engine.engine.event.MouseScrollEvent;
import com.shepherdjerred.capstone.engine.engine.event.WindowResizeEvent;
import com.shepherdjerred.capstone.engine.engine.input.GlfwKeyCodeConverter;
import com.shepherdjerred.capstone.engine.engine.input.Key;
import com.shepherdjerred.capstone.engine.engine.input.mouse.GlfwMouseCodeConverter;
import com.shepherdjerred.capstone.engine.engine.input.mouse.MouseButton;
import com.shepherdjerred.capstone.engine.engine.input.mouse.MouseCoordinate;
import com.shepherdjerred.capstone.engine.engine.input.mouse.MouseTracker;
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
  private final GlfwKeyCodeConverter keyCodeConverter;
  private final GlfwMouseCodeConverter mouseCodeConverter;
  private final EventBus<Event> eventBus;
  private final MouseTracker mouseTracker;

  public GlfwWindow(WindowSettings windowSettings,
      MouseTracker mouseTracker,
      EventBus<Event> eventBus) {
    this.windowSettings = windowSettings;
    this.keyCodeConverter = new GlfwKeyCodeConverter();
    this.mouseCodeConverter = new GlfwMouseCodeConverter();
    this.eventBus = eventBus;
    this.mouseTracker = mouseTracker;
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
      Optional<Key> key = keyCodeConverter.fromGlfwKey(glfwKey);

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
      var event = new WindowResizeEvent(new WindowSize(width, height));
      eventBus.dispatch(event);
    });

    glfwSetCursorPosCallback(windowHandle, (windowHandle, x, y) -> {
      // TODO fix casting?
      var event = new MouseMoveEvent(new MouseCoordinate((int) x, (int) y));
      eventBus.dispatch(event);
    });

    glfwSetMouseButtonCallback(windowHandle, (windowHandle, glfwButton, action, mode) -> {
      Optional<MouseButton> mouseButton = mouseCodeConverter.fromGlfwButton(glfwButton);

      if (mouseButton.isPresent()) {
        Event event = null;
        if (action == GLFW_PRESS) {
          event = new MouseButtonDownEvent(mouseButton.get(), mouseTracker.getMousePosition());
        } else if (action == GLFW_RELEASE) {
          event = new MouseButtonUpEvent(mouseButton.get(), mouseTracker.getMousePosition());
        }

        if (event != null) {
          eventBus.dispatch(event);
        }
      }
    });

    glfwSetCursorEnterCallback(windowHandle, (windowHandle, entered) -> {
      Event event;
      if (entered) {
        event = new MouseEnterEvent();
      } else {
        event = new MouseLeaveEvent();
      }
      eventBus.dispatch(event);
    });

    glfwSetScrollCallback(windowHandle, (windowHandle, xOffset, yOffset) ->
        eventBus.dispatch(new MouseScrollEvent((int) xOffset, (int) yOffset)));
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

    glfwSetWindowPos(windowHandle,
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
