package com.ghiloufi.kata.testutil.helpers;

import com.ghiloufi.kata.domain.GameState;
import com.ghiloufi.kata.testutil.data.TennisTestCase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class TestCaseLoader {

  private static final String DEFAULT_TEST_FILE = "test-cases.csv";

  public static List<TennisTestCase> loadTestCases() {
    return loadTestCases(DEFAULT_TEST_FILE);
  }

  public static List<TennisTestCase> loadTestCases(final String fileName) {
    return readTestCaseFileLines(fileName)
        .filter(line -> !shouldSkipLine(line))
        .map(TestCaseLoader::parseTestCase)
        .filter(Objects::nonNull)
        .toList();
  }

  public static Stream<String> readTestCaseFileLines(final String fileName) {
    try (var lines = Files.lines(Paths.get("src/test/resources/" + fileName))) {
      return lines.map(String::trim).toList().stream();
    } catch (IOException e) {
      System.out.println("Error reading test cases file: " + e.getMessage());
      return Stream.empty();
    }
  }

  public static boolean shouldSkipLine(final String line) {
    return line.isEmpty() || line.startsWith("#");
  }

  private static TennisTestCase parseTestCase(final String line) {
    String[] parts = line.split(",", 5);
    if (parts.length >= 3) {
      String input = parts[0].trim();
      String expectedStateStr = parts[1].trim();
      String description = parts[2].trim();

      GameState expectedState = mapStringToGameState(expectedStateStr);

      String expectedLastMessage = null;
      Integer expectedHistorySize = null;

      if (parts.length >= 4 && !parts[3].trim().isEmpty()) {
        expectedLastMessage = parts[3].trim();
      }

      if (parts.length >= 5 && !parts[4].trim().isEmpty()) {
        try {
          expectedHistorySize = Integer.parseInt(parts[4].trim());
        } catch (NumberFormatException e) {
          System.out.println("Warning: Invalid history size '" + parts[4] + "' in line: " + line);
        }
      }

      return new TennisTestCase(
          input, description, expectedState, expectedLastMessage, expectedHistorySize);
    }
    return null;
  }

  private static GameState mapStringToGameState(String stateStr) {
    return switch (stateStr) {
      case "GAME_WON_A" -> GameState.GAME_WON_A;
      case "GAME_WON_B" -> GameState.GAME_WON_B;
      case "DEUCE" -> GameState.DEUCE;
      case "ADVANTAGE_A" -> GameState.ADVANTAGE_A;
      case "ADVANTAGE_B" -> GameState.ADVANTAGE_B;
      case "IN_PROGRESS" -> GameState.IN_PROGRESS;
      default -> throw new IllegalArgumentException("Unknown game state: " + stateStr);
    };
  }
}
