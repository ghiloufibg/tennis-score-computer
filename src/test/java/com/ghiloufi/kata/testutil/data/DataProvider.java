package com.ghiloufi.kata.testutil.data;

import com.ghiloufi.kata.domain.state.GameState;
import com.ghiloufi.kata.testutil.helpers.GameStateMatchers;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class DataProvider {

  private static final Predicate<GameState> IS_GAME_WON_A = GameStateMatchers::isGameWonA;
  private static final Predicate<GameState> IS_GAME_WON_B = GameStateMatchers::isGameWonB;
  private static final Predicate<GameState> IS_ADVANTAGE_A = GameStateMatchers::isAdvantageA;
  private static final Predicate<GameState> IS_DEUCE = GameStateMatchers::isDeuce;

  private DataProvider() {}

  public static Stream<Object[]> scoreProgressionScenarios() {
    return Stream.of(
        new Object[] {"A", "Player A : 15 / Player B : 0"},
        new Object[] {"AA", "Player A : 30 / Player B : 0"},
        new Object[] {"AAA", "Player A : 40 / Player B : 0"},
        new Object[] {"AB", "Player A : 15 / Player B : 15"},
        new Object[] {"ABAB", "Player A : 30 / Player B : 30"});
  }

  public static Stream<Object[]> quickWinScenarios() {
    return Stream.of(
        new Object[] {
          "AAAA",
          IS_GAME_WON_A,
          "Player A wins the game",
          new String[] {
            "Player A : 15 / Player B : 0",
            "Player A : 30 / Player B : 0",
            "Player A : 40 / Player B : 0",
            "Player A wins the game"
          }
        },
        new Object[] {
          "BBBB",
          IS_GAME_WON_B,
          "Player B wins the game",
          new String[] {
            "Player A : 0 / Player B : 15",
            "Player A : 0 / Player B : 30",
            "Player A : 0 / Player B : 40",
            "Player B wins the game"
          }
        });
  }

  public static Stream<Object[]> advantageScenarios() {
    return Stream.of(
        new Object[] {
          "ABABABA", IS_ADVANTAGE_A, "Player A : 40 / Player B : 40 (Advantage Player A)"
        },
        new Object[] {"ABABABAB", IS_DEUCE, "Player A : 40 / Player B : 40 (Deuce)"});
  }

  public static Stream<String[]> invalidInputScenarios() {
    return Stream.of(
        new String[] {"", "Match notation cannot be empty"},
        new String[] {"C", "Invalid player: C at position 0. Only 'A' or 'B' are allowed."},
        new String[] {"AB1", "Invalid player: 1 at position 2. Only 'A' or 'B' are allowed."});
  }

  public static Stream<TennisTestCase> complexGameScenarios() {
    return Stream.of(
        TennisTestCase.gameWonA("AAAA", "Player A wins the game", 4),
        TennisTestCase.gameWonB("BBBB", "Player B wins the game", 4),
        TennisTestCase.deuce("ABABAB", "Player A : 40 / Player B : 40 (Deuce)", 6),
        TennisTestCase.advantageA(
            "ABABABA", "Player A : 40 / Player B : 40 (Advantage Player A)", 7));
  }

  public static Stream<TennisTestCase> dataFileTestCases() {
    return Stream.of(
        TennisTestCase.gameWonA("AAAA", "Player A wins the game", 4),
        TennisTestCase.gameWonB("BBBB", "Player B wins the game", 4),
        TennisTestCase.deuce("ABABAB", "Player A : 40 / Player B : 40 (Deuce)", 6),
        TennisTestCase.advantageA(
            "ABABABA", "Player A : 40 / Player B : 40 (Advantage Player A)", 7),
        TennisTestCase.deuce("ABABABAB", "Player A : 40 / Player B : 40 (Deuce)", 8),
        TennisTestCase.gameWonA("ABABABAA", "Player A wins the game", 8),
        TennisTestCase.inProgress("A", "Player A : 15 / Player B : 0", 1),
        TennisTestCase.inProgress("AB", "Player A : 15 / Player B : 15", 2));
  }

  public static Stream<Object[]> gameScenarioExpectedOutputs() {
    return Stream.of(
        new Object[] {
          "AAAA",
          new String[] {
            "Player A : 15 / Player B : 0",
            "Player A : 30 / Player B : 0",
            "Player A : 40 / Player B : 0",
            "Player A wins the game"
          }
        },
        new Object[] {
          "BBBB",
          new String[] {
            "Player A : 0 / Player B : 15",
            "Player A : 0 / Player B : 30",
            "Player A : 0 / Player B : 40",
            "Player B wins the game"
          }
        },
        new Object[] {
          "ABABAB",
          new String[] {
            "Player A : 15 / Player B : 0",
            "Player A : 15 / Player B : 15",
            "Player A : 30 / Player B : 15",
            "Player A : 30 / Player B : 30",
            "Player A : 40 / Player B : 30",
            "Player A : 40 / Player B : 40 (Deuce)"
          }
        },
        new Object[] {
          "ABABABA",
          new String[] {
            "Player A : 15 / Player B : 0",
            "Player A : 15 / Player B : 15",
            "Player A : 30 / Player B : 15",
            "Player A : 30 / Player B : 30",
            "Player A : 40 / Player B : 30",
            "Player A : 40 / Player B : 40 (Deuce)",
            "Player A : 40 / Player B : 40 (Advantage Player A)"
          }
        });
  }
}
