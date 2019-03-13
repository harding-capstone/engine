package com.shepherdjerred.capstone.engine.game.ui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import com.shepherdjerred.capstone.engine.engine.Renderable;

public class UIPiece implements Renderable {

  private final int width = 30;
  private final int height = 30;
  private int x;
  private int y;

  public UIPiece() {
    x = 0;
    y = 0;
  }

  @Override
  public void render() {
    glBegin(GL_QUADS); // starts the drawing of quads
    {
      glColor3f(0.6f, 0.6f, 0.6f); // sets the color of our quad
      glVertex2f(x, y); // sets the top-left corner position of our quad
      glVertex2f(x, y + height); // sets the bottom-left corner position of our quad
      glVertex2f(x + width, y + height); // sets the bottom-right corner position of our quad
      glVertex2f(x + width, y); // sets the top-right corner position of our quad
    }
    glEnd();
  }
}
