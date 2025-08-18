package com.ramgames.model;

import com.ramgames.model.decks.Card;
import com.ramgames.model.decks.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {
    List<Card> hand;
    Hand() {
        hand = new ArrayList<>();
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public void addCards(List<Card> cards) {
        hand.addAll(cards);
    }

    public int handSize() {
        return hand.size();
    }

    public List<Card> getHand() {
        return hand;
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public Card getLowestCardOfSuit(Suit suit) {
        List<Card> handCopy = new ArrayList<>(hand);
        handCopy.removeIf((card) -> card.getSuit() != suit);
        Collections.sort(handCopy);
        return handCopy.get(0);
    }
    public void flushHand() {
        hand = new ArrayList<>();
    }

    public Card playCard(Card card) {
        if (hand.contains(card)) {
            removeCard(card);
            return card;
        }
        // shouldn't get here...
        // TODO log or some exception handling.
        return null;
    }
}
