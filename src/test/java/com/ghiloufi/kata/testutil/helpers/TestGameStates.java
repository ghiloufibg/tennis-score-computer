package com.ghiloufi.kata.testutil.helpers;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.state.AdvantageGameState;
import com.ghiloufi.kata.domain.state.DeuceGameState;
import com.ghiloufi.kata.domain.state.GameState;
import com.ghiloufi.kata.domain.state.GameWonState;
import com.ghiloufi.kata.domain.state.InProgressGameState;

public final class TestGameStates {

  public static final GameState IN_PROGRESS = InProgressGameState.INSTANCE;
  public static final GameState DEUCE = DeuceGameState.INSTANCE;
  public static final GameState ADVANTAGE_A = new AdvantageGameState(Player.A);
  public static final GameState ADVANTAGE_B = new AdvantageGameState(Player.B);
  public static final GameState GAME_WON_A = new GameWonState(Player.A);
  public static final GameState GAME_WON_B = new GameWonState(Player.B);

  private TestGameStates() {}
}
