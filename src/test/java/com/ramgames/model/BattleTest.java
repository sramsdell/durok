package com.ramgames.model;

import org.junit.jupiter.api.Test;

public class BattleTest extends ParentTest {

    @Test
    void battleLossSameCard() {
        Battle battle = new Battle(cardTwoClub);
        battle.defend(cardTwoClub);
        assert (!battle.isWon());
    }

    @Test
    void battleLossLesserValue() {
        Battle battle = new Battle(cardAceClub);
        battle.defend(cardTwoClub);;
        assert (!battle.isWon());
    }

    @Test
    void battleLossWrongSuit() {
        Battle battle = new Battle(cardTwoClub);
        battle.defend(cardFiveSpade);
        assert (!battle.isWon());
    }

    @Test
    void battleWin() {
        Battle battle = new Battle(cardKingDiamond);
        battle.defend(cardAceDiamond);
        assert (battle.isWon());
    }
}
