package com.ghiloufi.kata.domain.state;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.domain.model.ScoringSystem;

public interface GameState {

  GameState scorePoint(Player playerA, Player playerB, Point point, ScoringSystem scoringSystem);

  boolean isGameFinished();

  String getDisplayName();

  Player getWinner();
}
