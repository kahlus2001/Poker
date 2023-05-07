import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> holeCards;

    public Player(String name) {
        this.name = name;
        this.holeCards = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Card> getHoleCards() {
        return holeCards;
    }

    // Add a hole card to the player's hand
    public void addHoleCard(Card card) {
        if (holeCards.size() < 2) {
            holeCards.add(card);
        } else {
            throw new IllegalStateException("Player already has two hole cards");
        }
    }

    // Clear hole cards for a new round
    public void clearHoleCards() {
        holeCards.clear();
    }

    // Show player's hole cards
    public String showHoleCards() {
        return "Player " + name + " hole cards: " + holeCards;
    }

    @Override
    public String toString() {
        return "Player [name=" + name + "]";
    }

}
