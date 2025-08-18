package com.ramgames.model;

import com.ramgames.model.decks.*;

import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Game {

    private int instanceId;

    Deck deck;
    List<Player> players;
    List<Player> activePlayers;

    Stack<Card> trumpCards;
    Suit trumpSuit;

    public Game(Rules rules) {
        deck = DeckFactory.create(rules.getDeckType());
        deck.shuffleDeck();
        trumpCards.add(deck.getNextCard());
        if (!rules.isSecondTrump()) {
            trumpCards.add(deck.getNextCard());
        }
        trumpSuit = trumpCards.peek().getSuit();
    }

    public void addPlayer(Player player) {
        // TODO validate via deck size
        players.add(player);
        activePlayers.add(player);
    }

    private void deal() {
        for (int i = 0; i < 6; i++) {
            for (Player player : players) {
                player.addCardToHand(deck.getNextCard());
            }
        }
    }

    private Player determineFirstPlayer() {
        Card lowestCard = Card.getHighestCard();
        Player firstPlayer = players.get(0);
        for (Player player : players) {
            Card card = player.getHand().getLowestCardOfSuit(trumpSuit);
            if (card.compareTo(lowestCard) <= 0) {
                firstPlayer = player;
                lowestCard = card;
            }
        }
        return firstPlayer;
    }

    public int firstPlayerPosition(Player firstPlayer) {
        int pos = 0;
        for (int i = 0; i< players.size(); i++) {
            if (firstPlayer.equals(players.get(i))) {
                pos = i;
                break;
            }
        }
        return pos;
    }
}
