package com.ramgames.model;

import com.ramgames.model.decks.Card;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Schema(description="A Player")
public class Player {

    @Getter
    String id;

    @Getter
    String name;

    Hand hand;
    @Setter
    @Getter
    boolean isDonePlayingCardsInCurrentRound = false;

    public Player(String name) {
        this.id = UUID.randomUUID().toString();
        // TODO validate the name
        this.name = name;
    }

    public void addCardToHand(Card card) {
        if (hand == null) {
            hand = new Hand();
        }
        hand.addCard(card);
    }

    public Hand getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return name;
    }
}
