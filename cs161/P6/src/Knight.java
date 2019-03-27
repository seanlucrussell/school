import java.util.ArrayList;

public class Knight extends GamePiece {

    public Knight(String color, int row, int column) {
        super(color, row, column);
    }
    
    public Knight(String color) {
        super(color);
    }

    @Override
    public ArrayList<String> legalMoves(GamePiece[][] board) {

        ArrayList<String> moves = new ArrayList<String>();

        int rowInc = color.equals("black") ? 1 : -1;


        // up 2, left 1
        checkOneMove(board, column - 1, row + 2*rowInc, moves);
        // up 1 left 2
        checkOneMove(board, column - 2, row + 1*rowInc, moves);
        // up 2, right 1
        checkOneMove(board, column + 1, row + 2*rowInc, moves);
        // up 1, right 2
        checkOneMove(board, column + 2, row + 1*rowInc, moves);
        // down 2, left 1
        checkOneMove(board, column - 1, row - 2*rowInc, moves);
        // down 1 left 2
        checkOneMove(board, column - 2, row - 1*rowInc, moves);
        // down 2, right 1
        checkOneMove(board, column + 1, row - 2*rowInc, moves);
        // down 1, right 2
        checkOneMove(board, column + 2, row - 1*rowInc, moves);

        return moves;
    }

    // Add toCol,toRow to moves if open or contains an opponent's piece.
    private void checkOneMove(GamePiece[][] board, int toCol, int toRow, ArrayList<String> moves) {
        if (isOpen(board,toCol,toRow) || isOpponent(board,toCol,toRow))
            moves.add(position(toCol,toRow));
    }

    @Override
    public String toString() {
        return color.equals("white") ? "\u2658" : "\u265E";
    }
}
