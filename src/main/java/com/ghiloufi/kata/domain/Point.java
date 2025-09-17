package com.ghiloufi.kata.domain;

import static com.ghiloufi.kata.display.GameDisplayTemplates.*;
import static com.ghiloufi.kata.domain.PlayerNotation.*;

import java.util.Objects;

public final class Point {

  public static final Point PLAYER_A = new Point(PLAYER_A_CHAR);
  public static final Point PLAYER_B = new Point(PLAYER_B_CHAR);

  private final char winner;

  private Point(char winner) {
    this.winner = winner;
  }

  public static Point from(char winner) {
    if (!isValidPlayerChar(winner)) {
      throw new IllegalArgumentException(String.format(INVALID_PLAYER_ERROR_TEMPLATE, winner));
    }
    return winner == PLAYER_A_CHAR ? PLAYER_A : PLAYER_B;
  }

  public boolean isWonBy(Player player) {
    return winner == getPlayerChar(player);
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
