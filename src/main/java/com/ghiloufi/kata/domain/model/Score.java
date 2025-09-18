package com.ghiloufi.kata.domain.model;

public record Score(int points) {

  public static Score from(int points) {
    return new Score(points);
  }

  public Score increment() {
    return Score.from(points + 1);
  }

  public boolean isForty() {
    return points == 3;
  }

  public boolean isAtLeastForty() {
    return points >= 3;
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
