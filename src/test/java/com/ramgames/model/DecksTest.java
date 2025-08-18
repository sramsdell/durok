package com.ramgames.model;

import com.ramgames.model.decks.*;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(stdDeck.getSize(),52);
        Assertions.assertEquals(doubleDeck.getSize(),104);
        Assertions.assertEquals(shortDeck.getSize(),36);
    }

    @Test
    void stdDeckUnique() {
        Set<Card> stdSet = new HashSet<>(stdDeck.getDeck());
        Assertions.assertEquals(stdSet.size(),52);
    }

    @Test
    void doubleDeckDoubled() {
        Set<Card> doubleSet = new HashSet<>(doubleDeck.getDeck());
        Assertions.assertEquals(doubleSet.size(),52);
    }

    @Test
    void shortDeckUnique() {
        Set<Card> shortSet = new HashSet<>(shortDeck.getDeck());
        Assertions.assertEquals(shortSet.size(),36);
    }

    @Test
    void testCardComparison() {
        Assertions.assertTrue(cardAceClub.compareTo(cardKingDiamond) > 0);
        Assertions.assertTrue(cardTwoClub.compareTo(cardKingDiamond) < 0);
        Assertions.assertEquals(0, cardTwoClub.compareTo(cardTwoHeart));
        Assertions.assertEquals(0, cardKingDiamond.compareTo(cardKingDiamond2));

    }
}