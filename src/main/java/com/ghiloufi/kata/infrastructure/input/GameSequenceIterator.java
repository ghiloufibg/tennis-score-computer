package com.ghiloufi.kata.infrastructure.input;

import com.ghiloufi.kata.domain.model.Point;
import java.util.Iterator;
import java.util.NoSuchElementException;

class GameSequenceIterator implements Iterator<Point> {

  private static final String NO_MORE_ELEMENTS_MESSAGE = "No more points available";

  private final String input;
  private int currentIndex;

  GameSequenceIterator(String input) {
    this.input = input;
    this.currentIndex = 0;
  }

  @Override
  public boolean hasNext() {
    return input != null && currentIndex < input.length();
  }

  @Override
  public Point next() {
    if (!hasNext()) {
      throw new NoSuchElementException(NO_MORE_ELEMENTS_MESSAGE);
    }
    return Point.from(input.charAt(currentIndex++));
  }
}
