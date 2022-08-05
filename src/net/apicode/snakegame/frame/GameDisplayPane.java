package net.apicode.snakegame.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import net.apicode.snakegame.SnakeGame;
import net.apicode.snakegame.controller.Snake;
import net.apicode.snakegame.controller.SnakeDirection;
import net.apicode.snakegame.data.GameData;
import net.apicode.snakegame.scheduler.Scheduler;

public class GameDisplayPane extends JPanel implements PanelListener, KeyListener {

  public static final long TICK_DURATION = SnakeGame.getSnakeGame().getGameData().getTickSpeedInMillis();
  public static final int FIELD_PIXEL_SIZE =  SnakeGame.getSnakeGame().getGameData().getFieldSize();

  private final GameWindow gameWindow;
  private final Scheduler scheduler;
  private volatile GameFieldDisplay gameFieldDisplay;
  private final List<FieldPosition> mealFields = new ArrayList<>();

  private Snake snake;

  public GameDisplayPane(GameWindow gameWindow) {
    this.gameWindow = gameWindow;
    scheduler = Scheduler.createScheduler(this::tick, TICK_DURATION);
  }

  @Override
  public void paint(Graphics g) {
    int offset = 15;
    int width = getWidth()-offset*2;
    int height = getHeight()-offset*2;
    int maxHorizontalFields = width/FIELD_PIXEL_SIZE;
    int maxVerticalFields = height/FIELD_PIXEL_SIZE;

    Rectangle[][] rectangles = new Rectangle[maxVerticalFields][];
    for(int rowIndex = 0;rowIndex < maxVerticalFields;rowIndex++) {
      Rectangle[] array = new Rectangle[maxHorizontalFields];
      for(int columnIndex = 0; columnIndex < maxHorizontalFields;columnIndex++) {
        int x = offset + FIELD_PIXEL_SIZE * columnIndex;
        int y = offset + FIELD_PIXEL_SIZE * rowIndex;
        array[columnIndex] = new Rectangle(x, y, FIELD_PIXEL_SIZE, FIELD_PIXEL_SIZE);
      }
      rectangles[rowIndex] = array;
    }
    gameFieldDisplay = new GameFieldDisplay(rectangles);
    if(snake == null) {
      snake = new Snake(new FieldPosition(maxHorizontalFields/2, maxVerticalFields/2));
      addMealField();
    }

    Graphics2D graphics = (Graphics2D) g;
    graphics.setColor(new Color(46, 46, 46));
    graphics.fillRect(0, 0, getWidth(), getHeight());

    //draw frame
    graphics.setColor(Color.WHITE);
    graphics.drawRect(offset, offset, maxHorizontalFields * FIELD_PIXEL_SIZE,
        maxVerticalFields * FIELD_PIXEL_SIZE);
    /*
    Drawing field mask

    for (Rectangle[] array1 : gameFieldDisplay.getField2DArray()) {
      for (Rectangle rectangle : array1) {
        graphics.draw(rectangle);
      }
    }*/


    Rectangle snakeHeadRectangle = gameFieldDisplay.getRectangle(snake.getPosition());
    graphics.setColor(Color.GREEN);
    graphics.fillRect(snakeHeadRectangle.x, snakeHeadRectangle.y, snakeHeadRectangle.width, snakeHeadRectangle.height);
    graphics.setColor(Color.RED);
    graphics.draw(snakeHeadRectangle);

    for (FieldPosition mealField : new ArrayList<>(mealFields)) {
      Rectangle rectangle = gameFieldDisplay.getRectangle(mealField);
      drawImage(graphics, "apple.png", rectangle.x, rectangle.y);
    }
    for (FieldPosition bodyPosition : snake.getBodyPositions()) {
      Rectangle snakeBodyRectangle = gameFieldDisplay.getRectangle(bodyPosition);
      graphics.setColor(Color.GREEN);
      graphics.fillRect(snakeBodyRectangle.x, snakeBodyRectangle.y, snakeBodyRectangle.width, snakeBodyRectangle.height);
    }
  }

  public GameFieldDisplay getGameFieldDisplay() {
    return gameFieldDisplay;
  }

  protected void tick() {
    snake.updateMovement();
    if(mealFields.contains(snake.getPosition())) {
      mealFields.remove(snake.getPosition());
      snake.addGrowProcess();
    }
    if(!snake.isPositionValid(gameFieldDisplay.getMaximumHorizontalFields(),
        gameFieldDisplay.getMaximumVerticalFields())) {
      Toolkit.getDefaultToolkit().beep();
      GameData gameData = SnakeGame.getSnakeGame().getGameData();
      if(snake.getSize() > gameData.getHighScore()) {
        gameData.setHighScore(snake.getSize());
        gameData.saveAsync();
      }
      gameWindow.showStartMenu();
      return;
    }
    if(snake.getMaximumMealFields() > mealFields.size()) {
      if(isRate(25D)) {
        addMealField();
      }
    }
    repaint();
  }

  private void addMealField() {
    FieldPosition randomFieldPosition = gameFieldDisplay.getRandomFieldPosition(
        pos -> !snake.getPosition().equals(pos)
            && !snake.getBodyPositions().contains(pos));
    mealFields.add(randomFieldPosition);
  }


  public void drawImage(Graphics2D graphics, String name, int x, int y) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(getClass().getClassLoader().getResource(name));
    } catch (IOException e) {
      e.printStackTrace();
    }
    graphics.drawImage(image, x, y, FIELD_PIXEL_SIZE, FIELD_PIXEL_SIZE, null);
  }

  @Override
  public void load(GameWindow window) {
    scheduler.start(1000L);
    window.addKeyListener(this);
  }

  @Override
  public void unload(GameWindow window) {
    window.removeKeyListener(this);
    scheduler.stop();
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
      snake.setDirection(SnakeDirection.UP);
    } else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
      snake.setDirection(SnakeDirection.LEFT);
    } else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
      snake.setDirection(SnakeDirection.DOWN);
    } else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
      snake.setDirection(SnakeDirection.RIGHT);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  private boolean isRate(double percentage) {
    double randomNum = ThreadLocalRandom.current().nextInt(10000);
    double randomPercentage = randomNum / 100;
    return percentage >= randomPercentage;
  }
}
