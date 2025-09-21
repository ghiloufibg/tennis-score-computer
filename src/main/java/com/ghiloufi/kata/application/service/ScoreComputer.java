package com.ghiloufi.kata.application.service;

import static com.ghiloufi.kata.application.error.TechnicalError.*;

import com.ghiloufi.kata.application.validation.Require;
import com.ghiloufi.kata.domain.model.Game;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.presentation.display.ScoreboardDisplay;
import java.util.Iterator;

public class ScoreComputer {

  private final ScoreboardDisplay scoreboardDisplay;
  private final Game game;

  public ScoreComputer(final ScoreboardDisplay scoreboardDisplay) {
    this.scoreboardDisplay = Require.nonNull(scoreboardDisplay, NULL_SCOREBOARD_DISPLAY);
    this.game = Game.newGame();
  }

  public void playMatch(Iterator<Point> gameSequence) {
    Require.nonNull(gameSequence, NULL_GAME_SEQUENCE);

    Game currentGame = game.reset();

    while (gameSequence.hasNext() && !currentGame.isGameFinished()) {
      final Point point = gameSequence.next();
      currentGame = currentGame.scorePoint(point);
      scoreboardDisplay.displayScore(currentGame);
    }
  }
}
