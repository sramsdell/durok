package com.ramgames.model;

import com.ramgames.model.decks.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

@Schema(description = "A Game")
public class Game {

    @Getter
    String id;
    Deck deck;
    @Getter
    List<Player> players = new ArrayList<>();
    @Getter
    List<Player> activePlayers = new ArrayList<>();
    @Getter
    List<Player> otherAttackers = new ArrayList<>();
    @Getter
    Player defender;
    @Getter
    Player attacker;

    @Getter
    Player host;
    Stack<Card> trumpCards = new Stack<>();
    @Getter
    Suit trumpSuit;

    Rules rules;

    // TODO consider enum for state.
    @Getter
    boolean started = false;

    public Game(String hostName, Rules rules) {
        this.rules = rules;
        id = UUID.randomUUID().toString();
        deck = DeckFactory.create(rules.getDeckType());
        deck.shuffleDeck();
        trumpCards.add(deck.getNextCard());
        if (!rules.isSecondTrump()) {
            trumpCards.add(deck.getNextCard());
        }
        trumpSuit = trumpCards.peek().getSuit();
        host = new Player(hostName);
        addPlayer(host);
    }

    public void addPlayer(Player player) {
        // TODO validate via deck size
        players.add(player);
        activePlayers.add(player);
    }

    public boolean isGameStarted() {
        return started;
    }

    public void startGame() {
        this.started = true;
        deal();
        attacker = determineFirstPlayer();
        defender = nextPlayer(attacker.getName());
        determineOtherAttackers(defender);
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

    // TODO use Ids not names
    // use determineOtherAttackers strategy?
    public Player nextPlayer(String currentPlayerName) {
        int index = 0;
        for (int i = 0; i < activePlayers.size(); i++) {
            if (activePlayers.get(i).getName().equals(currentPlayerName)) {
                index = (i + 1) % activePlayers.size();
                break;
            }
        }
        return activePlayers.get(index);
    }

    public void determineOtherAttackers(Player defender) {
        if (!rules.isFamilyFirstAttack()) {
            // Todo, I don't like it, empty by other means.
           otherAttackers = new ArrayList<>();
           otherAttackers.add(nextPlayer(defender.getName()));
        }
    }
}
