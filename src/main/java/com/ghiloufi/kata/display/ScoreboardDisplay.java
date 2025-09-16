package com.ghiloufi.kata.display;

import com.ghiloufi.kata.domain.Game;
import java.util.function.Consumer;
import java.util.function.Function;

public class ScoreboardDisplay {

  private final Consumer<String> outputConsumer;
  private final Function<Game, String> scoreRenderer;

  public ScoreboardDisplay(Consumer<String> outputConsumer, Function<Game, String> scoreRenderer) {
    this.outputConsumer = outputConsumer;
    this.scoreRenderer = scoreRenderer;
  }

  public void displayScore(Game game) {
    final String score = scoreRenderer.apply(game);
    outputConsumer.accept(score);
  }
}
