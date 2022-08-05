package net.apicode.snakegame.frame;

import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {

  public GameWindow() {
    super("Snake-Game");
    setSize(600, 500);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  @Override
  public void setContentPane(Container contentPane) {
    throw new UnsupportedOperationException();
  }

  private void setPane(JPanel pane) {
    Container contentPane = getContentPane();
    if(contentPane != null) {
      if(contentPane instanceof PanelListener listener) {
        listener.unload(this);
      }
    }
    super.setContentPane(pane);

    revalidate();
    repaint();
    if(pane instanceof PanelListener listener) {
      listener.load(this);
    }
  }


  public void showStartMenu() {
    MenuDisplayPane menuDisplayPane = new MenuDisplayPane(this);
    setPane(menuDisplayPane);
  }

  public void startGame() {
    GameDisplayPane gameDisplayPane = new GameDisplayPane(this);
    setPane(gameDisplayPane);
  }

}
