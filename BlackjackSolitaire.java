import java.util.Scanner;

public class BlackjackSolitaire {
    private Card[][] grid;
    private Deck deck;
    private int discardCards;
    private int placedCardCount;

    public BlackjackSolitaire() {
        grid = new Card[4][5];
        deck = new Deck();
        discardCards = 4;
        placedCardCount = 0;
    }

    public void play() {
        deck.shuffle();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Blackjack Solitaire!");

        while (!gridFull()) {
            displayGrid();

            Card card = deck.getCard();
            boolean validMove = false;

            while (!validMove) {
                System.out.println("You drew: " + card);
                System.out.println("Discards remaining: " + discardCards);
                System.out.println("Enter the position (1-16) to place the card, or type D to discard:");

                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("D")) {
                    if (discardCards > 0) {
                        discardCards--;
                        System.out.println("Discards remaining: " + discardCards);
                        validMove = true;
                    } else {
                        System.out.println("No discards remaining.");
                    }
                } else {
                    try {
                        int position = Integer.parseInt(input);
                        if (position < 1 || position > 16 || !placeCard(card, position)) {
                            System.out.println("Invalid position. Try again.");
                        } else {
                            placedCardCount++;
                            validMove = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Enter a position or 'D' to discard.");
                    }
                }
            }

            if (placedCardCount == 16) {
                int score = calculateScore();
                System.out.println("Game over! You scored: " + score + " points");
                break;
            }
        }
    }

    public boolean placeCard(Card card, int position) {
        int row, col;
        if (position >= 1 && position <= 5) {
            row = 0;
            col = position - 1;
        } else if (position >= 6 && position <= 10) {
            row = 1;
            col = position - 6;
        } else if (position >= 11 && position <= 13) {
            row = 2;
            col = position - 11 + 1;
        } else if (position >= 14 && position <= 16) {
            row = 3;
            col = position - 14 + 1;
        } else {
            return false;
        }
        if (grid[row][col] != null) {
            System.out.println("Position already occupied. Please choose another position.");
            return false;
        }
        grid[row][col] = card;
        return true;
    }

    public void displayGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Card card = grid[i][j];
                if (card != null) {
                    System.out.print(card + "\t");
                } else if ((i == 2 && j == 0) || (i == 2 && j == 4) || (i == 3 && j == 0) || (i == 3 && j == 4)) {
                    System.out.print("\t");
                } else {
                    int number;
                    if (i == 0) {
                        number = j + 1;
                    } else if (i == 1) {
                        number = j + 6;
                    } else if (i == 2) {
                        number = j + 10;
                    } else {
                        number = j + 13;
                    }
                    System.out.print(number + "\t");
                }
            }
            System.out.println();
        }
    }

    public boolean gridFull() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public int calculateScore() {
        int score = 0;

        for (int i = 0; i < grid.length; i++) {
            int rowSum = 0;
            int aceCount = 0;
            int numCards = 0;

            for (int j = 0; j < grid[i].length; j++) {
                Card card = grid[i][j];
                if (card != null) {
                    rowSum += card.getValue();
                    numCards++;
                    if (card.isAce()) {
                        aceCount++;
                    }
                }
            }

            while (rowSum > 21 && aceCount > 0) {
                rowSum -= 10;
                aceCount--;
            }
            int rowScore = scoreValue(rowSum, numCards);
            System.out.println("Row " + (i + 1) + " sum: " + rowSum + ", score: " + rowScore);
            score += rowScore;
        }

        // Calculate column scores
        for (int j = 0; j < grid[0].length; j++) {
            int colSum = 0;
            int aceCount = 0;
            int numCards = 0;

            for (int i = 0; i < grid.length; i++) {
                Card card = grid[i][j];
                if (card != null) {
                    colSum += card.getValue();
                    numCards++;
                    if (card.isAce()) {
                        aceCount++;
                    }
                }
            }

            while (colSum > 21 && aceCount > 0) {
                colSum -= 10;
                aceCount--;
            }
            int colScore = scoreValue(colSum, numCards);
            System.out.println("Column " + (j + 1) + " sum: " + colSum + ", score: " + colScore);
            score += colScore;
        }

        System.out.println("Total score: " + score);
        return score;
    }

    private int scoreValue(int handValue, int numCards) {
        if (numCards == 2 && handValue == 21) {
            return 10;
        } else if ((numCards >= 2 || numCards <= 5) && handValue == 21) {
            return 7;
        } else if (handValue == 20) {
            return 5;
        } else if (handValue == 19) {
            return 4;
        } else if (handValue == 18) {
            return 3;
        } else if (handValue == 17) {
            return 2;
        } else if (handValue <= 16) {
            return 1;
        } else {
            return 0;
        }
    }
}

