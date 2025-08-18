package com.ramgames.model;

import com.ramgames.model.decks.DeckFactory;
import com.ramgames.model.decks.DeckType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GameTest extends ParentTest {
    static Rules rules;
    static String[] names = {"steve", "beth", "mary", "jim"};
    static Game game;

    @BeforeEach
    public void gameSetup() {
        rules = new Rules();
        game = new Game(names[0], rules);
        for (int i = 1; i< names.length; i++) {
            game.addPlayer(new Player(names[i]));
        }
    }

    @Test
    void gameStarted() {
        game = new Game(names[0], rules);
        Assertions.assertFalse(game.started);
        game.startGame();
        Assertions.assertTrue(game.started);
    }

    @Test
    void testDefender() {
        Assertions.assertEquals(names[3], game.nextPlayer(names[2]).getName());
        Assertions.assertEquals(names[0], game.nextPlayer(names[3]).getName());
    }

    @Test
    void testOtherAttackers() {

        game.determineOtherAttackers(new Player(names[2]));
        Assertions.assertEquals(names[3], game.getOtherAttackers().get(0).getName());

        game.determineOtherAttackers(new Player(names[3]));
        Assertions.assertEquals(names[0], game.getOtherAttackers().get(0).getName());
    }
}
