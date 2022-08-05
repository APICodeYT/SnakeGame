package net.apicode.snakegame;

import net.apicode.snakegame.data.GameData;
import net.apicode.snakegame.frame.GameWindow;

public class SnakeGame {

  private static SnakeGame snakeGame;

  private final GameData gameData;
  private final GameWindow gameWindow;

  public SnakeGame() {
    snakeGame = this;
    gameData = new GameData();
    gameData.load();
    gameWindow = new GameWindow();
    gameWindow.setVisible(true);
    gameWindow.showStartMenu();
  }

  public GameData getGameData() {
    return gameData;
  }

  public GameWindow getGameWindow() {
    return gameWindow;
  }

  public static SnakeGame getSnakeGame() {
    return snakeGame;
  }
}
