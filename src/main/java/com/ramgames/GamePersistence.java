package com.ramgames;

import com.ramgames.model.Game;

import java.util.Optional;

public interface GamePersistence {
    Game save(Game game);
    Optional<Game> findById(String id);
    void delete(String id);
}
