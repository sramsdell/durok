package com.ramgames.model;

import com.ramgames.model.decks.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

@Schema(description = "A Game")
public class Game {

    @Getter
    Player theDurok;
    @Getter
    String id;
    Deck deck;
    @Getter
    List<Player> players = new ArrayList<>();
    @Getter
    List<Player> activePlayers = new ArrayList<>();
    @Getter
    List<Player> otherAttackers = new ArrayList<>();
    @Setter
    @Getter
    Player defender;
    @Setter
    @Getter
    Player attacker;
    @Getter
    Player host;
    Stack<Card> trumpCards = new Stack<>();
    @Getter
    Suit trumpSuit;
    @Getter
    Rules rules;

    // not great that its getting set to null
    @Getter
    Round round = null;

    // TODO consider enum for state.
    @Getter
    boolean started = false;

    @Getter
    Boolean isDeckEmpty = false;

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

    public void setDurok(Player player) {
        theDurok = player;
    }

    public boolean deckHasCards() {
        return deck.getDeck().size() + trumpCards.size() > 0;
    }

    public Card getNextCardFromDeck() {
        Card card;
        if (deck.getSize() > 0) {
            return deck.getNextCard();
        }
        if (trumpCards.size() > 0) {
            return trumpCards.pop();
        }
        isDeckEmpty = true;
        return null;
    }

    public void addPlayer(Player player) {
        // TODO validate via deck size
        players.add(player);
        activePlayers.add(player);
    }

    public void resetRound() {
        round = null;
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

    public void firstAttack(Card card) {
        round = new Round(card, defender);
    }

    public boolean attack(Card card) {
        return round.addAttack(card,defender.getHand().getHandSize(), rules);
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

    public Player getActivePlayerByName(String name) {
        int index = 0;
        for (Player activePlayer : activePlayers) {
            if (activePlayer.getName().equals(name)) {
                return activePlayers.get(index);
            }
        }
        // TODO handle shouldn't get here
        return null;
    }

    public void determineOtherAttackers(Player defender) {
        if (!rules.isFamilyFirstAttack()) {
            // Todo, I don't like it, empty by other means.
           otherAttackers = new ArrayList<>();
           if (activePlayers.size() > 1) {
               otherAttackers.add(nextPlayer(defender.getName()));
           }
        }
    }
}
