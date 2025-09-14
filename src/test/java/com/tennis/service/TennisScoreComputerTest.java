package com.tennis.service;

import com.tennis.model.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Comprehensive unit tests for TennisScoreComputer class
 */
class TennisScoreComputerTest {
    
    private TennisScoreComputer computer;
    
    @BeforeEach
    void setUp() {
        computer = new TennisScoreComputer();
    }
    
    @Test
    @DisplayName("Test basic scoring progression")
    void testBasicScoring() {
        computer.processGame("ABABAA");
        List<String> history = computer.getScoreHistory();
        
        assertEquals(6, history.size());
        assertEquals("Player A : 15 / Player B : 0", history.get(0));
        assertEquals("Player A : 15 / Player B : 15", history.get(1));
        assertEquals("Player A : 30 / Player B : 15", history.get(2));
        assertEquals("Player A : 30 / Player B : 30", history.get(3));
        assertEquals("Player A : 40 / Player B : 30", history.get(4));
        assertEquals("Player A wins the game", history.get(5));
    }
    
    @Test
    @DisplayName("Test quick win for player A")
    void testQuickWinPlayerA() {
        computer.processGame("AAAA");
        List<String> history = computer.getScoreHistory();
        
        assertEquals(5, history.size());
        assertEquals("Player A : 15 / Player B : 0", history.get(0));
        assertEquals("Player A : 30 / Player B : 0", history.get(1));
        assertEquals("Player A : 40 / Player B : 0", history.get(2));
        assertEquals("Player A wins the game", history.get(3));
        assertEquals(GameState.GAME_WON_A, computer.getGameState());
    }
    
    @Test
    @DisplayName("Test quick win for player B")
    void testQuickWinPlayerB() {
        computer.processGame("BBBB");
        List<String> history = computer.getScoreHistory();
        
        assertEquals(5, history.size());
        assertEquals("Player A : 0 / Player B : 15", history.get(0));
        assertEquals("Player A : 0 / Player B : 30", history.get(1));
        assertEquals("Player A : 0 / Player B : 40", history.get(2));
        assertEquals("Player B wins the game", history.get(3));
        assertEquals(GameState.GAME_WON_B, computer.getGameState());
    }
    
    @Test
    @DisplayName("Test deuce scenario")
    void testDeuce() {
        computer.processGame("AAABBB");
        List<String> history = computer.getScoreHistory();
        
        assertEquals(6, history.size());
        assertEquals("Player A : 40 / Player B : 40 (Deuce)", history.get(5));
        assertEquals(GameState.DEUCE, computer.getGameState());
    }
    
    @Test
    @DisplayName("Test advantage Player A")
    void testAdvantagePlayerA() {
        computer.processGame("AAABBBA");
        List<String> history = computer.getScoreHistory();
        
        assertEquals(7, history.size());
        assertEquals("Player A : 40 / Player B : 40 (Advantage Player A)", history.get(6));
        assertEquals(GameState.ADVANTAGE_A, computer.getGameState());
    }
    
    @Test
    @DisplayName("Test advantage Player B")
    void testAdvantagePlayerB() {
        computer.processGame("AAABBBB");
        List<String> history = computer.getScoreHistory();
        
        assertEquals(7, history.size());
        assertEquals("Player A : 40 / Player B : 40 (Advantage Player B)", history.get(6));
        assertEquals(GameState.ADVANTAGE_B, computer.getGameState());
    }
    
    @Test
    @DisplayName("Test winning from advantage")
    void testWinFromAdvantage() {
        computer.processGame("AAABBBAA");
        List<String> history = computer.getScoreHistory();
        
        assertEquals(8, history.size());
        assertEquals("Player A : 40 / Player B : 40 (Deuce)", history.get(5));
        assertEquals("Player A : 40 / Player B : 40 (Advantage Player A)", history.get(6));
        assertEquals("Player A wins the game", history.get(7));
        assertEquals(GameState.GAME_WON_A, computer.getGameState());
    }
    
    @Test
    @DisplayName("Test back to deuce from advantage")
    void testBackToDeuceFromAdvantage() {
        computer.processGame("AAABBBAB");
        List<String> history = computer.getScoreHistory();
        
        assertEquals(8, history.size());
        assertEquals("Player A : 40 / Player B : 40 (Deuce)", history.get(5));
        assertEquals("Player A : 40 / Player B : 40 (Advantage Player A)", history.get(6));
        assertEquals("Player A : 40 / Player B : 40 (Deuce)", history.get(7));
        assertEquals(GameState.DEUCE, computer.getGameState());
    }
    
    @Test
    @DisplayName("Test advantage switching")
    void testAdvantageSwitching() {
        computer.processGame("AAABBBABA");
        List<String> history = computer.getScoreHistory();
        
        assertEquals(9, history.size());
        assertEquals("Player A : 40 / Player B : 40 (Deuce)", history.get(5));
        assertEquals("Player A : 40 / Player B : 40 (Advantage Player A)", history.get(6));
        assertEquals("Player A : 40 / Player B : 40 (Deuce)", history.get(7));
        assertEquals("Player A : 40 / Player B : 40 (Advantage Player B)", history.get(8));
        assertEquals(GameState.ADVANTAGE_B, computer.getGameState());
    }
    
    @Test
    @DisplayName("Test long deuce game")
    void testLongDeuceGame() {
        computer.processGame("AAABBBABABAB");
        List<String> history = computer.getScoreHistory();
        
        // Should have multiple deuce and advantage transitions
        assertTrue(history.size() > 10);
        assertEquals("Player B wins the game", history.get(history.size() - 1));
        assertEquals(GameState.GAME_WON_B, computer.getGameState());
    }
    
    @Test
    @DisplayName("Test null input")
    void testNullInput() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> computer.processGame(null)
        );
        assertEquals("Input cannot be null or empty", exception.getMessage());
    }
    
    @Test
    @DisplayName("Test empty input")
    void testEmptyInput() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> computer.processGame("")
        );
        assertEquals("Input cannot be null or empty", exception.getMessage());
    }
    
    @Test
    @DisplayName("Test invalid character input")
    void testInvalidCharacterInput() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> computer.processGame("AAXBB")
        );
        assertEquals("Invalid character: X. Only 'A' or 'B' are allowed.", exception.getMessage());
    }
    
    @Test
    @DisplayName("Test mixed case input")
    void testMixedCaseInput() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> computer.processGame("AaBb")
        );
        assertTrue(exception.getMessage().contains("Only 'A' or 'B' are allowed"));
    }
    
    @Test
    @DisplayName("Test game stops after win")
    void testGameStopsAfterWin() {
        // Game should stop after AAAA even if there are more characters
        computer.processGame("AAAABB");
        List<String> history = computer.getScoreHistory();
        
        // Should stop after 4 balls (3 scores + 1 win message)
        assertEquals(5, history.size());
        assertEquals("Player A wins the game", history.get(4));
    }
    
    @Test
    @DisplayName("Test initial state")
    void testInitialState() {
        assertEquals(0, computer.getPlayerA().getPoints());
        assertEquals(0, computer.getPlayerB().getPoints());
        assertEquals(GameState.IN_PROGRESS, computer.getGameState());
        assertTrue(computer.getScoreHistory().isEmpty());
    }
    
    @Test
    @DisplayName("Test multiple games reset correctly")
    void testMultipleGamesReset() {
        // First game
        computer.processGame("AAAA");
        assertEquals(GameState.GAME_WON_A, computer.getGameState());
        
        // Second game - should reset properly
        computer.processGame("BBBB");
        assertEquals(GameState.GAME_WON_B, computer.getGameState());
        assertEquals(0, computer.getPlayerA().getPoints());
        assertEquals(4, computer.getPlayerB().getPoints());
    }
    
    @Test
    @DisplayName("Test score conversion at different points")
    void testScoreConversion() {
        computer.processGame("A");
        List<String> history = computer.getScoreHistory();
        assertEquals("Player A : 15 / Player B : 0", history.get(0));
        
        computer.processGame("AB");
        history = computer.getScoreHistory();
        assertEquals("Player A : 15 / Player B : 15", history.get(1));
        
        computer.processGame("ABB");
        history = computer.getScoreHistory();
        assertEquals("Player A : 15 / Player B : 30", history.get(2));
    }
}