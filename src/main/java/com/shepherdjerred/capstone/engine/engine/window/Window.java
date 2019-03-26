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
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.shepherdjerred.capstone.engine.engine.event.InputEvent;
import com.shepherdjerred.capstone.engine.engine.event.KeyPressedEvent;
import com.shepherdjerred.capstone.engine.engine.event.KeyReleasedEvent;
import com.shepherdjerred.capstone.engine.engine.event.WindowResizedEvent;
import com.shepherdjerred.capstone.engine.engine.input.GlfwKeyConverter;
import com.shepherdjerred.capstone.engine.engine.input.Keyboard.Key;
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
public class Window {

  private final String title;
  @Getter
  private WindowSize windowSize;
  @Getter
  private long windowHandle;
  @Getter
  private boolean isVsyncEnabled;
  private final EventBus<Event> eventBus;
  private final GlfwKeyConverter keyConverter;

  public Window(EventBus<Event> eventBus,
      String title,
      int width,
      int height,
      boolean isVsyncEnabled) {
    this.eventBus = eventBus;
    this.title = title;
    this.windowSize = new WindowSize(width, height);
    this.isVsyncEnabled = isVsyncEnabled;
    this.keyConverter = new GlfwKeyConverter();
  }

  public void init() {
    Configuration.DEBUG.set(true);

    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

    setWindowHints();
    createWindow();
    createCallbacks();
    setupOpenGl();
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
      var event = new WindowResizedEvent(width, height);
      eventBus.dispatch(event);
    });
  }

  private void createWindow() {
    windowHandle = glfwCreateWindow(windowSize.getWidth(), windowSize.getHeight(), title, NULL, NULL);
    if (windowHandle == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

    glfwSetWindowPos(
        windowHandle,
        (vidmode.width() - windowSize.getWidth()) / 2,
        (vidmode.height() - windowSize.getHeight()) / 2
    );

    glfwMakeContextCurrent(windowHandle);

    if (isVsyncEnabled) {
      glfwSwapInterval(1);
    }

    glfwShowWindow(windowHandle);
  }

  private void setupOpenGl() {
    GL.createCapabilities();
    setClearColor(0, 0, 0, 0);
  }

  public void setClearColor(float r, float g, float b, float alpha) {
    glClearColor(r, g, b, alpha);
  }

  public boolean isKeyPressed(int keyCode) {
    return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
  }

  public boolean shouldWindowClose() {
    return glfwWindowShouldClose(windowHandle);
  }

  public void update() {
    glfwSwapBuffers(windowHandle);
    glfwPollEvents();
  }
}
