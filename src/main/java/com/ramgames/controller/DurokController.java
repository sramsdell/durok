package com.ramgames.controller;

import com.ramgames.GamePersistence;
import com.ramgames.model.Game;
import com.ramgames.model.Player;
import com.ramgames.model.Rules;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
}
