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

  private boolean addBattle(Card card) {
      Battle battle = new Battle(card);
      return battles.add(battle);
  }

  public List<Battle> getBattles() {
      return battles;
  }

  public boolean canAddBattle(int defenderHandSize, Rules rules) {
      // There will be optional rules later. for now, up to six and not more cards than the defender has.
      return battles.size() < rules.getMaxBattles() && battles.size() < defenderHandSize;
  }

  public boolean addAttack(Card card, int defenderHandSize, Rules rules) {
      return canAddBattle(defenderHandSize, rules) && addBattle(card);
  }

  public void transfer() {
      System.out.println("stub");
  }
}
