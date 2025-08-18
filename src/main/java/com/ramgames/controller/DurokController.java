package com.ramgames.controller;

import com.ramgames.GamePersistence;
import com.ramgames.model.*;
import com.ramgames.model.decks.Card;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DurokController {
    private static final Logger log = LoggerFactory.getLogger(DurokController.class);
    private final GamePersistence gamePersistence;

    public DurokController(GamePersistence gamePersistence) {
        this.gamePersistence = gamePersistence;
    }

    @Operation(summary = "Create a new Game of Durok with Rules")
    @ApiResponse(responseCode = "200", description = "Game created successfully")
    @PostMapping("/game/custom")
    public Game createGame(@RequestParam String host, @RequestParam Rules rules) {
        log.info("Host {} is creating the game with custom rules", host);
        Game game = new Game(host, rules);
        log.info("Game {} was created", game.getId());
        gamePersistence.save(game);
        return game;
    }

    @Operation(summary = "Create a new Game of Basic Durok")
    @ApiResponse(responseCode = "200", description = "Game created successfully")
    @PostMapping("/game")
    public Game createGame(@RequestParam String host) {
        log.info("Host {} is creating the game with default rules", host);
        Rules rules = new Rules();
        Game game = new Game(host, rules);
        log.info("Game {} was created", game.getId());
        gamePersistence.save(game);
        return game;
    }

    @GetMapping("/game/{id}")
    @Operation(summary = "Get a game")
    public Game getGame(@PathVariable String id) {
        return gamePersistence.findById(id).orElseThrow();
    }

    // for development
    @GetMapping("/game")
    @Operation(summary = "Get all game ids")
    public List<String> getGameIds() {
        return gamePersistence.getAllIds().orElseThrow();
    }

    @PostMapping("/game/{id}/join")
    @Operation(summary = "Join a game")
    public Game joinGame(@PathVariable String id, @RequestParam String name) {
        log.info("Player {} is joining the game with id {}", name, id);
        Game game = gamePersistence.findById(id).orElseThrow();
        Player player = new Player(name);
        game.addPlayer(player);
        log.info("Player {} has joined id {}", name, player.getId());
        gamePersistence.save(game);
        return game;
    }

    @PostMapping("/game/{id}/start")
    @Operation(summary = "start a game")
    public Game startGame(@PathVariable String id, @RequestParam String hostName) {
        log.info("Host {} is starting the game with id {}", hostName, id);
        Game game = gamePersistence.findById(id).orElseThrow();
        // TODO need authentication & authorization. should be using GUIDs instead of strings.
        if (game.getHost().toString().equals(hostName)) {
            game.startGame();
            log.info("game {} has been started", id);
            gamePersistence.save(game);
        } else {
            log.info("game {} was not started", id);
        }
        return game;
    }
    @PostMapping("/game/{id}/attack")
    @Operation(summary = "play an attack card")
    public Game playAttackCard(@PathVariable String id, @RequestBody Card card, @RequestParam String name) throws Exception {
        Game game = gamePersistence.findById(id).orElseThrow();
        // TODO refactor most of this can be encapsulated at a lower level
        // if null is first attack. TODO come up with something better.
        Round round = game.getRound();
        if (round == null) {
            // validate the attacker
            if (game.getAttacker().getName().equals(name)) {
                Hand hand = game.getAttacker().getHand();
                // validate the card
                if (hand.hasCard(card)) {
                    game.firstAttack(hand.playCard(card));
                } else {
                    throw new Exception(String.format("%s's hand does not contain card %s", name, card.toString()));
                }
            } else {
                throw new Exception(String.format("it is not %s's turn to attack", name));
            }
        } else { // There is an initiated round

            // validate attackers
            List<String> attackers = new ArrayList<>(game.getOtherAttackers().stream().map((x) -> x.getName()).collect(Collectors.toList()));
            attackers.add(game.getAttacker().getName());
            if (!attackers.contains(name)) {
                throw new Exception(String.format("it is not %s's turn to attack", name));
            }
            if (!round.canAddBattle(game.getDefender().getHand().getHandSize(), game.getRules())) {
                throw new Exception("The defender does not enough enough cards to be attacked");
            }
            if (!round.canPlayAttackCard(card)) {
                throw new Exception(String.format("card with value %s is invalid for attack", card.getValue().name()));
            }

            Player attacker = game.getActivePlayerByName(name);
            if (!attacker.getHand().hasCard(card)){
                throw new Exception(String.format("Attacker %s doesn't have card %s to play",name, card.getValue().name()));
            }
            round.addBattle(attacker.getHand().playCard(card));
        }

        gamePersistence.save(game);
        return game;
    }

    @PostMapping("/game/{id}/defend")
    @Operation(summary = "play a defend card")
    public Game playDefendCard(@PathVariable String id, @RequestBody int position, @RequestBody Card card, @RequestParam String name) throws Exception {
        Game game = gamePersistence.findById(id).orElseThrow();

        // TODO refactor most of this can be encapsulated at a lower level
        Round round = game.getRound();
        if (round == null) {
            throw new Exception("There's been no initial attack");
        }

        // validate defender
        if (!game.getDefender().getName().equals(name)) {
            throw new Exception(String.format("it is not %s's turn to defend", name));
        }

        // play a card on a battle of a round based on position.
        List<Battle> battles = round.getBattles();

        Player defender = game.getDefender();

        // will client know about 0 indexing? probably.
        if (position >= battles.size()) {
            throw new Exception(String.format("battle in position %d doesn't exist", position));
        }
        Battle battle = battles.get(position);

        if (battle.isWon()) {
            throw new Exception(String.format("battle in position %d is already won", position));
        }

        if (!defender.getHand().hasCard(card)){
            throw new Exception(String.format("player %s doesn't have card %s to play",name, card.getValue().name()));
        }
        if (!battle.isDefendCardValid(card)) {
            throw new Exception("Card is the wrong suit or value to defend");
        }

        battle.defend(defender.getHand().playCard(card));

        gamePersistence.save(game);
        return game;
    }

    @PostMapping("/game/{id}/donePlayingCards")
    @Operation(summary = "Declare you are done playing cards this round")
    public Game donePlayingCards(@PathVariable String id, @RequestParam String name) throws Exception {
        Game game = gamePersistence.findById(id).orElseThrow();
        // TODO refactor most of this can be encapsulated at a lower level
        List<String> attackerNames = new ArrayList<>(game.getOtherAttackers().stream().map((x) -> x.getName()).collect(Collectors.toList()));
        attackerNames.add(game.getAttacker().getName());
        if (!attackerNames.contains(name) && !game.getDefender().getName().equals(name)) {
            throw new Exception(String.format("player %s is not an active player", name));
        }

        if (attackerNames.contains(name)) {
            Player attacker = game.getActivePlayerByName(name);
            attacker.setDonePlayingCardsInCurrentRound(true);
        }

        Player defender = game.getDefender();
        if (defender.getName().equals(name)) {
            List<Player> attackerObjs = game.getOtherAttackers();
            attackerObjs.add(game.getAttacker());
            for (Player attacker : attackerObjs) {
                if (!attacker.isDonePlayingCardsInCurrentRound()) {
                    throw new Exception(String.format("not all attackers have declared done"));
                }
            }

            // if not all battles won, defender gets all the cards from the battle.
            Round round = game.getRound();
            if (!round.allBattlesWon()) {
                List<Battle> battles = round.getBattles();
                for (Battle battle : battles) {
                    defender.getHand().addCard(battle.getAttackCard());
                    if (battle.getDefendCard() != null) {
                        defender.getHand().addCard(battle.getDefendCard());
                    }
                }
            }

            // set all players isDonePlayingCardsInCurrentRound to false;
            game.resetRound();
            for (Player player : game.getActivePlayers()) {
                player.setDonePlayingCardsInCurrentRound(false);
            }

            // deal out new cards in order attacker, otherAttacker, then defender.
            while (game.getAttacker().getHand().getHandSize() < 6 && game.deckHasCards()) {
                game.getAttacker().getHand().addCard(game.getNextCardFromDeck());
            }
            for (Player player : game.getOtherAttackers()){
                while (player.getHand().getHandSize() < 6 && game.deckHasCards()) {
                    player.getHand().addCard(game.getNextCardFromDeck());
                }
            }
            while (game.getDefender().getHand().getHandSize() < 6 && game.deckHasCards()) {
                game.getDefender().getHand().addCard(game.getNextCardFromDeck());
            }

            // if any of the players at this point have no cards, remove from the active player list.
            List<Player> activePlayerCopy = new ArrayList<>(game.getActivePlayers());
            for (Player player : activePlayerCopy) {
                if (player.getHand().getHandSize() <= 0) {
                    game.getActivePlayers().remove(player);
                }
            }

            // if There's only one active player left, game over.
            if (game.getActivePlayers().size() <= 1) {
                game.setDurok(game.getActivePlayers().get(0));
                gamePersistence.save(game);
                return game;
            }

            // set new attacker, defender and otherAttackers for next round
            game.setAttacker(game.nextPlayer(game.getAttacker().getName()));
            game.setDefender(game.nextPlayer(game.getAttacker().getName()));
            game.determineOtherAttackers(game.getDefender());
        }

        gamePersistence.save(game);
        return game;
    }
}
