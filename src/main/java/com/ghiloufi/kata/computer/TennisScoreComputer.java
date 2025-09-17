package com.ghiloufi.kata.computer;

import com.ghiloufi.kata.display.ScoreboardDisplay;
import com.ghiloufi.kata.domain.Point;
import com.ghiloufi.kata.domain.TennisGame;
import java.util.Iterator;

public class TennisScoreComputer {

  private final ScoreboardDisplay scoreboardDisplay;
  private TennisGame tennisGame;

  public TennisScoreComputer(final ScoreboardDisplay scoreboardDisplay) {
    this.tennisGame = TennisGame.newGame();
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
