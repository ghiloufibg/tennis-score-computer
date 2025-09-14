package com.tennis.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Integration tests for TennisScoreComputer covering complete game scenarios
 */
class TennisScoreComputerIntegrationTest {
    
    private TennisScoreComputer computer;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    
    @BeforeEach
    void setUp() {
        computer = new TennisScoreComputer();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }
    
    @Test
    @DisplayName("Integration test: Complete game scenario from kata example")
    void testCompleteGameScenario() {
        computer.processGame("ABABAA");
        
        String output = outputStream.toString();
        String[] lines = output.split(System.lineSeparator());
        
        // Verify all expected output lines
        assertEquals("Player A : 15 / Player B : 0", lines[0]);
        assertEquals("Player A : 15 / Player B : 15", lines[1]);
        assertEquals("Player A : 30 / Player B : 15", lines[2]);
        assertEquals("Player A : 30 / Player B : 30", lines[3]);
        assertEquals("Player A : 40 / Player B : 30", lines[4]);
        assertEquals("Player A wins the game", lines[5]);
        
        // Verify final state
        assertTrue(computer.getGameState().toString().contains("GAME_WON_A"));
    }
    
    @Test
    @DisplayName("Integration test: Complete deuce scenario with multiple advantages")
    void testCompleteDeuce() {
        computer.processGame("AAABBBABAB");
        
        List<String> history = computer.getScoreHistory();
        
        // Verify key states in the game
        assertTrue(history.contains("Player A : 40 / Player B : 40 (Deuce)"));
        assertTrue(history.contains("Player A : 40 / Player B : 40 (Advantage Player A)"));
        assertTrue(history.contains("Player A : 40 / Player B : 40 (Advantage Player B)"));
        assertTrue(history.get(history.size() - 1).equals("Player B wins the game"));
    }
    
    @ParameterizedTest
    @DisplayName("Test various winning scenarios")
    @ValueSource(strings = {"AAAA", "BBBB", "AAAAAA", "BBBBBBB"})
    void testVariousWinningScenarios(String input) {
        computer.processGame(input);
        
        List<String> history = computer.getScoreHistory();
        String lastMessage = history.get(history.size() - 1);
        
        assertTrue(lastMessage.contains("wins the game"));
        assertTrue(computer.getGameState().toString().contains("GAME_WON"));
    }
    
    @Test
    @DisplayName("Integration test: Long deuce game with multiple back and forth")
    void testLongDeuceGame() {
        // Simulate a very long game with multiple deuces
        computer.processGame("AAABBBABABABABABA");
        
        List<String> history = computer.getScoreHistory();
        
        // Count deuce occurrences
        long deuceCount = history.stream()
            .filter(line -> line.contains("(Deuce)"))
            .count();
        
        assertTrue(deuceCount >= 3, "Should have at least 3 deuce situations");
        assertTrue(history.get(history.size() - 1).equals("Player A wins the game"));
    }
    
    @Test
    @DisplayName("Test game state consistency throughout the game")
    void testGameStateConsistency() {
        computer.processGame("AABBA");
        
        // After each ball, the game should be in a valid state
        List<String> history = computer.getScoreHistory();
        
        // Verify score progression makes sense
        assertEquals("Player A : 15 / Player B : 0", history.get(0));
        assertEquals("Player A : 30 / Player B : 0", history.get(1));
        assertEquals("Player A : 30 / Player B : 15", history.get(2));
        assertEquals("Player A : 30 / Player B : 30", history.get(3));
        assertEquals("Player A : 40 / Player B : 30", history.get(4));
        
        // Game should be in progress, not finished
        assertFalse(computer.getGameState().toString().contains("GAME_WON"));
    }
    
    @Test
    @DisplayName("Test edge case: Win immediately from 40-40")
    void testWinFromDeuce() {
        computer.processGame("AAABBBAA");
        
        List<String> history = computer.getScoreHistory();
        
        // Should go: deuce -> advantage A -> win A
        assertTrue(history.contains("Player A : 40 / Player B : 40 (Deuce)"));
        assertTrue(history.contains("Player A : 40 / Player B : 40 (Advantage Player A)"));
        assertTrue(history.contains("Player A wins the game"));
    }
    
    @Test
    @DisplayName("Test game doesn't continue after win")
    void testGameStopsAfterWin() {
        // Game should stop after 4 consecutive wins, even with more input
        computer.processGame("AAAABBBBAAAA");
        
        List<String> history = computer.getScoreHistory();
        
        // Should only process until first win
        boolean foundWin = false;
        int winIndex = -1;
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).contains("wins the game")) {
                foundWin = true;
                winIndex = i;
                break;
            }
        }
        
        assertTrue(foundWin, "Should find a win message");
        assertEquals(winIndex, history.size() - 1, "Win message should be the last message");
    }
    
    @Test
    @DisplayName("Test score format consistency")
    void testScoreFormatConsistency() {
        computer.processGame("ABAB");
        
        List<String> history = computer.getScoreHistory();
        
        // All score messages should follow the same format
        for (String score : history) {
            if (!score.contains("wins")) {
                assertTrue(score.matches("Player A : \\d+|40 / Player B : \\d+|40.*"), 
                    "Score format should be consistent: " + score);
            }
        }
    }
    
    @Test
    @DisplayName("Test all possible score combinations are reachable")
    void testAllScoreCombinations() {
        // Test that we can reach all basic score combinations
        String[][] testCases = {
            {"A", "Player A : 15 / Player B : 0"},
            {"AB", "Player A : 15 / Player B : 15"},
            {"AAB", "Player A : 30 / Player B : 15"},
            {"AABB", "Player A : 30 / Player B : 30"},
            {"AAABB", "Player A : 40 / Player B : 30"}
        };
        
        for (String[] testCase : testCases) {
            computer.processGame(testCase[0]);
            List<String> history = computer.getScoreHistory();
            assertEquals(testCase[1], history.get(history.size() - 1));
        }
    }
    
    void tearDown() {
        System.setOut(originalOut);
    }
}