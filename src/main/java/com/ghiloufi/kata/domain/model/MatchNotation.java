package com.ghiloufi.kata.domain.model;

import com.ghiloufi.kata.domain.error.GameError;
import java.util.stream.Stream;

public final class MatchNotation {

  private static final int MAX_LENGTH = 10000;

  private final String value;

  private MatchNotation(String value) {
    this.value = value;
  }

  public static MatchNotation from(String raw) {
    return new MatchNotation(validateAndSanitize(raw));
  }

  private static String validateAndSanitize(String notation) {

    if (notation == null || notation.trim().isEmpty()) {
      throw GameError.NOTATION_EMPTY.toException();
    }

    String sanitized = notation.replaceAll("\\s+", "");

    if (sanitized.length() > MAX_LENGTH) {
      throw GameError.NOTATION_TOO_LONG.toException(sanitized.length(), MAX_LENGTH);
    }

    sanitized
        .chars()
        .mapToObj(c -> (char) c)
        .filter(c -> !PlayerNotation.isValidPlayerChar(c))
        .findFirst()
        .ifPresent(
            invalidChar -> {
              int position = sanitized.indexOf(invalidChar);
              throw GameError.INVALID_PLAYER_AT_POSITION.toException(invalidChar, position);
            });

    return sanitized;
  }

  public Stream<Point> points() {
    return value.chars().mapToObj(c -> Point.from((char) c));
  }

  public String getValue() {
    return value;
  }
}
