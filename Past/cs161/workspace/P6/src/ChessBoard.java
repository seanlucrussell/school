public class ChessBoard extends GameBoard{

   public int getRowFromPosition(String position) {
        return Integer.parseInt(position.substring(1)) -1;
    }

    public int getColumnFromPosition(String position) {
        return (int)position.charAt(0) - (int)'a';
    }


    public void initialize() {
        //Pawns
        for (int col=0; col<8; col++)
            placePiece(new Pawn("black"), "abcdefgh".charAt(col) + "2");
        for (int col=0; col<8; col++)
            placePiece(new Pawn("white"), "abcdefgh".charAt(col) + "7");
        //Rooks
        placePiece(new Rook("black"), "a1");
        placePiece(new Rook("black"), "h1");
        placePiece(new Rook("white"), "a8");
        placePiece(new Rook("white"), "h8");
        //Knights
        placePiece(new Knight("black"), "b1");
        placePiece(new Knight("black"), "g1");
        placePiece(new Knight("white"), "b8");
        placePiece(new Knight("white"), "g8");
        //Bishops
        placePiece(new Bishop("black"), "c1");
        placePiece(new Bishop("black"), "f1");
        placePiece(new Bishop("white"), "c8");
        placePiece(new Bishop("white"), "f8");
        //Royals
        placePiece(new King("black"), "d1");
        placePiece(new King("white"), "d8");
        placePiece(new Queen("black"), "e1");
        placePiece(new Queen("white"), "e8");
    }

    public boolean move(String fromPosition, String toPosition) {
        // System.out.println("MOVE " + fromPosition + " to " + toPosition);
        int fromCol = getColumnFromPosition(fromPosition);
        int fromRow = getRowFromPosition(fromPosition);
        GamePiece x = board[fromRow][fromCol];
        // System.out.println(x);
        // System.out.println(x.legalMoves(board));
        if (x.legalMoves(board).contains(toPosition)) {
            board[fromRow][fromCol] = null;
            int toCol = getColumnFromPosition(toPosition);
            int toRow = getRowFromPosition(toPosition);
            board[toRow][toCol] = x;
            x.setRow(toRow);
            x.setColumn(toCol);
            // System.out.println("moved " + x + "col,row= " + x.getColumn() + "," + x.getRow());
            return true;
        } else {
            return false;
        }
    }

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
