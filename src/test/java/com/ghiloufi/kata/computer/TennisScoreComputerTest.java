package com.ghiloufi.kata.computer;

import static com.ghiloufi.kata.testutil.assertions.Assertions.*;

import com.ghiloufi.kata.domain.GameState;
import com.ghiloufi.kata.testutil.data.TennisTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Tests du calculateur de score de tennis")
class TennisScoreComputerTest {

  private TennisScoreComputer computer;

  @BeforeEach
  void set_up() {
    computer = new TennisScoreComputer();
  }

  @Test
  @DisplayName("Devrait commencer avec l'état initial correct")
  void should_start_with_correct_initial_state() {
    assertInitialState(computer);
  }

  @ParameterizedTest
  @DisplayName("Devrait progresser à travers les scores de base correctement")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#scoreProgressionScenarios")
  void should_progress_through_basic_scores_correctly(String input, String expectedFinalScore) {
    computer.processGame(input);
    assertLastScoreMessage(computer, expectedFinalScore);
  }

  @ParameterizedTest
  @DisplayName("Devrait gérer les victoires rapides correctement")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#quickWinScenarios")
  void should_handle_quick_wins_correctly(
      String input, GameState expectedState, String expectedWinMessage, String[] expectedHistory) {
    computer.processGame(input);

    assertScoreHistory(computer, expectedHistory);
    assertGameState(computer, expectedState);
    assertGameEnded(computer);
  }

  @Test
  @DisplayName("Devrait atteindre l'égalité correctement")
  void should_reach_deuce_correctly() {
    computer.processGame("AAABBB");

    assertGameState(computer, GameState.DEUCE);
    assertLastScoreMessage(computer, "Player A : 40 / Player B : 40 (Deuce)");
  }

  @ParameterizedTest
  @DisplayName("Devrait gérer les scénarios d'avantage correctement")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#advantageScenarios")
  void should_handle_advantage_scenarios_correctly(
      String input, GameState expectedState, String expectedLastMessage) {
    computer.processGame(input);

    assertGameState(computer, expectedState);
    assertLastScoreMessage(computer, expectedLastMessage);
  }

  @ParameterizedTest
  @DisplayName("Devrait rejeter les entrées invalides avec des messages d'erreur corrects")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#invalidInputScenarios")
  void should_reject_invalid_input_with_correct_error_messages(
      String input, String expectedMessage) {
    assertInvalidInputThrows(computer, input, expectedMessage);
  }

  @Test
  @DisplayName("Devrait arrêter le jeu après une victoire")
  void should_stop_game_after_win() {
    computer.processGame("AAAABB");

    assertHistorySize(computer, 4);
    assertLastScoreMessage(computer, "Player A wins the game");
    assertGameEnded(computer);
  }

  @ParameterizedTest
  @DisplayName("Devrait gérer les scénarios de jeu complexes correctement")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#complexGameScenarios")
  void should_handle_complex_game_scenarios_correctly(TennisTestCase testCase) {
    computer.processGame(testCase.input());

    assertGameState(computer, testCase.expectedState());
    assertLastScoreMessage(computer, testCase.expectedLastMessage());
    assertHistorySize(computer, testCase.expectedHistorySize());
  }
}
