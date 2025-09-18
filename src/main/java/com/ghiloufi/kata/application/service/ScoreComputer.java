package com.ghiloufi.kata.application.service;

import static com.ghiloufi.kata.application.error.TechnicalError.*;

import com.ghiloufi.kata.application.validation.Require;
import com.ghiloufi.kata.domain.model.Game;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.presentation.display.ScoreboardDisplay;
import java.util.Iterator;

public class ScoreComputer {

  private final ScoreboardDisplay scoreboardDisplay;
  private Game tennisGame;

  public ScoreComputer(final ScoreboardDisplay scoreboardDisplay) {
    this.scoreboardDisplay = Require.nonNull(scoreboardDisplay, NULL_SCOREBOARD_DISPLAY);
    this.tennisGame = Game.newGame();
  }

  public void playMatch(Iterator<Point> gameSequence) {
    Require.nonNull(gameSequence, NULL_GAME_SEQUENCE);

    tennisGame = tennisGame.reset();

    while (gameSequence.hasNext() && !tennisGame.isGameFinished()) {
      final Point point = gameSequence.next();
      tennisGame = tennisGame.scorePoint(point);
      scoreboardDisplay.displayScore(tennisGame);
    }
  }
}
