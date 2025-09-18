package com.ghiloufi.kata.presentation.display;

import com.ghiloufi.kata.domain.model.Player;

public final class GameDisplayTemplates {

  private GameDisplayTemplates() {}

  public static final String DEUCE_DISPLAY = "Player A : 40 / Player B : 40 (Deuce)";
  public static final String ADVANTAGE_PLAYER_A_DISPLAY =
      "Player A : 40 / Player B : 40 (Advantage Player A)";
  public static final String ADVANTAGE_PLAYER_B_DISPLAY =
      "Player A : 40 / Player B : 40 (Advantage Player B)";
  public static final String GAME_WON_BY_A_DISPLAY = "Player A wins the game";
  public static final String GAME_WON_BY_B_DISPLAY = "Player B wins the game";
  public static final String SCORE_FORMAT_TEMPLATE = "Player A : %s / Player B : %s";

  public static String getAdvantageMessage(Player player) {
    return player == Player.A ? ADVANTAGE_PLAYER_A_DISPLAY : ADVANTAGE_PLAYER_B_DISPLAY;
  }

  public static String getGameWonMessage(Player player) {
    return player == Player.A ? GAME_WON_BY_A_DISPLAY : GAME_WON_BY_B_DISPLAY;
  }

  public static String formatScore(String scoreA, String scoreB) {
    return String.format(SCORE_FORMAT_TEMPLATE, scoreA, scoreB);
  }
}
