package ChessPieces;

import java.util.ArrayList;

public class ChessPiece {
    String name;
    String team;

    public final String ANSI_RESET = "\u001B[0m";
    public final String ANSI_RED = "\u001B[41m";
    public boolean red = false;
    public boolean hasPiece = false;

    public ChessPiece(String team) {
        this.team = team;
        this.hasPiece = true;
        this.name = "  ";
    }

    public ChessPiece() {
        this.team = "";
        this.name = "  ";
    }

    public String Name() {
        return "NoPiece";
    }

    public boolean ValidMove(int square1, int square2, ChessPiece[] board) {
        return AllMoves(square1, board).contains(square2);
    }
    public String toString() {
        if (red) {
            return ANSI_RED + " " + ANSI_RESET + " ";
        } else {
            return "  ";
        }
    }

    public ArrayList<Integer> AllMoves(int square, ChessPiece[] board) {
        return new ArrayList<>();
    }

    public int TeamEquals(ChessPiece other) {
        if (this.team.equals("w")) {
            if (other.team.equals("w")) {
                return 1;
            } else if (other.team.equals("b")) {
                return -1;
            } else {
                return 0;
            }
        } else {
            if (other.team.equals("b")) {
                return 1;
            } else if (other.team.equals("w")) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
