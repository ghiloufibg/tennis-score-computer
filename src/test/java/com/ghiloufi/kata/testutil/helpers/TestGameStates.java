package com.ghiloufi.kata.testutil.helpers;

import com.ghiloufi.kata.domain.state.GameState;

public final class TestGameStates {

  public static final GameState IN_PROGRESS = GameState.PLAYING;
  public static final GameState DEUCE = GameState.DEUCE;
  public static final GameState ADVANTAGE_A = GameState.ADVANTAGE_A;
  public static final GameState ADVANTAGE_B = GameState.ADVANTAGE_B;
  public static final GameState GAME_WON_A = GameState.WON_A;
  public static final GameState GAME_WON_B = GameState.WON_B;

  private TestGameStates() {}
}
