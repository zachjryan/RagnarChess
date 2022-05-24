import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Handler {

    public static Piece[][] board = new Piece[8][8]; // 8 x 8 corresponds to chess board


    /*public void initializeBoard() { // fill array with empty so array will exist
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Piece(i, j, 0, 0);
            }
        }
    }*/

    public static void loadBoard(){ // eventually call with array of piece and locations to allow variants/puzzles

        // an array with starting pieces
        Piece[] piecesStart = new Piece[32];
        piecesStart[0] = new Piece(0, 0, 2, 4);
        piecesStart[1] = new Piece(0, 1, 2, 2);
        piecesStart[2] = new Piece(0, 2, 2, 3);
        piecesStart[3] = new Piece(0, 3, 2, 5);
        piecesStart[4] = new Piece(0, 4, 2, 6);
        piecesStart[5] = new Piece(0, 5, 2, 3);
        piecesStart[6] = new Piece(0, 6, 2, 2);
        piecesStart[7] = new Piece(0, 7, 2, 4);
        piecesStart[8] = new Piece(1, 0, 2, 1);
        piecesStart[9] = new Piece(1, 1, 2, 1);
        piecesStart[10] = new Piece(1, 2, 2, 1);
        piecesStart[11] = new Piece(1, 3, 2, 1);
        piecesStart[12] = new Piece(1, 4, 2, 1);
        piecesStart[13] = new Piece(1, 5, 2, 1);
        piecesStart[14] = new Piece(1, 6, 2, 1);
        piecesStart[15] = new Piece(1, 7, 2, 1);
        piecesStart[16] = new Piece(6, 0, 1, 1);
        piecesStart[17] = new Piece(6, 1, 1, 1);
        piecesStart[18] = new Piece(6, 2, 1, 1);
        piecesStart[19] = new Piece(6, 3, 1, 1);
        piecesStart[20] = new Piece(6, 4, 1, 1);
        piecesStart[21] = new Piece(6, 5, 1, 1);
        piecesStart[22] = new Piece(6, 6, 1, 1);
        piecesStart[23] = new Piece(6, 7, 1, 1);
        piecesStart[24] = new Piece(7, 0, 1, 4);
        piecesStart[25] = new Piece(7, 1, 1, 2);
        piecesStart[26] = new Piece(7, 2, 1, 3);
        piecesStart[27] = new Piece(7, 3, 1, 5);
        piecesStart[28] = new Piece(7, 4, 1, 6);
        piecesStart[29] = new Piece(7, 5, 1, 3);
        piecesStart[30] = new Piece(7, 6, 1, 2);
        piecesStart[31] = new Piece(7, 7, 1, 4);

        for (Piece piece : piecesStart){ // loop will load board
            addPiece(piece);
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.println(board[i][j]);
            }
        }
    }



    public static void addPiece(Piece piece){
        board[piece.gety()][piece.getx()] = piece;
    }

    public static void removePiece(Piece piece){
        board[piece.gety()][piece.getx()] = null;
    }

    public static void movePiece(Piece piece, int x, int y){
        removePiece(piece);
        piece.setx(x);
        piece.sety(y);
        addPiece(piece);
        System.out.println("piece moved");
    }

    public boolean kingsAlive(){
        boolean blackKing = false;
        boolean whiteKing = false;
        for (Piece[] row : board){
            for (Piece piece : row){
                if (piece.team == 1 && piece.type == 6){
                    whiteKing = true;
                }
                if (piece.team == 2 && piece.type == 6){
                    blackKing = true;
                }
            }
        }
        return whiteKing && blackKing;
    }
}


