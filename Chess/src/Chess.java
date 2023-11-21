import ChessPieces.*;

import java.util.ArrayList;

public class Chess {
    ChessPiece[] board;

    Chess() {
        this.board = generateBoard();
    }

    public static ChessPiece[] generateBoard() {
        return new ChessPiece[]{
                new Rook("b"),new Knight("b"),new Bishop("b"),new Queen("b"),new King("b"),new Bishop("b"),new Knight("b"),new Rook("b"),                                       // a8 b8 c8 d8 e8 f8 g8 h8
                new Pawn("b"),new Pawn("b"),new Pawn("b"),new Pawn("b"),new Pawn("b"),new Pawn("b"),new Pawn("b"),new Pawn("b"),   // a7 b7 c7 d7 e7 f7 g7 h8
                new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),                        // a6 b6 c6 d6 e6 f6 g6 h6
                new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),                        // a5 b5 c5 d5 e5 f5 g5 h5
                new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),                        // a4 b4 c4 d4 e4 f4 g4 h4
                new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),new ChessPiece(),                        // a3 b3 c3 d3 e3 f3 g3 h3
                new Pawn("w"),new Pawn("w"),new Pawn("w"),new Pawn("w"),new Pawn("w"),new Pawn("w"),new Pawn("w"),new Pawn("w"),   // a2 b2 c2 d2 e2 f2 g2 h2
                new Rook("w"),new Knight("w"),new Bishop("w"),new Queen("w"),new King("w"),new Bishop("w"),new Knight("w"),new Rook("w")                                        // a1 b1 c1 d1 e1 f1 g1 h1
        };
    }

    public String makeMove(String move) {
        String sq1 = move.substring(0, 2);
        String sq2 = move.substring(2, 4);

        int pos1 = getArrayPositionFromString(sq1);
        int pos2 = getArrayPositionFromString(sq2);

        if (!this.board[pos1].hasPiece) {
            return "NoPiece";
        }

        boolean valid_piece_move = this.board[pos1].AllMoves(pos1, board).contains(pos2);

        if (!valid_piece_move) {
            return "The " + this.board[pos1].Name() + " can not move like that.";
        }

        this.board[pos2] = this.board[pos1];
        this.board[pos1] = new ChessPiece();

        return "";
    }

    private static int getArrayPositionFromString(String str) {
        int pos = 0;
        switch (str.charAt(0)) {
            case 'a' -> pos += 0;
            case 'b' -> pos += 1;
            case 'c' -> pos += 2;
            case 'd' -> pos += 3;
            case 'e' -> pos += 4;
            case 'f' -> pos += 5;
            case 'g' -> pos += 6;
            case 'h' -> pos += 7;
        }

        switch (str.charAt(1)) {
            case '1' -> pos += 56;
            case '2' -> pos += 48;
            case '3' -> pos += 40;
            case '4' -> pos += 32;
            case '5' -> pos += 24;
            case '6' -> pos += 16;
            case '7' -> pos += 8;
            case '8' -> {}
        }
        return pos;
    }

    public String toString() {
        StringBuilder str = new StringBuilder("   _____________________\n8  |");
        for (int i = 0; i < 64; i++) {
            if (i == 8) {
                str.append("7  |");
            } else if (i == 16) {
                str.append("6  |");
            } else if (i == 24) {
                str.append("5  |");
            } else if (i == 32) {
                str.append("4  |");
            } else if (i == 40) {
                str.append("3  |");
            } else if (i == 48) {
                str.append("2  |");
            } else if (i == 56) {
                str.append("1  |");
            }
            str.append(this.board[i]);
            if ((i + 1) % 8 == 0) {
                str.append("|\n");
            }
        }
        str.append("   ￣￣￣￣￣￣￣￣￣￣￣￣￣\n");
        str.append("    a b c d e f g h");
        return str.toString();
    }

    public void showAvailableSquares(String move) {
        int square = getArrayPositionFromString(move);
        ArrayList<Integer> allMoves = this.board[square].AllMoves(square, board);

        for (int i : allMoves) {
            this.board[i].red = true;
        }
    }

    public void RefreshColors() {
        for (int i = 0; i < 63; i++) {
            this.board[i].red = false;
        }
    }
}
