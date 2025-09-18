package com.ghiloufi.kata.testutil.data;

import com.ghiloufi.kata.domain.state.GameState;
import com.ghiloufi.kata.testutil.helpers.GameStateMatchers;
import java.util.function.Predicate;

public record TennisTestCase(
    String input,
    Predicate<GameState> expectedStateChecker,
    String expectedLastMessage,
    int expectedHistorySize) {

  public static TennisTestCase of(
      String input, Predicate<GameState> stateChecker, String lastMessage, int historySize) {
    return new TennisTestCase(input, stateChecker, lastMessage, historySize);
  }

  public static TennisTestCase inProgress(String input, String lastMessage, int historySize) {
    return of(input, GameStateMatchers::isInProgress, lastMessage, historySize);
  }

  public static TennisTestCase deuce(String input, String lastMessage, int historySize) {
    return of(input, GameStateMatchers::isDeuce, lastMessage, historySize);
  }

  public static TennisTestCase advantageA(String input, String lastMessage, int historySize) {
    return of(input, GameStateMatchers::isAdvantageA, lastMessage, historySize);
  }

  public static TennisTestCase gameWonA(String input, String lastMessage, int historySize) {
    return of(input, GameStateMatchers::isGameWonA, lastMessage, historySize);
  }

  public static TennisTestCase gameWonB(String input, String lastMessage, int historySize) {
    return of(input, GameStateMatchers::isGameWonB, lastMessage, historySize);
  }
}
