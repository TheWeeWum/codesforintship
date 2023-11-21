package ChessPieces;

import java.util.ArrayList;

public class King extends ChessPiece {
    public King(String team) {
        super(team);
        this.name = "K";
    }

    public String Name() {
        return "King";
    }
    public String toString() {
        if (this.team.equals("b")) {
            if (red) {
                return ANSI_RED + "♚" + ANSI_RESET + " ";
            } else {
                return "♚ ";
            }
        } else {
            if (red) {
                return ANSI_RED + "♔" + ANSI_RESET + " ";
            } else {
                return "♔ ";
            }
        }
    }

    public ArrayList<Integer> AllMoves(int square, ChessPiece[] board) {
        ArrayList<Integer> moves =  new ArrayList<>();
        if (square == 56) {
            if (this.TeamEquals(board[square-8]) != 1) {
                moves.add(square-8);
            }
            if (this.TeamEquals(board[square-7]) != 1) {
                moves.add(square-7);
            }
            if (this.TeamEquals(board[square+1]) != 1) {
                moves.add(square+1);
            }
        } else if (square == 0) {
            if (this.TeamEquals(board[square+1]) != 1) {
                moves.add(square+1);
            }
            if (this.TeamEquals(board[square+8]) != 1) {
                moves.add(square+8);
            }
            if (this.TeamEquals(board[square+9]) != 1) {
                moves.add(square+9);
            }
        } else if (square == 7) {
            if (this.TeamEquals(board[square-1]) != 1) {
                moves.add(square-1);
            }
            if (this.TeamEquals(board[square+8]) != 1) {
                moves.add(square+8);
            }
            if (this.TeamEquals(board[square+7]) != 1) {
                moves.add(square+7);
            }
        } else if (square == 63) {
            if (this.TeamEquals(board[square-1]) != 1) {
                moves.add(square-1);
            }
            if (this.TeamEquals(board[square-8]) != 1) {
                moves.add(square-8);
            }
            if (this.TeamEquals(board[square-9]) != 1) {
                moves.add(square-9);
            }
        } else if (square >= 57) {
            if (this.TeamEquals(board[square+1]) != 1) {
                moves.add(square+1);
            }
            if (this.TeamEquals(board[square-1]) != 1) {
                moves.add(square-1);
            }
            if (this.TeamEquals(board[square-7]) != 1) {
                moves.add(square-7);
            }
            if (this.TeamEquals(board[square-8]) != 1) {
                moves.add(square-8);
            }
            if (this.TeamEquals(board[square-9]) != 1) {
                moves.add(square-9);
            }
        } else if (square <= 6) {
            if (this.TeamEquals(board[square+1]) != 1) {
                moves.add(square+1);
            }
            if (this.TeamEquals(board[square-1]) != 1) {
                moves.add(square-1);
            }
            if (this.TeamEquals(board[square+7]) != 1) {
                moves.add(square+7);
            }
            if (this.TeamEquals(board[square+8]) != 1) {
                moves.add(square+8);
            }
            if (this.TeamEquals(board[square+9]) != 1) {
                moves.add(square+9);
            }
        } else if (square % 8 == 0) {
            if (this.TeamEquals(board[square+8]) != 1) {
                moves.add(square+8);
            }
            if (this.TeamEquals(board[square-8]) != 1) {
                moves.add(square-8);
            }
            if (this.TeamEquals(board[square-7]) != 1) {
                moves.add(square-7);
            }
            if (this.TeamEquals(board[square+1]) != 1) {
                moves.add(square+1);
            }
            if (this.TeamEquals(board[square+9]) != 1) {
                moves.add(square + 9);
            }
        } else if ((square + 1) % 8 == 0) {
            if (this.TeamEquals(board[square+8]) != 1) {
                moves.add(square+8);
            }
            if (this.TeamEquals(board[square-8]) != 1) {
                moves.add(square-8);
            }
            if (this.TeamEquals(board[square+7]) != 1) {
                moves.add(square+7);
            }
            if (this.TeamEquals(board[square-1]) != 1) {
                moves.add(square-1);
            }
            if (this.TeamEquals(board[square-9]) != 1) {
                moves.add(square-9);
            }
        } else {
            if (this.TeamEquals(board[square+1]) != 1) {
                moves.add(square+1);
            }
            if (this.TeamEquals(board[square-1]) != 1) {
                moves.add(square-1);
            }
            if (this.TeamEquals(board[square+7]) != 1) {
                moves.add(square+7);
            }
            if (this.TeamEquals(board[square-7]) != 1) {
                moves.add(square-7);
            }
            if (this.TeamEquals(board[square+8]) != 1) {
                moves.add(square+8);
            }
            if (this.TeamEquals(board[square-8]) != 1) {
                moves.add(square-8);
            }
            if (this.TeamEquals(board[square+9]) != 1) {
                moves.add(square+9);
            }
            if (this.TeamEquals(board[square-9]) != 1) {
                moves.add(square-9);
            }
        }
        return moves;
    }
}
