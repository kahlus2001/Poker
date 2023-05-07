import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Create a poker table
        PokerTable pokerTable = new PokerTable();

        // Add a player to the table
        Player player = new Player("John");
        pokerTable.addPlayer(player);

        // Shuffle the deck
        pokerTable.shuffleDeck();

        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Deal hole cards");
            System.out.println("2. Deal flop");
            System.out.println("3. Deal turn");
            System.out.println("4. Deal river");
            System.out.println("5. Show hand");
            System.out.println("6. Show community cards");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    pokerTable.dealHoleCards();
                    System.out.println(player.showHoleCards());
                    break;
                case 2:
                    pokerTable.dealFlop();
                    System.out.println(pokerTable.showCommunityCards());
                    break;
                case 3:
                    pokerTable.dealTurn();
                    System.out.println(pokerTable.showCommunityCards());
                    break;
                case 4:
                    pokerTable.dealRiver();
                    System.out.println(pokerTable.showCommunityCards());
                    break;
                case 5:
                    System.out.println(player.showHoleCards());
                    break;
                case 6:
                    System.out.println(pokerTable.showCommunityCards());
                    break;
                case 7:
                    // Evaluate the best hand
                    HandEvaluator.HandResult bestHand = pokerTable.evaluateBestHand(player, pokerTable.getCommunityCards());
                    System.out.println("Your best hand is: " + bestHand.getHandType() + " with cards " + bestHand.getBestCards());
                    break;
                case 8:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }
        // Close the scanner
        scanner.close();
    }
}
