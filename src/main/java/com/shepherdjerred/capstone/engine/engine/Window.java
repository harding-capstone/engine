package com.shepherdjerred.capstone.engine.engine;

import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
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
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.system.MemoryUtil.NULL;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

@Log4j2
public class Window {

  private final String title;
  @Getter
  private int width;
  @Getter
  private int height;
  @Getter
  private long windowHandle;
  @Getter
  @Setter
  private boolean resized;
  @Getter
  private boolean isVsyncEnabled;
  private boolean isWireframe;

  public Window(String title, int width, int height, boolean isVsyncEnabled, boolean isWireframe) {
    this.title = title;
    this.width = width;
    this.height = height;
    this.isVsyncEnabled = isVsyncEnabled;
    this.resized = false;
    this.isWireframe = isWireframe;
  }

  public void init() {
    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if (!glfwInit()) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }

    glfwSetErrorCallback((error, description) -> log.error(
        "GLFW error [" + Integer.toHexString(error) + "]: " + GLFWErrorCallback.getDescription(
            description)));

    glfwDefaultWindowHints(); // optional, the current window hints are already the default
    glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
    glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);

    // Create the window
    windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
    if (windowHandle == NULL) {
      throw new RuntimeException("Failed to create the GLFW window");
    }

    // Setup resize callback
    glfwSetFramebufferSizeCallback(windowHandle, (window, width, height) -> {
      this.width = width;
      this.height = height;
      this.resized = true;
    });

    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
      if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
      }
    });

    // Get the resolution of the primary monitor
    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    // Center our window
    glfwSetWindowPos(
        windowHandle,
        (vidmode.width() - width) / 2,
        (vidmode.height() - height) / 2
    );

    // Make the OpenGL context current
    glfwMakeContextCurrent(windowHandle);

    if (isVsyncEnabled) {
      // Enable v-sync
      glfwSwapInterval(1);
    }

    // Make the window visible
    glfwShowWindow(windowHandle);

    GL.createCapabilities();
    GLUtil.setupDebugMessageCallback(System.out);
    GLUtil.setupDebugMessageCallback();

    if (isWireframe) {
      glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }

    // Set the clear color
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
  }

  public void setClearColor(float r, float g, float b, float alpha) {
    glClearColor(r, g, b, alpha);
  }

  public boolean isKeyPressed(int keyCode) {
    return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
  }

  public boolean windowShouldClose() {
    return glfwWindowShouldClose(windowHandle);
  }

  public void update() {
    glfwSwapBuffers(windowHandle);
    glfwPollEvents();
  }
}
