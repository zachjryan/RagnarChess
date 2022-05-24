import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel {

    Handler handler = new Handler();

    // variables for game state

    private static int turn = 1; // 1 for white turn, 2 for black
    private static Piece selected = null; // when click piece will store it here (selected piece)

    final int WIDTH = 700;
    final int HEIGHT = 1000;

    public GamePanel()  {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addMouseListener(new MyMouseListener());
        startGame();
    }

    public void startGame(){
        Handler.loadBoard();
    }

    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH, HEIGHT);

        g.setColor(Color.white);
        int y;
        int x;
        for (int i = 0; i < 8; i++){
            y = 150 + 75 * i;
            if (i == 0 || i == 2 || i == 4 || i == 6) {
                for (int j = 0; j < 4; j++) {
                    x = 50 + 150 * j;
                    g.fillRect(x, y, 75, 75);
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    x = 125 + 150 * j;
                    g.fillRect(x, y, 75, 75);
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Handler.board[i][j] != null) {
                    Piece tempPiece = Handler.board[i][j];
                    tempPiece.draw(g2d);
                }
                //system.out.println(Handler.board[i][j].toString());
            }
        }
    }

    public class MyMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int boardX = (int) Math.floor((x - 58)/ 75.0); // find spot on board - start 50 (+8, not sure why) pixels to right 75 pixel square size
            int boardY = (int) Math.floor((y - 180)/ 75.0); // subtract additional 30 (50+30=180) for top of window
            //System.out.println(x + " " + y);
            System.out.println(boardX + " " + boardY);

            if (boardX >= 0 && boardX < 8 && boardY >= 0 && boardY < 8) { // click within board boundaries then proceed
                clickedSquare(boardY, boardX);
            } else { // if outside board make selected null
                selected = null;
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    public void clickedSquare(int y, int x){
        if (selected == null){ // no piece selected, select piece if one in square clicked.
            System.out.println(Handler.board[y][x]);
            if (Handler.board[y][x].getTeam() == turn){ // ensure clicked piece matches team of turn
                selected = Handler.board[y][x]; // if match store piece in selected
                System.out.println(selected);
            }
        } else if (selected.getx() == x && selected.gety() == y) { // clicked on selected piece
            selected = null; // deselect it
        } else {
            // test if click in valid spot to move to based on selected piece
            if (validMove(y,x, selected.getType())){
                // valid, move piece and change turn
                System.out.println("valid Line true");
                handler.movePiece(selected, x, y);
                repaint();
                if (turn == 1) {
                    turn = 2;
                } else {
                    turn = 1;
                }
            } else { // not a valid move
                Piece tempPiece = Handler.board[y][x]; // piece in square clicked of invalid move
                if (tempPiece == null) { // invalid move on empty square set selected to null
                    selected = null;
                } else if (tempPiece.getTeam() == turn) { // clicked on piece of same team, set to selected
                    selected = tempPiece;
                } else {
                    selected = null;
                }
            }
        }
    }

    public boolean validMove(int y, int x, int pieceType) {
        System.out.println("validMove called");
        // save stuff about selected piece to variable
        int pieceX = selected.getx();
        int pieceY = selected.gety();
        // difference between click and piece location
        int yDif = y - pieceY;
        int xDif = x - pieceX;
        // variable for direction, neg or pos direction as -1 or +1
        int xDir;
        int yDir;
        if (xDif != 0) {
            xDir = xDif / Math.abs(xDif);
        } else {
            xDir = 1;
        }
        if (yDif != 0) {
            yDir = yDif / Math.abs(yDif);
        } else {
            yDir = 1;
        }

        if (pieceType == 1) { // pawn
            // TODO figure out the french move
            // TODO simplify with new dif and dir variables
            if (Handler.board[y][x] == null) { // no piece in clicked square so only straight forward move
                if (turn == 1) { // white team
                    if (pieceX == x) { // empty selected so can only move straight forward
                        if (y == pieceY - 1) { // forward one space is selected, valid move
                            return true;
                        } else if (y == pieceY - 2) { // can move 2 forward only if not moved yet
                            if (pieceY == 6) { // 6 starting row for white pawns
                                // ensure nothing blocking it from moving
                                return Handler.board[5][x] == null;
                            } else {
                                return false;
                            }
                        } else { // not a valid move within same column
                            return false;
                        }
                    } else { // empty space not in same column means pawn can't go there
                        return false;
                    }
                } else { // black team
                    if (pieceX == x) { // empty selected so can only move straight forward
                        if (y == pieceY + 1) { // forward one space is selected, valid move
                            return true;
                        } else if (y == pieceY + 2) { // can move 2 forward only if not moved yet
                            if (pieceY == 1) { // 1 is starting row for black pawns
                                // ensure nothing blocking it from moving
                                // something in the way
                                return Handler.board[2][x] == null;
                            } else { // not on starting row
                                return false;
                            }
                        } else { // not a valid move within same column
                            return false;
                        }
                    } else { // empty space not in same column means pawn can't go there
                        return false;
                    }
                }
            } else { // not empty square check if pawn can take
                if (Handler.board[y][x].getTeam() != turn) { // means piece in clicked square is on other team
                    if (turn == 1){ // white team
                        if (y == pieceY - 1 && (x == pieceX + 1 || x == pieceX - 1)){ // pawn takes forward and to side 1 spot
                            return true;
                        } else {
                            return false;
                        }
                    } else { // black team
                        if (y == pieceY + 1 && (x == pieceX + 1 || x == pieceX - 1)){ // pawn takes forward and to side 1 spot
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else { // clicked on piece of same team, can't move there
                    return false;
                }
            }

        } else if (pieceType == 2) { // knight
            if ((y == pieceY + 2 && (x == pieceX - 1 || x == pieceX + 1)) ||
                    (y == pieceY + 1 && (x == pieceX + 2 || x == pieceX - 2)) ||
                    (y == pieceY - 1 && (x == pieceX + 2 || x == pieceX - 2)) ||
                    (y == pieceY - 2 && (x == pieceX - 1 || x == pieceX + 1))) { // all possible horse moves
                if (Handler.board[y][x] == null) { // empty spot, can move
                    return true;
                } else if (Handler.board[y][x].getTeam() != turn) { // not empty so must be a piece, test if on other team
                    return true;
                } else { // clicked on  piece of same team
                    return false;
                }
            } else { // invalid move
                return false;
            }

        } else if (pieceType == 3) { // bishop
            if (Math.abs(yDif) == Math.abs(xDif)){ // if click diagonal from piece location
                // check for unobstructed path
                int i = 0;
                // variable to hold piece in spots checking on bishop path
                Piece tempPiece = null;
                // loop to check bishop path unobstructed
                while (i < Math.abs(xDif) && tempPiece == null){
                    tempPiece = Handler.board[pieceY + ((i + 1) * yDir)][pieceX + ((i + 1) * xDir)]; // 1 space along bishop path
                    i++;
                }
                if (tempPiece == null){ // clear path, can move
                    return true;
                } else if (i == Math.abs(xDif)) { // spot not empty but at end of bishop path, check if can take
                    if (tempPiece.getTeam() != turn) { // clicked piece on other team (can take)
                        return true;
                    } else { // same team, cannot take
                        return false;
                    }
                } else { // path was blocked
                    return false;
                }
            } else { // click not diagonal from bishop
                return false;
            }

        } else if (pieceType == 4) { // rook
            // variable for counting while checking rooks path
            int i = 0;
            // variable to hold piece in spots checking on rook path
            Piece tempPiece = null;
            if (xDif != 0 && yDif == 0) { // move along a row
                // loop to check rook path unobstructed
                while (i <= Math.abs(xDif) && tempPiece == null){
                    tempPiece = Handler.board[y][pieceX + (xDir * (i + 1))]; // 1 space along rook path
                    i++;
                }
                if (tempPiece == null){ // clear path, can move
                    return true;
                } else if (i == Math.abs(xDif)) { // spot not empty but at end of rook path, check if can take
                    if (tempPiece.getTeam() != turn) { // clicked piece on other team (can take)
                        return true;
                    } else { // same team, cannot take
                        return false;
                    }
                } else { // path was blocked
                    return false;
                }
            } else if (yDif != 0 && xDif == 0) { // move along a column
                // check for unobstructed path
                while (i <= Math.abs(yDif) && tempPiece == null){
                    tempPiece = Handler.board[pieceY + (yDir * (i + 1))][x]; // 1 space along rook path
                    i++;
                }
                if (tempPiece == null){ // clear path, can move
                    return true;
                } else if (i == Math.abs(yDif)) { // spot not empty but at end of rook path, check if can take
                    if (tempPiece.getTeam() != turn) { // clicked piece on other team (can take)
                        return true;
                    } else { // same team, cannot take
                        return false;
                    }
                } else { // path was blocked
                    return false;
                }
            } else { // not valid move
                return false;
            }

        } else if (pieceType == 5) { // queen
            // can move like rook of bishop thus
            if (validMove(y, x, 3) || validMove(y, x, 4)){
                return true;
            } else {
                return false;
            }

        } else if (pieceType == 6) { // king
            if (Math.abs(xDif) > 1 || Math.abs(yDif) > 1) { // can only move one space
                return false;
            } else {
                Piece tempPiece = Handler.board[y][x];
                if (tempPiece == null){ // empty space, can move
                    return true;
                } else if (tempPiece.getTeam() != turn){ // enemy piece, can move
                    return true;
                } else { // team piece, can't move
                    return false;
                }
            }
        } else { // not valid piece type, won't happen but needed to prevent error
            return false;
        }
    }
}
