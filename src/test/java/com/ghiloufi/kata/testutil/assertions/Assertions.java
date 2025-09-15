package com.ghiloufi.kata.testutil.assertions;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.computer.TennisScoreComputer;
import com.ghiloufi.kata.domain.GameState;
import java.util.List;

public class Assertions {

  public static void assertGameState(TennisScoreComputer computer, GameState expectedState) {
    assertEquals(expectedState, computer.getGameState(), "Game state should be " + expectedState);
  }

  public static void assertScoreHistory(TennisScoreComputer computer, String[] expectedHistory) {
    List<String> actualHistory = computer.getScoreHistory();
    assertEquals(
        expectedHistory.length, actualHistory.size(), "History size should match expected");

    for (int i = 0; i < expectedHistory.length; i++) {
      assertEquals(
          expectedHistory[i], actualHistory.get(i), "History entry " + i + " should match");
    }
  }

  public static void assertLastScoreMessage(TennisScoreComputer computer, String expectedMessage) {
    List<String> history = computer.getScoreHistory();
    assertFalse(history.isEmpty(), "Score history should not be empty");
    assertEquals(
        expectedMessage,
        history.get(history.size() - 1),
        "Last score message should match expected");
  }

  public static void assertHistorySize(TennisScoreComputer computer, int expectedSize) {
    assertEquals(
        expectedSize, computer.getScoreHistory().size(), "History size should be " + expectedSize);
  }

  public static void assertPlayerPoints(
      TennisScoreComputer computer, int expectedPointsA, int expectedPointsB) {
    assertEquals(
        expectedPointsA,
        computer.getPlayerA().getPoints(),
        "Player A points should be " + expectedPointsA);
    assertEquals(
        expectedPointsB,
        computer.getPlayerB().getPoints(),
        "Player B points should be " + expectedPointsB);
  }

  public static void assertInitialState(TennisScoreComputer computer) {
    assertPlayerPoints(computer, 0, 0);
    assertGameState(computer, GameState.IN_PROGRESS);
    assertTrue(computer.getScoreHistory().isEmpty(), "Initial score history should be empty");
  }

  public static void assertGameEnded(TennisScoreComputer computer) {
    GameState state = computer.getGameState();
    assertTrue(
        state == GameState.GAME_WON_A || state == GameState.GAME_WON_B,
        "Game should have ended with a win");

    List<String> history = computer.getScoreHistory();
    String lastMessage = history.get(history.size() - 1);
    assertTrue(lastMessage.contains("wins the game"), "Last message should announce winner");
  }

  public static void assertInvalidInputThrows(
      TennisScoreComputer computer, String input, String expectedMessage) {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> computer.processGame(input));
    assertEquals(expectedMessage, exception.getMessage());
  }

  public static void assertOutputLinesMatch(String[] expectedLines, String[] actualLines) {
    assertEquals(expectedLines.length, actualLines.length, "Number of output lines should match");

    for (int i = 0; i < expectedLines.length; i++) {
      assertEquals(
          expectedLines[i], actualLines[i], "Line " + (i + 1) + " should match expected output");
    }
  }

  public static void assertGameEndsWithWin(TennisScoreComputer computer) {
    assertTrue(
        computer.getGameState().toString().contains("GAME_WON"), "Game should end with a win");

    List<String> history = computer.getScoreHistory();
    String lastMessage = history.get(history.size() - 1);
    assertTrue(lastMessage.contains("wins the game"), "Last message should announce the winner");
  }

  public static void assertGameInProgress(TennisScoreComputer computer) {
    assertFalse(
        computer.getGameState().toString().contains("GAME_WON"),
        "Game should still be in progress");
  }
}
