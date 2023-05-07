import java.util.ArrayList;
import java.util.List;

public class PokerTable {
    private List<Player> players;
    private List<Card> communityCards;
    private Deck deck;

    public PokerTable() {
        this.players = new ArrayList<>();
        this.communityCards = new ArrayList<>();
        this.deck = new Deck();
    }

    // Add a player to the table
    public void addPlayer(Player player) {
        players.add(player);
    }

    // Shuffle the deck
    public void shuffleDeck() {
        deck.shuffle();
    }

    // Deal hole cards to each player
    public void dealHoleCards() {
        for (Player player : players) {
            player.addHoleCard(deck.dealCard());
            player.addHoleCard(deck.dealCard());
        }
    }

    // Deal the flop (the first three community cards)
    public void dealFlop() {
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.dealCard());
        }
    }

    // Deal the turn (the fourth community card)
    public void dealTurn() {
        communityCards.add(deck.dealCard());
    }

    // Deal the river (the fifth and final community card)
    public void dealRiver() {
        communityCards.add(deck.dealCard());
    }

    // Show community cards
    public String showCommunityCards() {
        return "Community Cards: " + communityCards;
    }

    public List<Card> getCommunityCards() {
        return this.communityCards;
    }

    // Clear community cards for a new round
    public void clearCommunityCards() {
        communityCards.clear();
    }

    public HandEvaluator.HandResult evaluateBestHand(Player player, List<Card> communityCards) {
        // Combine the player's hand and the community cards
        List<Card> allCards = new ArrayList<>(player.getHoleCards());
        allCards.addAll(communityCards);

        // Evaluate the best hand
        return HandEvaluator.evaluateHand(this.players.get(0).getHoleCards(),  communityCards);
    }
}