import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {

    Handler handler = new Handler();

    final int WIDTH = 700;
    final int HEIGHT = 1000;

    public GamePanel()  {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        handler.loadBoard();
        handler.initializeBoard();
        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH, HEIGHT);

        g.setColor(Color.white);
        int y;
        for (int i = 0; i < 8; i++){
            y = 150 + 75 * i;
            if (i == 0 || i == 2 || i == 4 || i == 6) {
                for (int j = 0; j < 4; j++) {
                    int x = 50 + 150 * j;
                    g.fillRect(x, y, 75, 75);
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    int x = 125 + 150 * j;
                    g.fillRect(x, y, 75, 75);
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece tempPiece = Handler.board[i][j];
                tempPiece.draw(g2d);
                //system.out.println(Handler.board[i][j].toString());
            }
        }
    }

}
