package com.ghiloufi.kata.domain;

public final class MatchNotation {

  private static final char PLAYER_A = 'A';
  private static final char PLAYER_B = 'B';

  private MatchNotation() {}

  public static String sanitize(String notation) {
    return notation != null ? notation.replaceAll("\\s+", "") : null;
  }

  public static void validate(String notation) {
    if (notation == null) {
      throw new IllegalArgumentException("Match notation cannot be null");
    }
    if (notation.isEmpty()) {
      throw new IllegalArgumentException("Match notation cannot be empty");
    }

    for (int i = 0; i < notation.length(); i++) {
      char point = notation.charAt(i);
      if (!isValidPlayer(point)) {
        throw new IllegalArgumentException(
            String.format(
                "Invalid player: %c at position %d. Only 'A' or 'B' are allowed.", point, i));
      }
    }
  }

  public static void validateLength(String notation, int maxLength) {
    if (notation != null && notation.length() > maxLength) {
      throw new IllegalArgumentException(
          String.format(
              "Match notation too long: %d points. Maximum allowed: %d",
              notation.length(), maxLength));
    }
  }

  public static boolean isValidPlayer(char player) {
    return player == PLAYER_A || player == PLAYER_B;
  }
}
