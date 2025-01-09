import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> newCards;

    public Deck() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] suits = {"H", "D", "C", "S"};
        newCards = new ArrayList<>();

        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < suits.length; j++) {
                String rank = ranks[i]; // Get the current rank
                String suit = suits[j]; // Get the current suit
                newCards.add(new Card(rank, suit));
            }
        }
        shuffle();
    }
    public void shuffle() {
        Collections.shuffle(newCards);
    }

    public Card getCard() {
        return newCards.remove(0);
    }

    public boolean isEmpty() {
        return newCards.isEmpty(); // Check if deck is empty
    }

}
