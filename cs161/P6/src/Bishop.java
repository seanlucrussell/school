import java.util.ArrayList;

public class Bishop extends GamePiece {

    public Bishop(String colorArg, int rowArg, int columnArg) {
		super(colorArg, rowArg, columnArg);
	}
    
    public Bishop(String colorArg) {
		super(colorArg);
	}
    
	public ArrayList<String> legalMoves(GamePiece[][] board) {

        ArrayList<String> moves = new ArrayList<String>();

        // diagonal up-left
        oneDirection(board, -1, 1, moves);
        // diagonal up-right
        oneDirection(board, 1, 1, moves);
        // diagonal down-left
        oneDirection(board, -1, -1, moves);
        // diagonal down-right
        oneDirection(board, 1, -1, moves);
        
        return moves;
    }

    // Check in one direction from piece and add all legal moves.
    private void oneDirection(GamePiece[][] board, int colInc, int rowInc, ArrayList<String> moves) {
        for (int step=1; step < 8; step++) {
            int toCol = column + step*colInc;
            int toRow = row + step*rowInc;
            if (isOpen(board,toCol,toRow)) {
		// toCol,toRow is open, so add this move.
                moves.add(position(toCol,toRow));
            } else if (isOpponent(board,toCol,toRow)) {
		// toCol,toRow has an opponent, so add this move and stop looking in this direction.
                moves.add(position(toCol,toRow));
                break;
            } else
		// toCol,toRow has one of my pieces, so do not add this move and stop looking in this direciton.
                break;
        }
    }

    public String toString() {
        return color.equals("white") ? "\u2657" : "\u265D";
    }
}
