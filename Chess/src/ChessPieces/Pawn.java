package ChessPieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Pawn extends ChessPiece {
    public Pawn(String team) {
        super(team);
        this.name = "P";
    }

    public String Name() {
        return "Pawn";
    }

    public String toString() {
        if (this.team.equals("b")) {
            if (red) {
                return ANSI_RED + "♟" + ANSI_RESET + " ";
            } else {
                return "♟ ";
            }
        } else {
            if (red) {
                return ANSI_RED + "♙" + ANSI_RESET + " ";
            } else {
                return "♙ ";
            }
        }
    }

    public ArrayList<Integer> AllMoves(int square, ChessPiece[] board) {
        ArrayList<Integer> moves =  new ArrayList<>();
        if (this.team.equals("b")) {
            if (!board[square + 8].hasPiece && Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15).contains(square)) {
                moves.add(square + 16);
            }
            if (!board[square + 8].hasPiece) {
                moves.add(square + 8);
            }
            if (board[square + 7].team.equals("w") && square % 8 != 0) {
                moves.add(square + 7);
            }
            if (board[square + 9].team.equals("w") && (square+1) % 8 != 0) {
                moves.add(square + 9);
            }
        } else {
            if (!board[square - 8].hasPiece && Arrays.asList(48, 49, 50, 51, 52, 53, 54, 55).contains(square)) {
                moves.add(square - 16);
            }
            if (!board[square - 8].hasPiece) {
                moves.add(square - 8);
            }
            if (board[square - 7].team.equals("b") && (square+1) % 8 != 0) {
                moves.add(square - 7);
            }
            if (board[square - 9].team.equals("b") && square % 8 != 0) {
                moves.add(square - 9);
            }
        }
        System.out.println(moves);
        return moves;
    }
}
