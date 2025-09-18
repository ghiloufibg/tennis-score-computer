package com.ghiloufi.kata.application.service;

import com.ghiloufi.kata.domain.model.Game;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.presentation.display.ScoreboardDisplay;
import java.util.Iterator;

public class ScoreComputer {

  private final ScoreboardDisplay scoreboardDisplay;
  private Game tennisGame;

  public ScoreComputer(final ScoreboardDisplay scoreboardDisplay) {
    this.tennisGame = Game.newGame();
    this.scoreboardDisplay = scoreboardDisplay;
  }

  public void playMatch(Iterator<Point> gameSequence) {
    tennisGame = tennisGame.reset();

    while (gameSequence.hasNext() && !tennisGame.isGameFinished()) {
      final Point point = gameSequence.next();
      tennisGame = tennisGame.scorePoint(point);
      scoreboardDisplay.displayScore(tennisGame);
    }
  }
}
