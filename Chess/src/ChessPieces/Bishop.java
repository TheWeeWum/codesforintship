package ChessPieces;

import java.util.ArrayList;

public class Bishop extends ChessPiece {
    public Bishop(String team) {
        super(team);
        this.name = "B";
    }

    public String Name() {
        return "Bishop";
    }

    public String toString() {
        if (this.team.equals("b")) {
            if (red) {
                return ANSI_RED + "♝" + ANSI_RESET + " ";
            } else {
                return "♝ ";
            }
        } else {
            if (red) {
                return ANSI_RED + "♗" + ANSI_RESET + " ";
            } else {
                return "♗ ";
            }
        }
    }

    public ArrayList<Integer> AllMoves(int square, ChessPiece[] board) {
        ArrayList<Integer> moves =  new ArrayList<>();
        int square_i = square;
        if (this.team.equals("w")) {
            // up and right
            square = square_i;
            while (7 <= square && square <= 63) {
                if ((square + 1) % 8 == 0 || board[square-7].team.equals("w")) {
                    break;
                }
                if (board[square - 7].team.equals("b")) {
                    moves.add(square - 7);
                    break;
                }
                moves.add(square - 7);
                square -= 7;
            }
            // down and left
            square = square_i;
            while (0 <= square && square <= 63-7) {
                if (square % 8 == 0 || board[square+7].team.equals("w")) {
                    break;
                }
                if (board[square + 7].team.equals("b")) {
                    moves.add(square + 7);
                    break;
                }
                moves.add(square + 7);
                square += 7;
            }
            // up and left
            square = square_i;
            while (9 <= square && square <= 63) {
                if (square % 8 == 0 || board[square-9].team.equals("w")) {
                    break;
                }
                if (board[square - 9].team.equals("b")) {
                    moves.add(square - 9);
                    break;
                }
                moves.add(square - 9);
                square -= 9;
            }
            // down and right
            square = square_i;
            while (0 <= square && square <= 63-9) {
                if ((square + 1) % 8 == 0 || board[square + 9].team.equals("w")) {
                    break;
                }
                if (board[square + 9].team.equals("b")) {
                    moves.add(square + 9);
                    break;
                }
                moves.add(square + 9);
                square += 9;
            }
        } else {
            // up and right
            square = square_i;
            while (7 <= square && square <= 63) {
                if ((square + 1) % 8 == 0 || board[square - 7].team.equals("b")) {
                    break;
                }
                if (board[square - 7].team.equals("w")) {
                    moves.add(square - 7);
                    break;
                }
                moves.add(square - 7);
                square -= 7;
            }
            // down and left
            square = square_i;
            while (0 <= square && square <= 63 - 7) {
                if (square % 8 == 0 || board[square + 7].team.equals("b")) {
                    break;
                }
                if (board[square + 7].team.equals("w")) {
                    moves.add(square + 7);
                    break;
                }
                moves.add(square + 7);
                square += 7;
            }
            // up and left
            square = square_i;
            while (9 <= square && square <= 63) {
                if (square % 8 == 0 || board[square - 9].team.equals("b")) {
                    break;
                }
                if (board[square - 9].team.equals("w")) {
                    moves.add(square - 9);
                    break;
                }
                moves.add(square - 9);
                square -= 9;
            }
            // down and right
            square = square_i;
            while (0 <= square && square <= 63 - 9) {
                if ((square + 1) % 8 == 0 || board[square + 9].team.equals("b")) {
                    break;
                }
                if (board[square + 9].team.equals("w")) {
                    moves.add(square + 9);
                    break;
                }
                moves.add(square + 9);
                square += 9;
            }
        }
        return moves;
    }
}
