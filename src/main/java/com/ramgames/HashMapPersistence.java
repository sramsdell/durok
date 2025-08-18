package com.ramgames;

import com.ramgames.model.Game;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapPersistence implements GamePersistence {
    private final Map<String, Game> games = new ConcurrentHashMap<>();
    @Override
    public Game save(Game game) {
        games.put(game.getId(), game);
        return game;
    }

    @Override
    public Optional<Game> findById(String id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public void delete(String id) {
        games.remove(id);
    }
}
