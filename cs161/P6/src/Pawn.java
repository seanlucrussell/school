import java.util.ArrayList;

public class Pawn extends GamePiece {

    public Pawn(String color, int row, int column) {
        super(color, row, column);
    }
    
    public Pawn(String color) {
        super(color);
    }
    
    @Override
    public ArrayList<String> legalMoves(GamePiece[][] board) {

        ArrayList<String> moves = new ArrayList<String>();
        int rowInc = color.equals("black") ? 1 : -1;

        // Add one move ahead if open.
        addIfOpen(board, column, row+rowInc, moves);
        // Add two moves ahead if pawn has not moved yet.
        if (color.equals("black") && row == 1)
            addIfOpen(board,column,row+2*rowInc, moves);
        if (color.equals("white") && row == 6)
            addIfOpen(board,column,row+2*rowInc, moves);

        // Add diagonal left-up and right-up moves if diagonal position contains an opponent.
        if (isOpponent(board,column-1,row+rowInc))
            moves.add(position(column-1,row+rowInc));
        if (isOpponent(board,column+1,row+rowInc))
            moves.add(position(column+1,row+rowInc));
        
        return moves;
    }

    // Add move toCol,toRow if position is open.
    private void addIfOpen(GamePiece[][] board, int toCol, int toRow, ArrayList<String> moves) {
        if (isOpen(board,toCol,toRow))
            moves.add(position(toCol,toRow));
    }

    @Override
    public String toString() {
        return color.equals("white") ? "\u2659" : "\u265F";
    }
}
