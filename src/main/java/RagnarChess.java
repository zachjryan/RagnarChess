import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RagnarChess {

    private static Handler handler = new Handler();

    // variables for game state

    private static int turn = 1; // 1 for white turn, 2 for black
    private static Piece selected = null; // when click piece will store it here (selected piece)

    public static void main(String[] args) {

        new GameFrame();

    }
}

