package com.ghiloufi.kata.computer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TennisScoreComputerFileBasedTest {

  private TennisScoreComputer computer;

  @BeforeEach
  void setUp() {
    computer = new TennisScoreComputer();
  }

  @Test
  @DisplayName("Test cases from file: all predefined scenarios")
  void testCasesFromFile() {
    List<TestCase> testCases = loadTestCasesFromFile();

    assertFalse(testCases.isEmpty(), "Should load test cases from file");

    for (TestCase testCase : testCases) {
      System.out.println("Testing: " + testCase.description);

      computer.processGame(testCase.input);

      if ("DEUCE".equals(testCase.expectedOutcome)) {
        assertEquals(
            "DEUCE", computer.getGameState().name(), "Test case failed: " + testCase.description);
      } else if ("A".equals(testCase.expectedOutcome)) {
        assertEquals(
            "GAME_WON_A",
            computer.getGameState().name(),
            "Test case failed: " + testCase.description);
      } else if ("B".equals(testCase.expectedOutcome)) {
        assertEquals(
            "GAME_WON_B",
            computer.getGameState().name(),
            "Test case failed: " + testCase.description);
      } else if ("ADVANTAGE_A".equals(testCase.expectedOutcome)) {
        assertEquals(
            "ADVANTAGE_A",
            computer.getGameState().name(),
            "Test case failed: " + testCase.description);
      } else if ("ADVANTAGE_B".equals(testCase.expectedOutcome)) {
        assertEquals(
            "ADVANTAGE_B",
            computer.getGameState().name(),
            "Test case failed: " + testCase.description);
      } else if ("IN_PROGRESS".equals(testCase.expectedOutcome)) {
        assertEquals(
            "IN_PROGRESS",
            computer.getGameState().name(),
            "Test case failed: " + testCase.description);
      }
    }

    System.out.println("All " + testCases.size() + " test cases passed!");
  }

  private List<TestCase> loadTestCasesFromFile() {
    List<TestCase> testCases = new ArrayList<>();

    try {
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-cases.txt");

      if (inputStream == null) {
        System.out.println("Warning: test-cases.txt not found, creating default cases");
        return createDefaultTestCases();
      }

      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;

      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) {
          continue; // Skip empty lines and comments
        }

        String[] parts = line.split(":");
        if (parts.length >= 3) {
          String input = parts[0];
          String expectedOutcome = parts[1];
          String description = parts[2];

          testCases.add(new TestCase(input, expectedOutcome, description));
        }
      }

      reader.close();
    } catch (Exception e) {
      System.out.println("Error reading test cases file: " + e.getMessage());
      return createDefaultTestCases();
    }

    return testCases;
  }

  private List<TestCase> createDefaultTestCases() {
    List<TestCase> defaultCases = new ArrayList<>();
    defaultCases.add(new TestCase("AAAA", "A", "Quick win for player A"));
    defaultCases.add(new TestCase("BBBB", "B", "Quick win for player B"));
    defaultCases.add(new TestCase("ABABAA", "A", "Example from kata"));
    defaultCases.add(new TestCase("AAABBB", "DEUCE", "Basic deuce"));
    return defaultCases;
  }

  @Test
  @DisplayName("Validate test case file format")
  void testFileFormatValidation() {
    try {
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-cases.txt");

      assertNotNull(inputStream, "test-cases.txt should exist in test resources");

      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      int lineNumber = 0;

      while ((line = reader.readLine()) != null) {
        lineNumber++;
        line = line.trim();

        if (line.isEmpty() || line.startsWith("#")) {
          continue; // Skip empty lines and comments
        }

        String[] parts = line.split(":");
        assertTrue(
            parts.length >= 3,
            "Line " + lineNumber + " should have at least 3 parts separated by ':'");

        // Validate input contains only A and B
        String input = parts[0];
        assertTrue(
            input.matches("[AB]+"),
            "Line " + lineNumber + ": input should contain only 'A' and 'B' characters");

        // Validate expected outcome
        String outcome = parts[1];
        assertTrue(
            outcome.matches("A|B|DEUCE|ADVANTAGE_A"),
            "Line " + lineNumber + ": outcome should be 'A', 'B', or 'DEUCE'");
      }

      reader.close();
    } catch (Exception e) {
      fail("Error validating test case file: " + e.getMessage());
    }
  }

  /** Simple data class to hold test case information */
  private static class TestCase {
    final String input;
    final String expectedOutcome;
    final String description;

    TestCase(String input, String expectedOutcome, String description) {
      this.input = input;
      this.expectedOutcome = expectedOutcome;
      this.description = description;
    }

    @Override
    public String toString() {
      return "TestCase{"
          + "input='"
          + input
          + '\''
          + ", expectedOutcome='"
          + expectedOutcome
          + '\''
          + ", description='"
          + description
          + '\''
          + '}';
    }
  }
}
