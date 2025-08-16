package com.ramgames.model.decks;

public class DeckFactory {
    public static  Deck create(DeckType type) {
        switch (type) {
            case STD:
                return new StdDeck();
            case SHORT:
                return new ShortDeck();
            case DOUBLE:
                return new DoubleDeck();
            default:
                throw new IllegalArgumentException(String.format("Deck Type %s doesn't exist.", type.toString()));
        }
    }
    private DeckFactory() {

    }
}
