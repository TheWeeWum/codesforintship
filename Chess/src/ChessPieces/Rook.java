package ChessPieces;

import java.util.ArrayList;

public class Rook extends ChessPiece {
    public Rook(String team) {
        super(team);
        this.name = "R";
    }

    public String Name() {
        return "Rook";
    }

    public String toString() {
        if (this.team.equals("b")) {
            if (red) {
                return ANSI_RED + "♜" + ANSI_RESET + " ";
            } else {
                return "♜ ";
            }
        } else {
            if (red) {
                return ANSI_RED + "♖" + ANSI_RESET + " ";
            } else {
                return "♖ ";
            }
        }
    }

    public ArrayList<Integer> AllMoves(int square, ChessPiece[] board) {
        ArrayList<Integer> moves = new ArrayList<>();
        int square_i = square;
        square = square_i;
        while (square >= 8) {
            if (this.TeamEquals(board[square-8]) == 1) {
                break;
            }
            if (this.TeamEquals(board[square-8]) == -1) {
                moves.add(square - 8);
                break;
            }
            moves.add(square - 8);
            square -= 8;
        }
        square = square_i;
        while (square <= 63 - 8) {
            if (this.TeamEquals(board[square+8]) == 1) {
                break;
            }
            if (this.TeamEquals(board[square+8]) == -1) {
                moves.add(square + 8);
                break;
            }
            moves.add(square + 8);
            square += 8;
        }
        square = square_i;
        while (square - square % 8 <= square && square <= 7 + square - square % 8 - 1) {
            if (this.TeamEquals(board[square+1]) == 1) {
                break;
            }
            if (this.TeamEquals(board[square+1]) == -1) {
                moves.add(square + 1);
                break;
            }
            moves.add(square + 1);
            square++;
        }
        square = square_i;
        while (1 + square - square % 8 <= square && square <= 7 + square - square % 8) {
            if (this.TeamEquals(board[square-1]) == 1) {
                break;
            }
            if (this.TeamEquals(board[square-1]) == -1) {
                moves.add(square - 1);
                break;
            }
            moves.add(square - 1);
            square--;
        }
        return moves;
    }
}
