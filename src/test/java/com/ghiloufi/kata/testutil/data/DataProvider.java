package com.ghiloufi.kata.testutil.data;

import com.ghiloufi.kata.domain.GameState;
import com.ghiloufi.kata.testutil.helpers.TestCaseLoader;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class DataProvider {

  public static Stream<Arguments> scoreProgressionScenarios() {
    return Stream.of(
        Arguments.of("A", "Player A : 15 / Player B : 0"),
        Arguments.of("AB", "Player A : 15 / Player B : 15"),
        Arguments.of("ABB", "Player A : 15 / Player B : 30"),
        Arguments.of("ABBA", "Player A : 30 / Player B : 30"),
        Arguments.of("ABBAA", "Player A : 40 / Player B : 30"));
  }

  public static Stream<Arguments> quickWinScenarios() {
    return Stream.of(
        Arguments.of(
            "AAAA",
            GameState.GAME_WON_A,
            "Player A wins the game",
            new String[] {
              "Player A : 15 / Player B : 0",
              "Player A : 30 / Player B : 0",
              "Player A : 40 / Player B : 0",
              "Player A wins the game"
            }),
        Arguments.of(
            "BBBB",
            GameState.GAME_WON_B,
            "Player B wins the game",
            new String[] {
              "Player A : 0 / Player B : 15",
              "Player A : 0 / Player B : 30",
              "Player A : 0 / Player B : 40",
              "Player B wins the game"
            }));
  }

  public static Stream<Arguments> advantageScenarios() {
    return Stream.of(
        Arguments.of(
            "AAABBBA", GameState.ADVANTAGE_A, "Player A : 40 / Player B : 40 (Advantage Player A)"),
        Arguments.of(
            "AAABBBB",
            GameState.ADVANTAGE_B,
            "Player A : 40 / Player B : 40 (Advantage Player B)"));
  }

  public static Stream<Arguments> invalidInputScenarios() {
    return Stream.of(
        Arguments.of(null, "Match notation cannot be null"),
        Arguments.of("", "Match notation cannot be empty"),
        Arguments.of("AAXBB", "Invalid player: X at position 2. Only 'A' or 'B' are allowed."),
        Arguments.of("AaBb", "Invalid player: a at position 1. Only 'A' or 'B' are allowed."));
  }

  public static Stream<TennisTestCase> complexGameScenarios() {
    return Stream.of(
        new TennisTestCase(
            "ABABAA",
            "Basic scoring progression with win",
            GameState.GAME_WON_A,
            "Player A wins the game",
            6),
        new TennisTestCase(
            "AAABBB",
            "Basic deuce scenario",
            GameState.DEUCE,
            "Player A : 40 / Player B : 40 (Deuce)",
            6),
        new TennisTestCase(
            "AAABBBAA",
            "Win from deuce with advantage",
            GameState.GAME_WON_A,
            "Player A wins the game",
            8));
  }

  public static Stream<Arguments> gameScenarioExpectedOutputs() {
    return Stream.of(
        Arguments.of(
            "ABABAA",
            new String[] {
              "Player A : 15 / Player B : 0",
              "Player A : 15 / Player B : 15",
              "Player A : 30 / Player B : 15",
              "Player A : 30 / Player B : 30",
              "Player A : 40 / Player B : 30",
              "Player A wins the game"
            }),
        Arguments.of(
            "AAABBB",
            new String[] {
              "Player A : 15 / Player B : 0",
              "Player A : 30 / Player B : 0",
              "Player A : 40 / Player B : 0",
              "Player A : 40 / Player B : 15",
              "Player A : 40 / Player B : 30",
              "Player A : 40 / Player B : 40 (Deuce)"
            }));
  }

  public static Stream<TennisTestCase> dataFileTestCases() {
    return TestCaseLoader.loadTestCases().stream();
  }
}
