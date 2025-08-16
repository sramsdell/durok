package com.ramgames.model.decks;

import java.util.Collections;
import java.util.Stack;

public abstract class Deck {

    protected Stack<Card> deck;

    public Stack<Card> getDeck() {
        return deck;
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    protected void addCard(Card card) {
        deck.push(card);
    }

    public int getSize() {
        return deck.size();
    }
}
