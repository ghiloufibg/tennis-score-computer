package com.ghiloufi.kata.domain;

import static com.ghiloufi.kata.display.GameDisplayTemplates.*;
import static com.ghiloufi.kata.domain.PlayerNotation.*;

import java.util.Objects;

public final class MatchNotation {

  private static final int MAX_LENGTH = 10000;

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
      throw new IllegalArgumentException(NOTATION_NULL_ERROR);
    }
    return notation.replaceAll("\\s+", "");
  }

  private static void validate(String notation) {
    if (notation.isEmpty()) {
      throw new IllegalArgumentException(NOTATION_EMPTY_ERROR);
    }

    if (notation.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          String.format(NOTATION_TOO_LONG_ERROR_TEMPLATE, notation.length(), MAX_LENGTH));
    }

    if (!notation.chars().allMatch(c -> isValidPlayerChar((char) c))) {
      for (int i = 0; i < notation.length(); i++) {
        char point = notation.charAt(i);
        if (!isValidPlayerChar(point)) {
          throw new IllegalArgumentException(
              String.format(INVALID_PLAYER_AT_POSITION_ERROR_TEMPLATE, point, i));
        }
      }
    }
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
