public abstract class GameBoard {

    protected GamePiece[][] board;

    public GameBoard() {
        board = new GamePiece[8][8];
        initialize();
    }

    // Useful for testing.
    public GamePiece[][] getBoard() {
	return board;
    }

    public int getRowFromPosition(String position) {
        return Integer.parseInt(position.substring(1)) -1;
    }

    public int getColumnFromPosition(String position) {
        return (int)position.charAt(0) - (int)'a';
    }

    public boolean placePiece(GamePiece piece, String position) {
        int column = getColumnFromPosition(position);
        int row = getRowFromPosition(position);

        if (board[row][column] != null)
            return false;
        piece.setRow(row);
        piece.setColumn(column);
        board[row][column] = piece;
        return true;
    }

    public GamePiece getPiece(String position) {
        int col = getColumnFromPosition(position);
        int row = getRowFromPosition(position);
        return board[row][col];
    }

    public abstract void initialize();

    public abstract boolean move(String fromPosition, String toPosition);

    public String toString() {
        String result = "";
        for (int row = 8-1; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                GamePiece x = board[row][col];
                if (x == null)
                    result += "   ";
                else
                    result += " " + x + " ";  //calls toString
            }
            result += "\n";
        }
        return result;
    }


}
