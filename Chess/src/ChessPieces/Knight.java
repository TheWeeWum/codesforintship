package ChessPieces;

import java.util.ArrayList;

public class Knight extends ChessPiece {
    public Knight(String team) {
        super(team);
        this.name = "N";
    }

    public String Name() {
        return "Knight";
    }

    public String toString() {
        if (this.team.equals("b")) {
            if (red) {
                return ANSI_RED + "♞" + ANSI_RESET + " ";
            } else {
                return "♞ ";
            }
        } else {
            if (red) {
                return ANSI_RED + "♘" + ANSI_RESET + " ";
            } else {
                return "♘ ";
            }
        }
    }

    public boolean ValidMove(int square1, int square2, ChessPiece[] board) {
        int diff = Math.abs(square2 - square1);
        if (board[square2].team.equals(this.team)) {
            return false;
        }
        return (diff == 6 || diff == 10 || diff == 15 || diff == 17);
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
