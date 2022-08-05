package net.apicode.snakegame.frame;

import java.awt.Rectangle;
import java.util.Random;
import java.util.function.Predicate;

public class GameFieldDisplay {

  private static final Random random = new Random();

  private final int maximumVerticalFields;
  private final int maximumHorizontalFields;
  private final Rectangle[][] fields;

  public GameFieldDisplay(Rectangle[][] fields) {
    this.fields = fields;
    maximumVerticalFields = fields.length;
    maximumHorizontalFields = fields[0].length;
  }

  public int getMaximumHorizontalFields() {
    return maximumHorizontalFields;
  }

  public int getMaximumVerticalFields() {
    return maximumVerticalFields;
  }

  public FieldPosition getRandomFieldPosition() {
    int randomColumn = random.nextInt(maximumHorizontalFields - 1) + 1;
    int randomRow = random.nextInt(maximumVerticalFields - 1) + 1;
    return new FieldPosition(randomColumn, randomRow);
  }

  public FieldPosition getRandomFieldPosition(Predicate<FieldPosition> filter) {
    FieldPosition randomFieldPosition;
    while (true) {
      randomFieldPosition = getRandomFieldPosition();
      if(filter.test(randomFieldPosition)) {
        break;
      }
    }
    return randomFieldPosition;
  }

  public Rectangle getRectangle(FieldPosition fieldPosition) {
    return getRectangle(fieldPosition.getColumn(), fieldPosition.getRow());
  }

  public Rectangle getRectangle(int column, int row) {
    int aIndex = row-1;
    int bIndex = column-1;
    if(aIndex < 0 || aIndex >= maximumVerticalFields) {
      throw new ArrayIndexOutOfBoundsException("Out of field maximum!");
    }
    if(bIndex < 0 || bIndex >= maximumHorizontalFields) {
      throw new ArrayIndexOutOfBoundsException("Out of field maximum!");
    }
    return fields[aIndex][bIndex];
  }

  public Rectangle[][] getField2DArray() {
    return fields;
  }


}
