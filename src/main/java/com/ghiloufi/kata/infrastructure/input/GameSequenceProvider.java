package com.ghiloufi.kata.infrastructure.input;

import com.ghiloufi.kata.domain.model.MatchNotation;
import com.ghiloufi.kata.domain.model.Point;
import java.util.Iterator;

public final class GameSequenceProvider {

  private GameSequenceProvider() {}

  public static Iterator<Point> fromString(String raw) {
    return MatchNotation.from(raw)
        .getValue()
        .chars()
        .mapToObj(c -> Point.from((char) c))
        .iterator();
  }
}
