import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Chess c = new Chess();
        System.out.println(c);

        Scanner scan = new Scanner(System.in);
        String move = "";
        String moveTo = "";
        while (true) {
            // ask player which piece to move
            System.out.println("Which piece would you like to move");
            move = scan.nextLine();

            // check if player wants to quit
            if (move.equals("quit")) {
                System.exit(0);
            }

            c.showAvailableSquares(move);
            System.out.println(c);

            System.out.println("Where would you like to move this piece");
            moveTo = scan.nextLine();
            String worked = c.makeMove(move + moveTo);
            System.out.println(worked);

            if (worked.equals("NoPiece")) {
                System.out.println("There is no piece on " + move + "\n\n");
            }
            c.RefreshColors();
            System.out.println(c);
        }
    }
}
