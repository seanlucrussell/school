import java.util.ArrayList;

public abstract class GamePiece {

    protected int row;
    protected int column;
    protected String color;

    public GamePiece(String colorArg, int rowArg, int columnArg) {
        color = colorArg;
        row = rowArg;
        column = columnArg;
    }
    
    public GamePiece(String colorArg) {
        color = colorArg;
        row = 0;
        column = 0;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getColor() {
        return color;
    }

    public void setRow(int r) {
        row = r;
    }

    public void setColumn(int c) {
        column = c;
    }

    protected String position(int c, int r) {
        return "abcdefghij".substring(c,c+1) + "123456789".substring(r,r+1);
    }

    public String getPosition() {
        return position(column,row);
    }

    public abstract ArrayList<String> legalMoves(GamePiece[][] board);

    // Other methods that are helpful in the chess piece classes.

    // Return true if column,row is on the board.
    protected boolean valid(int toCol, int toRow) {
        return toCol >= 0 && toCol < 8 && toRow >= 0 && toRow < 8;
    }

    // Return true if column,row does not have a piece.
    protected boolean isOpen(GamePiece[][] board, int toCol, int toRow) {
        if (!valid(toCol,toRow))
            return false;
        return board[toRow][toCol] == null;
    }

    // Return true if column,row has an opponent's piece.
    protected boolean isOpponent(GamePiece[][] board, int toCol, int toRow) {
        if (!valid(toCol,toRow))
            return false;
        GamePiece other = board[toRow][toCol];
        return other != null && ! color.equals(other.color);
    }

	
}
