package com.ghiloufi.kata.presentation.display;

import static com.ghiloufi.kata.application.error.TechnicalError.*;

import com.ghiloufi.kata.application.validation.Require;
import com.ghiloufi.kata.domain.model.Game;
import java.util.function.Consumer;
import java.util.function.Function;

public class ScoreboardDisplay {

  private final Consumer<String> outputConsumer;
  private final Function<Game, String> scoreRenderer;

  public ScoreboardDisplay(Consumer<String> outputConsumer, Function<Game, String> scoreRenderer) {
    this.outputConsumer = Require.nonNull(outputConsumer, NULL_OUTPUT_CONSUMER);
    this.scoreRenderer = Require.nonNull(scoreRenderer, NULL_SCORE_RENDERER);
  }

  public void displayScore(Game game) {
    Require.nonNull(game, NULL_GAME);
    final String score = scoreRenderer.apply(game);
    outputConsumer.accept(score);
  }
}
