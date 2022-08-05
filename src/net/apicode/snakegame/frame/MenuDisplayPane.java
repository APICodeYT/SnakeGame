package net.apicode.snakegame.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import net.apicode.snakegame.SnakeGame;

public class MenuDisplayPane extends JPanel implements PanelListener {

  private Rectangle buttonRect;
  private final GameWindow gameWindow;

  public MenuDisplayPane(GameWindow gameWindow) {
    this.gameWindow = gameWindow;
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D graphics = (Graphics2D) g;

    //add render hints
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    //draw background
    graphics.setColor(Color.BLACK);
    graphics.fillRect(0, 0, getWidth(), getHeight());

    //draw frame
    graphics.setColor(Color.WHITE);
    drawFrame(graphics);

    //draw highscore
    graphics.setFont(new Font("Consolas", Font.PLAIN, 30));
    drawText(graphics, String.format("Highscore: %d", SnakeGame.getSnakeGame().getGameData().getHighScore()),
        getWidth()/2, getHeight()/3);

    //draw button
    buttonRect = drawButton(graphics, "PLAY", getWidth()/2, (int) (getHeight()/1.5));
  }

  protected void handleMouseEvent(MouseEvent e) {
    if(buttonRect != null) {
      if(buttonRect.contains(e.getPoint())) {
        gameWindow.startGame();
      }
    }
  }

  private Rectangle drawButton(Graphics2D graphics, String text, int x, int y) {
    int space = 20;
    Rectangle bounds = drawText(graphics, text, x, y);

    int posX = (int) (bounds.getX()-space);
    int posY = (int) (bounds.getY()-space);

    int width = (int) (bounds.getWidth() + space*2);
    int height = (int) (bounds.getHeight() + space*2);

    graphics.drawRect(posX, posY, width, height);
    return new Rectangle(posX, posY, width, height);
  }


  private Rectangle drawText(Graphics2D graphics, String text, int x, int y) {
    FontRenderContext ctx = graphics.getFontRenderContext();
    GlyphVector glyphVector = graphics.getFont().createGlyphVector(ctx, text);
    Rectangle2D bounds = glyphVector.getVisualBounds();
    int textWidth = (int) bounds.getWidth();
    int textHeight = (int) bounds.getHeight();
    int posX = x - textWidth/2;
    int posY = y + textHeight/2;
    graphics.drawString(text, posX, posY);
    return glyphVector.getPixelBounds(null, x - (int) ((double)textWidth/2),
        y + (int) ((double)textHeight/2));
  }

  private void drawFrame(Graphics2D graphics) {
    int offset = 10;
    graphics.drawRoundRect(offset, offset, getWidth()-offset*2, getHeight()-offset*2,
        20, 20);
  }

  @Override
  public void load(GameWindow window) {
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        handleMouseEvent(e);
      }
    });
  }

  @Override
  public void unload(GameWindow window) {

  }
}
