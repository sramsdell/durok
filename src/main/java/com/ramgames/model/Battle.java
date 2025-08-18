package com.ramgames.model;

import com.ramgames.model.decks.Card;
import lombok.Getter;

public class Battle {

    boolean isWon = false;

    @Getter
    Card attackCard;
    @Getter
    Card defendCard;

    public boolean isDefendCardValid(Card card) {
        //TODO don't forget about trumps
        return card.getSuit().equals(attackCard.getSuit()) &&
                card.compareTo(attackCard) > 0;
    }

    Battle(Card card) {
        attackCard = card;
    }

    public boolean defend(Card card) {
        if (isDefendCardValid(card)) {
            defendCard = card;
            isWon = true;
            return true;
        }
        return isWon;
    }

    public boolean isWon() {
        return isWon;
    }
}
