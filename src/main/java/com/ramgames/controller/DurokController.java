package com.ramgames.controller;

import com.ramgames.GamePersistence;
import com.ramgames.model.Game;
import com.ramgames.model.Rules;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class DurokController {

    private final GamePersistence gamePersistence;

    public DurokController(GamePersistence gamePersistence) {
        this.gamePersistence = gamePersistence;
    }

    @Operation(summary = "Create a new Game of Durok with Rules")
    @ApiResponse(responseCode = "200", description = "Game created successfully")
    @PostMapping("/game/custom")
    public Game createGame(@RequestParam String host, @RequestParam Rules rules) {
        Game game = new Game(host, rules);
        gamePersistence.save(game);
        return game;
    }

    @Operation(summary = "Create a new Game of Basic Durok")
    @ApiResponse(responseCode = "200", description = "Game created successfully")
    @PostMapping("/game")
    public Game createGame(@RequestParam String host) {
        Rules rules = new Rules();
        Game game = new Game(host, rules);
        gamePersistence.save(game);
        return game;
    }

    @GetMapping("/game/{id}")
    @Operation(summary = "Get a game")
    public Game getGame(@PathVariable String id) {
        return gamePersistence.findById(id).orElseThrow();
    }
}
