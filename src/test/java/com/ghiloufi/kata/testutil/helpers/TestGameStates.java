package com.ghiloufi.kata.testutil.helpers;

import com.ghiloufi.kata.domain.AdvantageGameState;
import com.ghiloufi.kata.domain.DeuceGameState;
import com.ghiloufi.kata.domain.GameWonState;
import com.ghiloufi.kata.domain.InProgressGameState;
import com.ghiloufi.kata.domain.Player;
import com.ghiloufi.kata.domain.TennisGameState;

public final class TestGameStates {

  public static final TennisGameState IN_PROGRESS = InProgressGameState.INSTANCE;
  public static final TennisGameState DEUCE = DeuceGameState.INSTANCE;
  public static final TennisGameState ADVANTAGE_A = new AdvantageGameState(Player.A);
  public static final TennisGameState ADVANTAGE_B = new AdvantageGameState(Player.B);
  public static final TennisGameState GAME_WON_A = new GameWonState(Player.A);
  public static final TennisGameState GAME_WON_B = new GameWonState(Player.B);

  private TestGameStates() {
  }
}