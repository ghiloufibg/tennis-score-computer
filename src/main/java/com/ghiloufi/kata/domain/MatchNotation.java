package com.ghiloufi.kata.domain;

import java.util.Objects;

public final class MatchNotation {

  private static final int MAX_LENGTH = 10000;
  private static final char PLAYER_A = 'A';
  private static final char PLAYER_B = 'B';

  private final String value;

  private MatchNotation(String value) {
    this.value = value;
  }

  public static MatchNotation from(String raw) {
    String sanitized = sanitize(raw);
    validate(sanitized);
    return new MatchNotation(sanitized);
  }

  private static String sanitize(String notation) {
    if (notation == null) {
      throw new IllegalArgumentException("Match notation cannot be null");
    }
    return notation.replaceAll("\\s+", "");
  }

  private static void validate(String notation) {
    if (notation.isEmpty()) {
      throw new IllegalArgumentException("Match notation cannot be empty");
    }

    if (notation.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          String.format(
              "Match notation too long: %d points. Maximum allowed: %d",
              notation.length(), MAX_LENGTH));
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

  private static boolean isValidPlayer(char player) {
    return player == PLAYER_A || player == PLAYER_B;
  }

  public String getValue() {
    return value;
  }

  public int length() {
    return value.length();
  }

  public char charAt(int index) {
    return value.charAt(index);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    MatchNotation notation = (MatchNotation) obj;
    return Objects.equals(value, notation.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value;
  }
}
