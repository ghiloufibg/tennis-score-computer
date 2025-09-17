package com.ghiloufi.kata.display;

import com.ghiloufi.kata.domain.TennisGame;
import java.util.function.Consumer;
import java.util.function.Function;

public class ScoreboardDisplay {

  private final Consumer<String> outputConsumer;
  private final Function<TennisGame, String> scoreRenderer;

  public ScoreboardDisplay(
      Consumer<String> outputConsumer, Function<TennisGame, String> scoreRenderer) {
    this.outputConsumer = outputConsumer;
    this.scoreRenderer = scoreRenderer;
  }

  public void displayScore(TennisGame game) {
    final String score = scoreRenderer.apply(game);
    outputConsumer.accept(score);
  }
}
