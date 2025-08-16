package com.ramgames.model.decks;

public enum Suit {
    HEART("heart"),
    SPADE("spade"),
    CLUB("clubs"),
    DIAMOND("diamond");

    final private String suit;

    Suit(String suit) {
        this.suit = suit;
    }

    public String getSuit() {
        return suit;
    }
}
