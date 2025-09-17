package com.ghiloufi.kata.domain;

public final class PlayerNotation {

  private PlayerNotation() {}

  public static final char PLAYER_A_CHAR = 'A';
  public static final char PLAYER_B_CHAR = 'B';

  public static boolean isValidPlayerChar(char player) {
    return player == PLAYER_A_CHAR || player == PLAYER_B_CHAR;
  }

  public static char getPlayerChar(Player player) {
    return player == Player.A ? PLAYER_A_CHAR : PLAYER_B_CHAR;
  }
}
