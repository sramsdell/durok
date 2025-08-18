package com.ramgames.model;

import com.ramgames.model.decks.Card;

import java.util.ArrayList;
import java.util.List;

public class Round {
  List<Battle> battles = new ArrayList<>();
  Player defender;

  Round(Card card, Player defender) {
      // every round starts with a battle;
      addBattle(card);
      this.defender = defender;
  }

  public boolean addBattle(Card card) {
      Battle battle = new Battle(card);
      return battles.add(battle);
  }

  public boolean allBattlesWon() {
      for (Battle battle : battles) {
          if (!battle.isWon()) {
              return battle.isWon();
          }
      }
      return true;
  }

  public List<Battle> getBattles() {
      return battles;
  }

  public boolean canPlayAttackCard(Card card) {
      for (Battle battle : battles) {
          if (battle.getAttackCard().getSuit().equals(card.getSuit()) ||
              battle.getDefendCard().getSuit().equals(card.getSuit())) {
              return true;
          }
      }
      return false;
  }

  public boolean canAddBattle(int defenderHandSize, Rules rules) {
      // There will be optional rules later. for now, up to six and not more cards than the defender has.
      return battles.size() < rules.getMaxBattles() && battles.size() < defenderHandSize;
  }

  // TODO refactor do I need this still?
  public boolean addAttack(Card card, int defenderHandSize, Rules rules) {
      return canAddBattle(defenderHandSize, rules) && addBattle(card);
  }

  public void transfer() {
      System.out.println("stub");
  }
}
