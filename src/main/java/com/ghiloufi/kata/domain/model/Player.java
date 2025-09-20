package com.ghiloufi.kata.domain.model;

public record Player(String name, Score score) {

  public static final Player A = new Player("A", new Score(0));
  public static final Player B = new Player("B", new Score(0));

  public Player scorePoint() {
    return new Player(name, score.increment());
  }

  public Player reset() {
    return new Player(name, new Score(0));
  }
}
