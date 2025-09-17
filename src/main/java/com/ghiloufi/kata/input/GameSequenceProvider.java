package com.ghiloufi.kata.input;

import com.ghiloufi.kata.domain.MatchNotation;
import com.ghiloufi.kata.domain.Point;
import java.util.Iterator;

public final class GameSequenceProvider {

  private GameSequenceProvider() {}

  public static Iterator<Point> fromString(String raw) {
    MatchNotation notation = MatchNotation.from(raw);
    return new GameSequenceIterator(notation.getValue());
  }

}
