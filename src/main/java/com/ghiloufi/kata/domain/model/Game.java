package com.ghiloufi.kata.domain.model;

import com.ghiloufi.kata.domain.state.GameState;

public record Game(
    Player playerA, Player playerB, GameState gameState, ScoringSystem scoringSystem) {

  public Game(Player playerA, Player playerB, GameState gameState) {
    this(playerA, playerB, gameState, ScoringSystem.STANDARD);
  }

  public static Game newGame() {
    return new Game(Player.A, Player.B, GameState.PLAYING, ScoringSystem.STANDARD);
  }

  public Game scorePoint(Point point) {
    if (isGameFinished()) {
      return this;
    }

    Player newPlayerA = point.isWonBy(Player.A) ? playerA.scorePoint() : playerA;
    Player newPlayerB = point.isWonBy(Player.B) ? playerB.scorePoint() : playerB;
    GameState newGameState = gameState.scorePoint(newPlayerA, newPlayerB, point, scoringSystem);

    return new Game(newPlayerA, newPlayerB, newGameState, scoringSystem);
  }

  public Game reset() {
    return new Game(playerA.reset(), playerB.reset(), GameState.PLAYING, scoringSystem);
  }

  public boolean isGameFinished() {
    return gameState.isGameFinished();
  }
}
