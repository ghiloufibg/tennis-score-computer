package com.tennis.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Player class
 */
class PlayerTest {
    
    private Player player;
    
    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer");
    }
    
    @Test
    void testPlayerCreation() {
        assertEquals("TestPlayer", player.getName());
        assertEquals(0, player.getPoints());
    }
    
    @Test
    void testIncrementPoints() {
        player.incrementPoints();
        assertEquals(1, player.getPoints());
        
        player.incrementPoints();
        assertEquals(2, player.getPoints());
        
        player.incrementPoints();
        assertEquals(3, player.getPoints());
    }
    
    @Test
    void testReset() {
        player.incrementPoints();
        player.incrementPoints();
        assertEquals(2, player.getPoints());
        
        player.reset();
        assertEquals(0, player.getPoints());
    }
    
    @Test
    void testToString() {
        String expected = "Player{name='TestPlayer', points=0}";
        assertEquals(expected, player.toString());
    }
}