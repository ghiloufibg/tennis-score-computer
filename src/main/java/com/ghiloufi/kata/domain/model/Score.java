package com.ghiloufi.kata.domain.model;

import com.ghiloufi.kata.domain.error.GameError;

public record Score(int points) {

  public Score {
    if (points < 0) {
      throw GameError.NEGATIVE_POINTS.toException(points);
    }
  }

  public Score increment() {
    return new Score(points + 1);
  }

  @Override
  public String toString() {
    return switch (points) {
      case 0 -> "0";
      case 1 -> "15";
      case 2 -> "30";
      default -> "40";
    };
  }
}
