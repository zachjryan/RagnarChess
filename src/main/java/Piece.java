import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Piece {

    protected int y, x; // will correspond to board location
    protected int team; // 0 for no team/empty, 1 = white, 2 = black
    protected int type; // piece type
    /*
    * 0 = empty
    * 1 = pawn
    * 2 = knight
    * 3 = bishop
    * 4 = rook
    * 5 = queen
    * 6 = king
    */
    public BufferedImage pieceImage;

    public Piece(int y, int x, int team, int type) {
        this.y = y;
        this.x = x;
        this.team = team;
        this.type = type;
    }

    public void getPieceImage(){
        try {
            // use type to add piece image
            if (team == 1) {
                if (type == 1) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("whitePawn.png")));
                } else if (type == 2) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("WhiteHorse.png")));
                } else if (type == 3) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("WhiteBishop.png")));
                } else if (type == 4) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("WhiteRook.png")));
                } else if (type == 5) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("whitePawn.png")));
                } else if (type == 6) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("whitePawn.png")));
                }
            }
            if (team == 2) {
                if (type == 1) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("blackPawn2.png")));
                } else if (type == 2) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("BlackHorse.png")));
                } else if (type == 3) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("blackBishop.png")));
                } else if (type == 4) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("BlackRook.png")));
                } else if (type == 5) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("blackQueen.png")));
                } else if (type == 6) {
                    pieceImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("blackKing.png")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d){
        if (pieceImage == null)
            getPieceImage();
        int xPixels = 50 + x * 75;
        int yPixels = 150 + y * 75;
        g2d.drawImage(pieceImage, xPixels, yPixels, 75, 75, null);
    }

    public void sety(int y){
        this.y = y;
    }
    public void setx(int x){
        this.x = x;
    }
    public int gety(){
        return y;
    }
    public int getx(){ return x; }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return type;
    }
    public int getTeam() { return team; }

    @Override
    public String toString() {
        return "Piece{" +
                "y=" + y +
                ", x=" + x +
                ", team=" + team +
                ", type='" + type + '\'' +
                ", image=" + pieceImage +
                '}';
    }
}
