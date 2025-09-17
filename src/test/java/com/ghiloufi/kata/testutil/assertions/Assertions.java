package com.ghiloufi.kata.testutil.assertions;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.computer.TennisScoreComputer;
import com.ghiloufi.kata.domain.GameState;
import com.ghiloufi.kata.testutil.builders.TennisTestBuilder;

public class Assertions {

  public static void assertGameState(TennisScoreComputer computer, GameState expectedState) {
    assertEquals(expectedState, computer.getGameState(), "Game state should be " + expectedState);
  }

  public static void assertScoreHistory(String[] actualHistory, String[] expectedHistory) {
    assertEquals(
        expectedHistory.length, actualHistory.length, "History size should match expected");

    for (int i = 0; i < expectedHistory.length; i++) {
      assertEquals(expectedHistory[i], actualHistory[i], "History entry " + i + " should match");
    }
  }

  public static void assertLastScoreMessage(String[] history, String expectedMessage) {
    assertNotEquals(0, history.length, "Score history should not be empty");
    assertEquals(
        expectedMessage, history[history.length - 1], "Last score message should match expected");
  }

  public static void assertHistorySize(String[] history, int expectedSize) {
    assertEquals(expectedSize, history.length, "History size should be " + expectedSize);
  }

  public static void assertPlayerPoints(
      TennisScoreComputer computer, int expectedPointsA, int expectedPointsB) {
    assertEquals(
        expectedPointsA,
        computer.getPlayerA().score().points(),
        "Player A points should be " + expectedPointsA);
    assertEquals(
        expectedPointsB,
        computer.getPlayerB().score().points(),
        "Player B points should be " + expectedPointsB);
  }

  public static void assertInitialState(TennisScoreComputer computer) {
    assertPlayerPoints(computer, 0, 0);
    assertGameState(computer, GameState.IN_PROGRESS);
  }

  public static void assertGameEnded(TennisScoreComputer computer, String[] history) {
    GameState state = computer.getGameState();
    assertTrue(
        state == GameState.GAME_WON_A || state == GameState.GAME_WON_B,
        "Game should have ended with a win");

    String lastMessage = history[history.length - 1];
    assertTrue(lastMessage.contains("wins the game"), "Last message should announce winner");
  }

  public static void assertInvalidInputThrows(String input, String expectedMessage) {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> {
              var testEnv = TennisTestBuilder.createTestEnvironment(input);
              testEnv.playMatch();
            });
    assertEquals(expectedMessage, exception.getMessage());
  }

  public static void assertOutputLinesMatch(String[] expectedLines, String[] actualLines) {
    assertEquals(expectedLines.length, actualLines.length, "Number of output lines should match");

    for (int i = 0; i < expectedLines.length; i++) {
      assertEquals(
          expectedLines[i], actualLines[i], "Line " + (i + 1) + " should match expected output");
    }
  }

  public static void assertGameEndsWithWin(TennisScoreComputer computer, String[] history) {
    assertTrue(
        computer.getGameState().toString().contains("GAME_WON"), "Game should end with a win");

    String lastMessage = history[history.length - 1];
    assertTrue(lastMessage.contains("wins the game"), "Last message should announce the winner");
  }

  public static void assertGameInProgress(TennisScoreComputer computer) {
    assertFalse(
        computer.getGameState().toString().contains("GAME_WON"),
        "Game should still be in progress");
  }
}
