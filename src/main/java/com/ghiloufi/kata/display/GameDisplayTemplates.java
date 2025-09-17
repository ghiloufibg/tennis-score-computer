package com.ghiloufi.kata.display;

import com.ghiloufi.kata.domain.Player;

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

  public static final String INVALID_PLAYER_ERROR_TEMPLATE =
      "Invalid player: %c. Only 'A' or 'B' are allowed.";
  public static final String INVALID_PLAYER_AT_POSITION_ERROR_TEMPLATE =
      "Invalid player: %c at position %d. Only 'A' or 'B' are allowed.";
  public static final String NOTATION_NULL_ERROR = "Match notation cannot be null";
  public static final String NOTATION_EMPTY_ERROR = "Match notation cannot be empty";
  public static final String NOTATION_TOO_LONG_ERROR_TEMPLATE =
      "Match notation too long: %d points. Maximum allowed: %d";

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
