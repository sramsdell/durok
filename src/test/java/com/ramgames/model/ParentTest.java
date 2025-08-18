package com.ramgames.model;

import com.ramgames.model.decks.Card;
import com.ramgames.model.decks.CardValue;
import com.ramgames.model.decks.Suit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;

public abstract class ParentTest {
    static Card cardTwoClub = new Card(CardValue.TWO, Suit.CLUB);
    static Card cardTwoHeart = new Card(CardValue.TWO, Suit.HEART);
    static Card cardFiveSpade = new Card(CardValue.FIVE, Suit.SPADE);
    static Card cardKingDiamond = new Card(CardValue.KING, Suit.DIAMOND);
    static Card cardKingDiamond2 = new Card(CardValue.KING, Suit.DIAMOND);
    static Card cardAceClub = new Card(CardValue.ACE, Suit.CLUB);
    static Card cardAceDiamond = new Card(CardValue.ACE, Suit.DIAMOND);
    @BeforeAll
    public static void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

}