package net.apicode.snakegame.controller;

public enum SnakeDirection {

  UP(true),
  DOWN(true),
  LEFT(false),
  RIGHT(false);

  private boolean vertical;

  SnakeDirection(boolean vertical) {
    this.vertical = vertical;
  }

  public boolean equalsAxis(SnakeDirection otherDirection) {
    return vertical == otherDirection.vertical;
  }

  public boolean isVerticalDirection() {
    return vertical;
  }

  public boolean isHorizontalDirection() {
    return !vertical;
  }
}
