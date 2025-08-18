package com.ramgames.model;

import com.ramgames.model.decks.Card;

public class Player {

    int id;

    String name;

    Hand hand;

    public void addCardToHand(Card card) {
        hand.addCard(card);
    }

    public Hand getHand() {
        return hand;
    }
}
