package com.ghiloufi.kata.computer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TennisScoreComputerPerformanceTest {

  private TennisScoreComputer computer;
  private Random random;

  @BeforeEach
  void setUp() {
    computer = new TennisScoreComputer();
    random = new Random(42); // Fixed seed for reproducible tests
  }

  @Test
  @DisplayName("Performance test: Process large number of games quickly")
  void testLargeNumberOfGames() {
    long startTime = System.currentTimeMillis();

    // Process 1000 games
    for (int i = 0; i < 1000; i++) {
      String gameSequence = generateRandomGameSequence();
      computer.processGame(gameSequence);
    }

    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;

    // Should complete 1000 games in less than 5 seconds (generous limit)
    assertTrue(
        duration < 5000, "Processing 1000 games took " + duration + "ms, should be under 5000ms");

    System.out.println("Processed 1000 games in " + duration + "ms");
  }

  @Test
  @DisplayName("Performance test: Very long single game")
  void testVeryLongSingleGame() {
    // Generate a very long game with many deuces
    StringBuilder longGame = new StringBuilder();

    // Start with basic game to 40-40
    longGame.append("AAABBB");

    // Add 100 back-and-forth exchanges at deuce
    for (int i = 0; i < 100; i++) {
      longGame.append("AB");
    }

    // Finally let A win
    longGame.append("AA");

    long startTime = System.currentTimeMillis();
    computer.processGame(longGame.toString());
    long endTime = System.currentTimeMillis();

    long duration = endTime - startTime;

    // Should complete even very long game quickly
    assertTrue(duration < 1000, "Very long game took " + duration + "ms, should be under 1000ms");

    // Verify the game ended correctly
    assertTrue(computer.getGameState().toString().contains("GAME_WON_A"));

    System.out.println("Processed " + longGame.length() + " ball game in " + duration + "ms");
  }

  @Test
  @DisplayName("Memory test: Multiple games don't cause memory issues")
  void testMemoryUsage() {
    Runtime runtime = Runtime.getRuntime();

    // Force garbage collection before test
    System.gc();
    long initialMemory = runtime.totalMemory() - runtime.freeMemory();

    // Process many games
    for (int i = 0; i < 10000; i++) {
      String gameSequence = generateRandomGameSequence();
      computer.processGame(gameSequence);

      // Occasionally check memory usage
      if (i % 1000 == 0) {
        System.gc();
        long currentMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryIncrease = currentMemory - initialMemory;

        // Memory increase should be reasonable (less than 50MB)
        assertTrue(
            memoryIncrease < 50 * 1024 * 1024,
            "Memory usage increased by "
                + (memoryIncrease / 1024 / 1024)
                + "MB after "
                + i
                + " games");
      }
    }

    System.gc();
    long finalMemory = runtime.totalMemory() - runtime.freeMemory();
    long totalIncrease = finalMemory - initialMemory;

    System.out.println(
        "Memory increase after 10,000 games: " + (totalIncrease / 1024 / 1024) + "MB");
  }

  @Test
  @DisplayName("Consistency test: Same input produces same output")
  void testConsistency() {
    String testInput = "AAABBBABABAA";

    // Run the same game multiple times
    String firstResult = null;

    for (int i = 0; i < 100; i++) {
      computer.processGame(testInput);
      String currentResult = computer.getScoreHistory().toString();

      if (firstResult == null) {
        firstResult = currentResult;
      } else {
        assertEquals(
            firstResult, currentResult, "Game " + i + " produced different result than first game");
      }
    }
  }

  /**
   * Generates a random but valid game sequence
   *
   * @return String representing a complete game
   */
  private String generateRandomGameSequence() {
    StringBuilder game = new StringBuilder();
    TennisScoreComputer tempComputer = new TennisScoreComputer();

    // Generate balls until game is won
    while (!tempComputer.getGameState().toString().contains("GAME_WON")) {
      char ball = random.nextBoolean() ? 'A' : 'B';
      game.append(ball);

      // Process internally to check state
      try {
        tempComputer.processGame(game.toString());
      } catch (Exception e) {
        // In case of any issue, return a simple winning game
        return "AAAA";
      }

      // Safety check to prevent infinite games (very unlikely but possible)
      if (game.length() > 1000) {
        game.append("AAAA"); // Force a win
        break;
      }
    }

    return game.toString();
  }

  @Test
  @DisplayName("Edge case performance: Alternating wins")
  void testAlternatingWinsPerformance() {
    StringBuilder alternatingGame = new StringBuilder();

    // Create alternating pattern for 500 balls
    for (int i = 0; i < 500; i++) {
      alternatingGame.append(i % 2 == 0 ? 'A' : 'B');
    }

    // Add winning sequence
    alternatingGame.append("AAA");

    long startTime = System.currentTimeMillis();
    computer.processGame(alternatingGame.toString());
    long endTime = System.currentTimeMillis();

    long duration = endTime - startTime;
    assertTrue(duration < 2000, "Alternating game took " + duration + "ms, should be under 2000ms");
  }
}
