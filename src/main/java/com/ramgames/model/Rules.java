package com.ramgames.model;

import com.ramgames.model.decks.DeckType;
import io.swagger.v3.oas.annotations.media.Schema;

public class Rules {
    // TODO read from config file or environment to set
    final int MAX_BATTLES = 6;
    final DeckType DECK_TYPE = DeckType.STD;

    final boolean SECOND_TRUMP = false;

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
