package net.apicode.snakegame.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.apicode.snakegame.frame.FieldPosition;

public class Snake {

  private FieldPosition fieldPosition;
  private SnakeDirection direction = SnakeDirection.UP;
  private SnakeDirection currentDirection = SnakeDirection.UP;
  private final List<FieldPosition> positions = new ArrayList<>();
  private int growPoints = 4;

  public Snake(FieldPosition fieldPosition) {
    this.fieldPosition = fieldPosition;
  }

  public List<FieldPosition> getBodyPositions() {
    return Collections.unmodifiableList(positions);
  }

  public void addGrowProcess() {
    growPoints++;
  }

  public int getSize() {
    return positions.size() + 1;
  }

  public FieldPosition getPosition() {
    return fieldPosition;
  }

  public void setPosition(FieldPosition fieldPosition) {
    this.fieldPosition = fieldPosition;
  }

  public SnakeDirection getDirection() {
    return direction;
  }

  public void setDirection(SnakeDirection direction) {
    if(!this.currentDirection.equalsAxis(direction)) {
      this.direction = direction;
    }
  }

  public void updateMovement() {
    switch (direction) {
      case UP -> {
        updateBodyPositions();
        fieldPosition.setY(fieldPosition.getY()-1);
      }
      case DOWN -> {
        updateBodyPositions();
        fieldPosition.setY(fieldPosition.getY()+1);
      }
      case LEFT -> {
        updateBodyPositions();
        fieldPosition.setX(fieldPosition.getX()-1);
      }
      case RIGHT -> {
        updateBodyPositions();
        fieldPosition.setX(fieldPosition.getX()+1);
      }
    }
    currentDirection = direction;
  }

  public int getMaximumMealFields() {
    return (getSize() / 8) + 1;
  }

  private void updateBodyPositions() {
    if(growPoints > 0) {
      growPoints--;
    } else {
      if(!positions.isEmpty()) {
        positions.remove(positions.size() - 1);
      }
    }
    positions.add(0, fieldPosition.clone());
  }

  public boolean isPositionValid(int maxColumns, int maxRows) {
    if(!fieldPosition.isPositionValid(maxColumns, maxRows)) return false;
    return !positions.contains(fieldPosition);
  }

}
