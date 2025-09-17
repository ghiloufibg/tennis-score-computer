package com.ghiloufi.kata.domain;

public record TennisScoringSystem(
    int minimumPointsForDeuce,
    int minimumPointsForWin,
    int advantagePointDifference,
    int winPointDifference) {

  public static final TennisScoringSystem STANDARD = new TennisScoringSystem(3, 4, 1, 2);

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
