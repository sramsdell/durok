package com.ramgames.model.decks;

import java.util.Objects;

public class Card implements Comparable<Card> {
    final private Suit suit;
    final private CardValue value;

    public Card(CardValue value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public CardValue getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Card otherCard = (Card) obj;
        return this.getSuit().equals(otherCard.getSuit()) &&
                this.getValue().equals(otherCard.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value.getValue(), this.suit.getSuit());
    }

    @Override
    public int compareTo(Card card) {
        return Integer.compare(this.value.getValue(), card.value.getValue());
    }
}
