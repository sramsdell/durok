package com.ramgames.model;

import com.ramgames.model.decks.Card;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="A Player")
public class Player {

    private String id;

    String name;

    Hand hand;

    public Player(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addCardToHand(Card card) {
        hand.addCard(card);
    }

    public Hand getHand() {
        return hand;
    }
}
