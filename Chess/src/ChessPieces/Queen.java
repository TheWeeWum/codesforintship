package ChessPieces;

import java.util.ArrayList;

public class Queen extends ChessPiece {
    public Queen(String team) {
        super(team);
        this.name = "Q";
    }

    public String Name() {
        return "Queen";
    }

    public String toString() {
        if (this.team.equals("b")) {
            if (red) {
                return ANSI_RED + "♛" + ANSI_RESET + " ";
            } else {
                return "♛ ";
            }
        } else {
            if (red) {
                return ANSI_RED + "♕" + ANSI_RESET + " ";
            } else {
                return "♕ ";
            }
        }
    }

    public boolean ValidMove(int square1, int square2, ChessPiece[] board) {
        int diff = (square2 - square1);
        if (board[square2].team.equals(this.team)) {
            return false;
        }
        int max = (square1 % 8) + square1;
        boolean rookMove = ((0 <= max - square2) && (max - square2 < 8));
        boolean bishopMove = (diff % 7 == 0 || diff % 9 == 0);
        return rookMove || bishopMove || diff % 8 == 0;
    }

    public ArrayList<Integer> AllMoves(int square, ChessPiece[] board) {
        ArrayList<Integer> moves =  new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            if (ValidMove(square, i, board)) {
                moves.add(i);
            }
        }
        return moves;
    }
}
