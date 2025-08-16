package com.ramgames.model.decks;

public enum CardValue {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14),
    JOKER(Integer.MAX_VALUE - 1),
    POLISH_QUEEN(Integer.MAX_VALUE);

    final private int value;
    CardValue(int i) {
        this.value = i;
    }

    public int getValue() {
        return value;
    }
}
