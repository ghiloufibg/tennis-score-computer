package com.ghiloufi.kata.domain.model;

import static com.ghiloufi.kata.domain.model.PlayerNotation.*;

import com.ghiloufi.kata.domain.error.GameError;
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
    if (notation == null || notation.trim().isEmpty()) {
      throw GameError.NOTATION_EMPTY.toException();
    }
    return notation.replaceAll("\\s+", "");
  }

  private static void validate(String notation) {
    if (notation.isEmpty()) {
      throw GameError.NOTATION_EMPTY.toException();
    }

    if (notation.length() > MAX_LENGTH) {
      throw GameError.NOTATION_TOO_LONG.toException(notation.length(), MAX_LENGTH);
    }

    if (!notation.chars().allMatch(c -> isValidPlayerChar((char) c))) {
      for (int i = 0; i < notation.length(); i++) {
        char point = notation.charAt(i);
        if (!isValidPlayerChar(point)) {
          throw GameError.INVALID_PLAYER_AT_POSITION.toException(point, i);
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
