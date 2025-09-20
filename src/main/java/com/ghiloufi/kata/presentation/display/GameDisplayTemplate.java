package com.ghiloufi.kata.presentation.display;

import com.ghiloufi.kata.domain.model.Player;

public enum GameDisplayTemplate {
  DEUCE("Player A : 40 / Player B : 40 (Deuce)"),
  ADVANTAGE("Player A : 40 / Player B : 40 (Advantage Player %s)"),
  GAME_WON("Player %s wins the game"),
  SCORE("Player A : %s / Player B : %s");

  private final String template;

  GameDisplayTemplate(String template) {
    this.template = template;
  }

  public String format(Object... args) {
    return String.format(template, args);
  }

  public static String getAdvantageMessage(Player player) {
    return ADVANTAGE.format(player.name());
  }

  public static String getGameWonMessage(Player player) {
    return GAME_WON.format(player.name());
  }

  public static String formatScore(String scoreA, String scoreB) {
    return SCORE.format(scoreA, scoreB);
  }
}
