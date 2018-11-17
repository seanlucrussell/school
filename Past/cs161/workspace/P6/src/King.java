import java.util.ArrayList;

public class King extends GamePiece {

    public King(String colorArg, int rowArg, int columnArg) {
		super(colorArg, rowArg, columnArg);
	}
    
    public King(String colorArg) {
		super(colorArg);
	}
    
	public ArrayList<String> legalMoves(GamePiece[][] board) {
        // Check all neighboring 8 positions
        ArrayList<String> moves = new ArrayList<String>();

        int toCol = 0;
        int toRow = 0;

        // left
        toCol = column-1; toRow = row;
        if (isOpen(board,toCol,toRow) || isOpponent(board, toCol, toRow))
            moves.add(position(toCol,toRow));

        // right
        toCol = column+1; toRow = row;
        if (isOpen(board,toCol,toRow) || isOpponent(board, toCol, toRow))
            moves.add(position(toCol,toRow));

        // down
        toCol = column; toRow = row-1;
        if (isOpen(board,toCol,toRow) || isOpponent(board, toCol, toRow))
            moves.add(position(toCol,toRow));

        // up
        toCol = column; toRow = row+1;
        if (isOpen(board,toCol,toRow) || isOpponent(board, toCol, toRow))
            moves.add(position(toCol,toRow));

        // left, down
        toCol = column-1; toRow = row-1;
        if (isOpen(board,toCol,toRow) || isOpponent(board, toCol, toRow))
            moves.add(position(toCol,toRow));

        // right, up
        toCol = column+1; toRow = row+1;
        if (isOpen(board,toCol,toRow) || isOpponent(board, toCol, toRow))
            moves.add(position(toCol,toRow));

	// left, up
        toCol = column-1; toRow = row+1;
        if (isOpen(board,toCol,toRow) || isOpponent(board, toCol, toRow))
            moves.add(position(toCol,toRow));

	// right, down
        toCol = column+1; toRow = row-1;
        if (isOpen(board,toCol,toRow) || isOpponent(board, toCol, toRow))
            moves.add(position(toCol,toRow));
        
        return moves;
    }

    @Override
    public String toString() {
        return color.equals("white") ? "\u2654" : "\u265A";
    }
}
