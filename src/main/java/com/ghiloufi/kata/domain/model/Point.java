package com.ghiloufi.kata.domain.model;

import com.ghiloufi.kata.domain.error.GameError;

public enum Point {
  A,
  B;

  public static Point from(char c) {
    return switch (c) {
      case 'A' -> A;
      case 'B' -> B;
      default -> throw GameError.INVALID_PLAYER.toException(c);
    };
  }

  public static boolean isValidPlayerChar(char c) {
    return c == 'A' || c == 'B';
  }

  public boolean isWonBy(Player player) {
    return this == A ? player == Player.A : player == Player.B;
  }
}
