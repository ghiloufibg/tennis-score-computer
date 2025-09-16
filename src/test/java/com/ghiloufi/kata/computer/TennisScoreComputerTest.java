package com.ghiloufi.kata.computer;

import static com.ghiloufi.kata.testutil.assertions.Assertions.*;

import com.ghiloufi.kata.domain.GameState;
import com.ghiloufi.kata.testutil.base.TennisTestBase;
import com.ghiloufi.kata.testutil.data.TennisTestCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Tests du calculateur de score de tennis")
class TennisScoreComputerTest extends TennisTestBase {

  @Test
  @DisplayName("Devrait commencer avec l'état initial correct")
  void should_start_with_correct_initial_state() {
    assertInitialState(testEnvironment.getComputer());
  }

  @ParameterizedTest
  @DisplayName("Devrait progresser à travers les scores de base correctement")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#scoreProgressionScenarios")
  void should_progress_through_basic_scores_correctly(String input, String expectedFinalScore) {
    testEnvironment.getComputer().processGame(input);
    assertLastScoreMessage(testEnvironment.getOutputAsArray(), expectedFinalScore);
  }

  @ParameterizedTest
  @DisplayName("Devrait gérer les victoires rapides correctement")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#quickWinScenarios")
  void should_handle_quick_wins_correctly(
      String input, GameState expectedState, String expectedWinMessage, String[] expectedHistory) {
    testEnvironment.getComputer().processGame(input);
    String[] outputLines = testEnvironment.getOutputAsArray();
    assertScoreHistory(outputLines, expectedHistory);
    assertGameState(testEnvironment.getComputer(), expectedState);
    assertGameEnded(testEnvironment.getComputer(), outputLines);
  }

  @Test
  @DisplayName("Devrait atteindre l'égalité correctement")
  void should_reach_deuce_correctly() {
    testEnvironment.getComputer().processGame("AAABBB");
    String[] outputLines = testEnvironment.getOutputAsArray();
    assertGameState(testEnvironment.getComputer(), GameState.DEUCE);
    assertLastScoreMessage(outputLines, "Player A : 40 / Player B : 40 (Deuce)");
  }

  @ParameterizedTest
  @DisplayName("Devrait gérer les scénarios d'avantage correctement")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#advantageScenarios")
  void should_handle_advantage_scenarios_correctly(
      String input, GameState expectedState, String expectedLastMessage) {
    testEnvironment.getComputer().processGame(input);
    String[] outputLines = testEnvironment.getOutputAsArray();
    assertGameState(testEnvironment.getComputer(), expectedState);
    assertLastScoreMessage(outputLines, expectedLastMessage);
  }

  @ParameterizedTest
  @DisplayName("Devrait rejeter les entrées invalides avec des messages d'erreur corrects")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#invalidInputScenarios")
  void should_reject_invalid_input_with_correct_error_messages(
      String input, String expectedMessage) {
    assertInvalidInputThrows(testEnvironment.getComputer(), input, expectedMessage);
  }

  @Test
  @DisplayName("Devrait arrêter le jeu après une victoire")
  void should_stop_game_after_win() {
    testEnvironment.getComputer().processGame("AAAABB");
    String[] outputLines = testEnvironment.getOutputAsArray();
    assertHistorySize(outputLines, 4);
    assertLastScoreMessage(outputLines, "Player A wins the game");
    assertGameEnded(testEnvironment.getComputer(), outputLines);
  }

  @ParameterizedTest
  @DisplayName("Devrait gérer les scénarios de jeu complexes correctement")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#complexGameScenarios")
  void should_handle_complex_game_scenarios_correctly(TennisTestCase testCase) {
    testEnvironment.getComputer().processGame(testCase.input());
    String[] outputLines = testEnvironment.getOutputAsArray();
    assertGameState(testEnvironment.getComputer(), testCase.expectedState());
    assertLastScoreMessage(outputLines, testCase.expectedLastMessage());
    assertHistorySize(outputLines, testCase.expectedHistorySize());
  }
}
