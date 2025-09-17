package com.ghiloufi.kata.domain;

import java.util.Objects;

public final class Point {

  private static final char PLAYER_A_CHAR = 'A';
  public static final Point PLAYER_A = new Point(PLAYER_A_CHAR);
  private static final char PLAYER_B_CHAR = 'B';
  public static final Point PLAYER_B = new Point(PLAYER_B_CHAR);

  private final char winner;

  private Point(char winner) {
    this.winner = winner;
  }

  public static Point from(char winner) {
    if (winner == PLAYER_A_CHAR) {
      return PLAYER_A;
    }
    if (winner == PLAYER_B_CHAR) {
      return PLAYER_B;
    }
    throw new IllegalArgumentException(
        String.format("Invalid player: %c. Only 'A' or 'B' are allowed.", winner));
  }

  public char getWinner() {
    return winner;
  }

  public boolean isWonBy(Player player) {
    return (player == Player.A && winner == PLAYER_A_CHAR)
        || (player == Player.B && winner == PLAYER_B_CHAR);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Point point = (Point) obj;
    return winner == point.winner;
  }

  @Override
  public int hashCode() {
    return Objects.hash(winner);
  }

  @Override
  public String toString() {
    return String.valueOf(winner);
  }
}
