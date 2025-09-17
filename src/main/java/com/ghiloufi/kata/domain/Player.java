package com.ghiloufi.kata.domain;

public record Player(String name, Score score) {

  public static final Player A = new Player("A", Score.LOVE_SCORE);
  public static final Player B = new Player("B", Score.LOVE_SCORE);

  public static Player withName(String name) {
    return new Player(name, Score.LOVE_SCORE);
  }

  public Player scorePoint() {
    return new Player(name, score.increment());
  }

  public Player reset() {
    return new Player(name, Score.LOVE_SCORE);
  }
}
