package com.ghiloufi.kata.testutil.assertions;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.error.GameException;
import com.ghiloufi.kata.testutil.builders.TennisTestBuilder;

public class Assertions {

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

  public static void assertInitialState() {
    var testEnv = TennisTestBuilder.createTestEnvironment("A");
    testEnv.playMatch();
    String[] output = testEnv.getOutputAsArray();
    assertLastScoreMessage(output, "Player A : 15 / Player B : 0");
  }

  public static void assertGameEnded(String[] history) {
    String lastMessage = history[history.length - 1];
    assertTrue(lastMessage.contains("wins the game"), "Last message should announce winner");
  }

  public static void assertInvalidInputThrows(String input, String expectedMessage) {
    GameException exception =
        assertThrows(
            GameException.class,
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

  public static void assertGameEndsWithWin(String[] history) {
    String lastMessage = history[history.length - 1];
    assertTrue(lastMessage.contains("wins the game"), "Last message should announce the winner");
  }

  public static void assertGameInProgress(String[] history) {
    if (history.length > 0) {
      String lastMessage = history[history.length - 1];
      assertFalse(lastMessage.contains("wins the game"), "Game should still be in progress");
    }
  }
}
