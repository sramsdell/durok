package com.ramgames.model;

import com.ramgames.model.decks.Card;

public class Battle {

    boolean isWon = false;

    Card attackCard;

    private boolean isDefendCardValid(Card card) {
        //TODO don't forget about trumps
        return card.getSuit().equals(attackCard.getSuit()) &&
                card.compareTo(attackCard) > 0;
    }

    Battle(Card card) {
        attackCard = card;
    }

    public boolean defend(Card card) {
        if (isDefendCardValid(card)) {
            isWon = true;
            return true;
        }
        return isWon;
    }

    public boolean isWon() {
        return isWon;
    }
}
