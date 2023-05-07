import java.util.*;
import java.util.stream.Collectors;

public class HandEvaluator {

    public enum HandType {
        HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH
    }

    public static class HandResult {
        private HandType handType;
        private List<Card> bestCards;

        public HandResult(HandType handType, List<Card> bestCards) {
            this.handType = handType;
            this.bestCards = bestCards;
        }

        public HandType getHandType() {
            return handType;
        }

        public List<Card> getBestCards() {
            return bestCards;
        }

        @Override
        public String toString() {
            return handType + " with cards: " + bestCards;
        }
    }

    public static HandResult evaluateHand(List<Card> holeCards, List<Card> communityCards) {
        // Combine hole cards and community cards
        List<Card> allCards = new ArrayList<>(holeCards);
        allCards.addAll(communityCards);

        // Sort cards by rank
        allCards.sort(Comparator.comparing(Card::getRank).reversed());

        HandResult bestHand = null;

        // Check for straight flush
        bestHand = getStraightFlush(allCards);
        if (bestHand != null) {
            return bestHand;
        }

        // Check for four of a kind
        bestHand = getFourOfAKind(allCards);
        if (bestHand != null) {
            return bestHand;
        }

        // Check for full house
        bestHand = getFullHouse(allCards);
        if (bestHand != null) {
            return bestHand;
        }

        // Check for flush
        bestHand = getFlush(allCards);
        if (bestHand != null) {
            return bestHand;
        }

        // Check for straight
        bestHand = getStraight(allCards);
        if (bestHand != null) {
            return bestHand;
        }

        // Check for three of a kind
        bestHand = getThreeOfAKind(allCards);
        if (bestHand != null) {
            return bestHand;
        }

        // Check for two pair
        bestHand = getTwoPair(allCards);
        if (bestHand != null) {
            return bestHand;
        }

        // Check for one pair
        bestHand = getOnePair(allCards);
        if (bestHand != null) {
            return bestHand;
        }

        // If no other hand is found, return the highest card as the high card hand
        return new HandResult(HandType.HIGH_CARD, allCards.subList(0, 1));
    }

    private static HandResult getStraight(List<Card> allCards) {
        List<Card> straightCards = new ArrayList<>();
        Card previousCard = null;
        for (Card card : allCards) {
            if (previousCard == null || card.getRank().ordinal() == previousCard.getRank().ordinal() - 1) {
                straightCards.add(card);
                if (straightCards.size() == 5) {
                    return new HandResult(HandType.STRAIGHT, straightCards);
                }
            } else if (card.getRank() != previousCard.getRank()) {
                straightCards.clear();
                straightCards.add(card);
            }
            previousCard = card;
        }

        return null;
    }

    private static HandResult getFlush(List<Card> allCards) {
        Map<Card.Suit, List<Card>> suitMap = new HashMap<>();

        // Group cards by suit
        for (Card card : allCards) {
            suitMap.putIfAbsent(card.getSuit(), new ArrayList<>());
            suitMap.get(card.getSuit()).add(card);
        }

        // Check for a flush (5 or more cards of the same suit)
        for (List<Card> suitedCards : suitMap.values()) {
            if (suitedCards.size() >= 5) {
                return new HandResult(HandType.FLUSH, suitedCards.subList(0, 5));
            }
        }

        return null;
    }

    private static HandResult getStraightFlush(List<Card> allCards) {
        HandResult flushResult = getFlush(allCards);
        if (flushResult != null) {
            HandResult straightResult = getStraight(flushResult.getBestCards());
            if (straightResult != null) {
                return new HandResult(HandType.STRAIGHT_FLUSH, straightResult.getBestCards());
            }
        }

        return null;
    }

    private static HandResult getFourOfAKind(List<Card> allCards) {
        Map<Card.Rank, List<Card>> rankMap = new HashMap<>();

        // Group cards by rank
        for (Card card : allCards) {
            rankMap.putIfAbsent(card.getRank(), new ArrayList<>());
            rankMap.get(card.getRank()).add(card);
        }

        // Check for four of a kind
        for (List<Card> sameRankCards : rankMap.values()) {
            if (sameRankCards.size() == 4) {
                return new HandResult(HandType.FOUR_OF_A_KIND, sameRankCards);
            }
        }

        return null;
    }

    private static HandResult getFullHouse(List<Card> allCards) {
        Map<Card.Rank, List<Card>> rankMap = new HashMap<>();

        // Group cards by rank
        for (Card card : allCards) {
            rankMap.putIfAbsent(card.getRank(), new ArrayList<>());
            rankMap.get(card.getRank()).add(card);
        }

        List<Card> threeOfAKindCards = null;
        List<Card> pairCards = null;

        // Check for three of a kind and a pair
        for (List<Card> sameRankCards : rankMap.values()) {
            if (sameRankCards.size() == 3) {
                if (threeOfAKindCards == null) {
                    threeOfAKindCards = sameRankCards;
                } else if (pairCards == null) {
                    pairCards = sameRankCards.subList(0, 2);
                    break;
                }
            } else if (sameRankCards.size() >= 2 && pairCards == null) {
                pairCards = sameRankCards.subList(0, 2);
            }
        }

        if (threeOfAKindCards != null && pairCards != null) {
            List<Card> fullHouseCards = new ArrayList<>(threeOfAKindCards);
            fullHouseCards.addAll(pairCards);
            return new HandResult(HandType.FULL_HOUSE, fullHouseCards);
        }

        return null;
    }

    private static HandResult getThreeOfAKind(List<Card> allCards) {
        Map<Card.Rank, List<Card>> rankMap = new HashMap<>();

        // Group cards by rank
        for (Card card : allCards) {
            rankMap.putIfAbsent(card.getRank(), new ArrayList<>());
            rankMap.get(card.getRank()).add(card);
        }

        // Check for three of a kind
        for (List<Card> sameRankCards : rankMap.values()) {
            if (sameRankCards.size() == 3) {
                return new HandResult(HandType.THREE_OF_A_KIND, sameRankCards);
            }
        }

        return null;
    }

    private static HandResult getTwoPair(List<Card> allCards) {
        Map<Card.Rank, List<Card>> rankMap = new HashMap<>();

        // Group cards by rank
        for (Card card : allCards) {
            rankMap.putIfAbsent(card.getRank(), new ArrayList<>());
            rankMap.get(card.getRank()).add(card);
        }

        List<Card> pair1Cards = null;
        List<Card> pair2Cards = null;
        List<Card> kickerCards = new ArrayList<>();

        // Check for two pairs and a kicker
        for (List<Card> sameRankCards : rankMap.values()) {
            if (sameRankCards.size() == 2) {
                if (pair1Cards == null) {
                    pair1Cards = sameRankCards;
                } else if (pair2Cards == null) {
                    pair2Cards = sameRankCards;
                }
            } else if (sameRankCards.size() == 1) {
                kickerCards.addAll(sameRankCards);
            }
        }

        if (pair1Cards != null && pair2Cards != null) {
            List<Card> twoPairCards = new ArrayList<>(pair1Cards);
            twoPairCards.addAll(pair2Cards);
            twoPairCards.addAll(kickerCards.stream().sorted(Comparator.comparing(Card::getRank).reversed()).limit(1).collect(Collectors.toList()));
            return new HandResult(HandType.TWO_PAIR, twoPairCards);
        }

        return null;
    }

    private static HandResult getOnePair(List<Card> allCards) {
        Map<Card.Rank, List<Card>> rankMap = new HashMap<>();

        // Group cards by rank
        for (Card card : allCards) {
            rankMap.putIfAbsent(card.getRank(), new ArrayList<>());
            rankMap.get(card.getRank()).add(card);
        }

        // Check for one pair and kickers
        List<Card> pairCards = null;
        List<Card> kickerCards = new ArrayList<>();
        for (List<Card> sameRankCards : rankMap.values()) {
            if (sameRankCards.size() == 2) {
                pairCards = sameRankCards;
            } else if (sameRankCards.size() == 1) {
                kickerCards.addAll(sameRankCards);
            }
        }

        if (pairCards != null) {
            List<Card> pairAndKickerCards = new ArrayList<>(pairCards);
            pairAndKickerCards.addAll(kickerCards.stream().sorted(Comparator.comparing(Card::getRank).reversed()).limit(3).collect(Collectors.toList()));
            return new HandResult(HandType.PAIR, pairAndKickerCards);
        }

        return null;
    }


}
