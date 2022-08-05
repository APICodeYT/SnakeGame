package net.apicode.snakegame.frame;

public class FieldPosition {

  private int x;
  private int y;

  public FieldPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public FieldPosition() {
    this(1, 1);
  }

  public FieldPosition setColumn(int x) {
    this.x = x;
    return this;
  }

  public FieldPosition setRow(int y) {
    this.y = y;
    return this;
  }

  public int getColumn() {
    return x;
  }

  public int getRow() {
    return y;
  }

  public FieldPosition setX(int x) {
    this.x = x;
    return this;
  }

  public FieldPosition setY(int y) {
    this.y = y;
    return this;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isPositionValid(int maxColumns, int maxRows) {
    return x > 0 && y > 0 && x <= maxColumns && y <= maxRows;
  }

  public FieldPosition clone() {
    return new FieldPosition(x, y);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    FieldPosition that = (FieldPosition) o;
    if (x != that.x) {
      return false;
    }
    return y == that.y;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }
}
