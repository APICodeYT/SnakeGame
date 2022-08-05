package net.apicode.snakegame.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class GameData {

  private static final File file = new File("property.xml");

  private final Properties properties;
  private int highScore = 0;
  private long tickSpeedInMillis = 300;
  private int fieldSize = 30;

  public GameData() {
    this.properties = new Properties();
  }

  public void setHighScore(int highScore) {
    this.highScore = highScore;
  }

  public void setTickSpeedInMillis(long tickSpeedInMillis) {
    this.tickSpeedInMillis = tickSpeedInMillis;
  }

  public int getHighScore() {
    return highScore;
  }

  public long getTickSpeedInMillis() {
    return tickSpeedInMillis;
  }

  public int getFieldSize() {
    return fieldSize;
  }

  public void setFieldSize(int fieldSize) {
    this.fieldSize = fieldSize;
  }

  public boolean exists() {
    return file.exists();
  }

  public void load() {
    if(exists()) {
      try {
        properties.loadFromXML(new FileInputStream(file));
        tickSpeedInMillis = Long.parseLong(properties.getProperty("tickspeed-in-millis"));
        highScore = Integer.parseInt(properties.getProperty("highscore"));
        fieldSize = Integer.parseInt(properties.getProperty("field-size"));
      } catch (IOException e) {
        throw new RuntimeException("Failed to load data", e);
      }
    } else {
      save();
    }
  }

  public void saveAsync() {
    Thread thread = new Thread(this::save);
    thread.start();
  }

  public void save() {
    try {
      if(!exists()) {
        file.createNewFile();
      }
      properties.setProperty("tickspeed-in-millis", String.valueOf(tickSpeedInMillis));
      properties.setProperty("highscore", String.valueOf(highScore));
      properties.setProperty("field-size", String.valueOf(fieldSize));
      properties.storeToXML(new FileOutputStream(file), "Properties for snake game");
    } catch (IOException e) {
      throw new RuntimeException("Failed to save data", e);
    }
  }
}
