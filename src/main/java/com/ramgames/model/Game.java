package com.ramgames.model;

import com.ramgames.model.decks.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

@Schema(description = "A Game")
public class Game {

    String id;
    Deck deck;
    List<Player> players = new ArrayList<>();
    List<Player> activePlayers = new ArrayList<>();;
    List<Player> otherAttackers = new ArrayList<>();;
    Player defender;
    Player attacker;

    Player host;
    Stack<Card> trumpCards = new Stack<>();
    Suit trumpSuit;

    public Game(String hostName, Rules rules) {
        id = UUID.randomUUID().toString();
        deck = DeckFactory.create(rules.getDeckType());
        deck.shuffleDeck();
        trumpCards.add(deck.getNextCard());
        if (!rules.isSecondTrump()) {
            trumpCards.add(deck.getNextCard());
        }
        trumpSuit = trumpCards.peek().getSuit();
        host = new Player(UUID.randomUUID().toString(), hostName);
    }

    public String getId() {
        return id;
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
