package com.ghiloufi.kata.domain;

public record Score(int points) {

  private static final int LOVE = 0;
  public static final Score LOVE_SCORE = new Score(LOVE);
  private static final int FIFTEEN = 1;
  public static final Score FIFTEEN_SCORE = new Score(FIFTEEN);
  private static final int THIRTY = 2;
  public static final Score THIRTY_SCORE = new Score(THIRTY);
  private static final int FORTY = 3;
  public static final Score FORTY_SCORE = new Score(FORTY);

  public static Score from(int points) {
    return switch (points) {
      case LOVE -> LOVE_SCORE;
      case FIFTEEN -> FIFTEEN_SCORE;
      case THIRTY -> THIRTY_SCORE;
      case FORTY -> FORTY_SCORE;
      default -> new Score(points);
    };
  }

  public Score increment() {
    return Score.from(points + 1);
  }

  public boolean isForty() {
    return points == FORTY;
  }

  public boolean isAtLeastForty() {
    return points >= FORTY;
  }

  public String toDisplayString() {
    return switch (points) {
      case LOVE -> "0";
      case FIFTEEN -> "15";
      case THIRTY -> "30";
      default -> "40";
    };
  }

  @Override
  public String toString() {
    return toDisplayString();
  }
}
