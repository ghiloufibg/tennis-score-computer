package com.tennis.service;

import com.tennis.model.GameState;
import com.tennis.model.Player;
import com.tennis.util.InputValidator;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class responsible for computing tennis scores according to tennis rules
 */
public class TennisScoreComputer {
    
    private static final int MAX_INPUT_LENGTH = 10000; // Reasonable limit for input length
    
    private final Player playerA;
    private final Player playerB;
    private final List<String> scoreHistory;
    private GameState gameState;
    
    public TennisScoreComputer() {
        this.playerA = new Player("A");
        this.playerB = new Player("B");
        this.gameState = GameState.IN_PROGRESS;
        this.scoreHistory = new ArrayList<>();
    }
    
    /**
     * Processes a sequence of balls won and prints the score after each ball
     * @param balls String containing 'A' or 'B' characters representing balls won
     * @throws IllegalArgumentException if input is invalid
     */
    public void processGame(String balls) {
        // Sanitize and validate input
        String sanitizedBalls = InputValidator.sanitizeInput(balls);
        InputValidator.validateGameInput(sanitizedBalls);
        InputValidator.validateInputLength(sanitizedBalls, MAX_INPUT_LENGTH);
        
        resetGame();
        
        for (char ball : sanitizedBalls.toCharArray()) {
            if (isGameFinished()) {
                break;
            }
            
            processBall(ball);
            String scoreMessage = getScoreMessage();
            System.out.println(scoreMessage);
            scoreHistory.add(scoreMessage);
            
            if (isGameFinished()) {
                String winnerMessage = getWinnerMessage();
                System.out.println(winnerMessage);
                scoreHistory.add(winnerMessage);
                break;
            }
        }
    }
    
    /**
     * Processes a single ball won by a player
     * @param winner 'A' or 'B' representing the player who won the ball
     */
    private void processBall(char winner) {
        if (winner == 'A') {
            playerA.incrementPoints();
        } else {
            playerB.incrementPoints();
        }
        
        updateGameState();
    }
    
    /**
     * Updates the game state based on current points
     */
    private void updateGameState() {
        int pointsA = playerA.getPoints();
        int pointsB = playerB.getPoints();
        
        // Check for game won conditions
        if (pointsA >= 4 || pointsB >= 4) {
            if (pointsA >= 4 && pointsA - pointsB >= 2) {
                gameState = GameState.GAME_WON_A;
            } else if (pointsB >= 4 && pointsB - pointsA >= 2) {
                gameState = GameState.GAME_WON_B;
            } else if (pointsA >= 3 && pointsB >= 3) {
                // Handle deuce and advantage scenarios
                if (pointsA == pointsB) {
                    gameState = GameState.DEUCE;
                } else if (pointsA > pointsB) {
                    gameState = GameState.ADVANTAGE_A;
                } else {
                    gameState = GameState.ADVANTAGE_B;
                }
            }
        } else {
            gameState = GameState.IN_PROGRESS;
        }
    }
    
    /**
     * Gets the current score message formatted according to tennis scoring
     * @return Formatted score string
     */
    private String getScoreMessage() {
        if (gameState == GameState.DEUCE) {
            return "Player A : 40 / Player B : 40 (Deuce)";
        } else if (gameState == GameState.ADVANTAGE_A) {
            return "Player A : 40 / Player B : 40 (Advantage Player A)";
        } else if (gameState == GameState.ADVANTAGE_B) {
            return "Player A : 40 / Player B : 40 (Advantage Player B)";
        } else {
            return String.format("Player A : %s / Player B : %s", 
                    convertPointsToScore(playerA.getPoints()),
                    convertPointsToScore(playerB.getPoints()));
        }
    }
    
    /**
     * Converts numeric points to tennis score format
     * @param points Numeric points (0, 1, 2, 3+)
     * @return Tennis score format ("0", "15", "30", "40")
     */
    private String convertPointsToScore(int points) {
        switch (points) {
            case 0: return "0";
            case 1: return "15";
            case 2: return "30";
            case 3: return "40";
            default: return "40";
        }
    }
    
    /**
     * Gets the winner message
     * @return Winner announcement string
     */
    private String getWinnerMessage() {
        if (gameState == GameState.GAME_WON_A) {
            return "Player A wins the game";
        } else if (gameState == GameState.GAME_WON_B) {
            return "Player B wins the game";
        }
        return "Game not finished";
    }
    
    /**
     * Checks if the game is finished
     * @return true if game is won, false otherwise
     */
    private boolean isGameFinished() {
        return gameState == GameState.GAME_WON_A || gameState == GameState.GAME_WON_B;
    }
    
    /**
     * Resets the game to initial state
     */
    private void resetGame() {
        playerA.reset();
        playerB.reset();
        gameState = GameState.IN_PROGRESS;
        scoreHistory.clear();
    }
    
    // Getters for testing purposes
    public Player getPlayerA() {
        return playerA;
    }
    
    public Player getPlayerB() {
        return playerB;
    }
    
    public GameState getGameState() {
        return gameState;
    }
    
    public List<String> getScoreHistory() {
        return new ArrayList<>(scoreHistory);
    }
}