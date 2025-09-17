package com.ghiloufi.kata.input;

import com.ghiloufi.kata.domain.MatchNotation;
import java.util.Iterator;

public final class GameSequenceProvider {

  private static final int MAX_NOTATION_LENGTH = 10000;

  private GameSequenceProvider() {}

  public static Iterator<Character> fromString(String matchNotation) {
    final String sanitizedNotation = MatchNotation.sanitize(matchNotation);
    MatchNotation.validate(sanitizedNotation);
    MatchNotation.validateLength(sanitizedNotation, MAX_NOTATION_LENGTH);

    return new GameSequenceIterator(sanitizedNotation);
  }
}
