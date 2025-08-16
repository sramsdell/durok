package com.ramgames.model.decks;

import java.util.Stack;

public class StdDeck extends Deck {
    public StdDeck() {
        deck = new Stack<>();
        for (Suit suit : Suit.values()) {
            addCard(new Card(CardValue.TWO, suit));
            addCard(new Card(CardValue.THREE, suit));
            addCard(new Card(CardValue.FOUR, suit));
            addCard(new Card(CardValue.FIVE, suit));
            addCard(new Card(CardValue.SIX, suit));
            addCard(new Card(CardValue.SEVEN, suit));
            addCard(new Card(CardValue.EIGHT, suit));
            addCard(new Card(CardValue.NINE, suit));
            addCard(new Card(CardValue.TEN, suit));
            addCard(new Card(CardValue.JACK, suit));
            addCard(new Card(CardValue.QUEEN, suit));
            addCard(new Card(CardValue.KING, suit));
            addCard(new Card(CardValue.ACE, suit));
        }
    }
}
