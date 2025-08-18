package com.ramgames.model;

import com.ramgames.model.decks.DeckType;
import io.swagger.v3.oas.annotations.media.Schema;

public class Rules {

    // defaults for basic game.
    int MAX_BATTLES = 6;
    DeckType DECK_TYPE = DeckType.STD;

    boolean SECOND_TRUMP = false;

    public int getMaxBattles() {
        return MAX_BATTLES;
    }

    public DeckType getDeckType() {
        return DECK_TYPE;
    }

    public boolean isSecondTrump() {
        return SECOND_TRUMP;
    }
}
