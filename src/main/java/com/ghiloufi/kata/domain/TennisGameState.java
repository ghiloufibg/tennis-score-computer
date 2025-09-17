package com.ghiloufi.kata.domain;

public interface TennisGameState {

  TennisGameState scorePoint(
      Player playerA, Player playerB, Point point, TennisScoringSystem scoringSystem);

  boolean isGameFinished();

  String getDisplayName();

  Player getWinner();
}
