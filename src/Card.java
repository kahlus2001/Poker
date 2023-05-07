public class Card {

    // Define the Suit enum
    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    // Define the Rank enum
    public enum Rank {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN,
        JACK, QUEEN, KING, ACE
    }

    // Properties (instance variables)
    private Suit suit;
    private Rank rank;

    // Constructor
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    // Getter methods
    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    // Additional methods
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}