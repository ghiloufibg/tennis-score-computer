package com.ghiloufi.kata.computer;

import com.ghiloufi.kata.display.ScoreboardDisplay;
import com.ghiloufi.kata.domain.GameState;
import com.ghiloufi.kata.domain.Player;
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

  public Player getPlayerA() {
    return tennisGame.getPlayerA();
  }

  public Player getPlayerB() {
    return tennisGame.getPlayerB();
  }

  public TennisGame getTennisGame() {
    return tennisGame;
  }

  public GameState getGameState() {
    return tennisGame.getGameState();
  }
}
