package com.ghiloufi.kata.infrastructure.input;

import com.ghiloufi.kata.application.error.TechnicalError;
import com.ghiloufi.kata.domain.model.MatchNotation;
import com.ghiloufi.kata.domain.model.Point;
import java.util.Iterator;

public final class GameSequenceProvider {

  private GameSequenceProvider() {}

  public static Iterator<Point> fromString(String raw) {

    String sequence = MatchNotation.from(raw).getValue();

    return new Iterator<>() {

      private int currentIndex = 0;

      @Override
      public boolean hasNext() {
        return currentIndex < sequence.length();
      }

      @Override
      public Point next() {
        if (!hasNext()) {
          throw TechnicalError.NO_MORE_ELEMENTS.toException();
        }
        return Point.from(sequence.charAt(currentIndex++));
      }
    };
  }
}
