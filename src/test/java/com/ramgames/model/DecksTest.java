package com.ramgames.model;

import com.ramgames.model.decks.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class DecksTest extends ParentTest {
    static Deck stdDeck;
    static Deck shortDeck;
    static Deck doubleDeck;

    @BeforeAll
    public static void initDecks() {
        stdDeck = DeckFactory.create(DeckType.STD);
        shortDeck = DeckFactory.create(DeckType.SHORT);
        doubleDeck = DeckFactory.create(DeckType.DOUBLE);
    }

    @Test
    void DeckSize() {
        assert (stdDeck.getSize() == 52);
        assert (doubleDeck.getSize() == 104);
        assert (shortDeck.getSize() == 36);
    }

    @Test
    void stdDeckUnique() {
        Set<Card> stdSet = new HashSet<>(stdDeck.getDeck());
        assert (stdSet.size() == 52);
    }

    @Test
    void doubleDeckDoubled() {
        Set<Card> doubleSet = new HashSet<>(doubleDeck.getDeck());
        assert (doubleSet.size() == 52);
    }

    @Test
    void shortDeckUnique() {
        Set<Card> shortSet = new HashSet<>(shortDeck.getDeck());
        assert (shortSet.size() == 36);
    }

    @Test
    void testCardComparison() {
        assert (cardAceClub.compareTo(cardKingDiamond) > 0);
        assert (cardTwoClub.compareTo(cardKingDiamond) < 0);
        assert (cardTwoClub.compareTo(cardTwoHeart) == 0);
        assert (cardKingDiamond.compareTo(cardKingDiamond2) == 0);
    }
}