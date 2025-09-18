package com.ghiloufi.kata.domain.model;

public record ScoringSystem(
    int minimumPointsForDeuce,
    int minimumPointsForWin,
    int advantagePointDifference,
    int winPointDifference) {

  public static final ScoringSystem STANDARD = new ScoringSystem(3, 4, 1, 2);

  public boolean isDeuce(int pointsA, int pointsB) {
    return pointsA >= minimumPointsForDeuce && pointsA == pointsB;
  }

  public boolean hasAdvantage(int playerPoints, int opponentPoints) {
    return playerPoints >= minimumPointsForWin
        && playerPoints == opponentPoints + advantagePointDifference;
  }

  public boolean hasWonGame(int playerPoints, int opponentPoints) {
    return playerPoints >= minimumPointsForWin
        && playerPoints >= opponentPoints + winPointDifference;
  }
}
