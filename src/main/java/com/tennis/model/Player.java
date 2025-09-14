package com.tennis.model;

/**
 * Represents a tennis player with their current score
 */
public class Player {
    private final String name;
    private int points;
    
    public Player(String name) {
        this.name = name;
        this.points = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void incrementPoints() {
        points++;
    }
    
    public void reset() {
        points = 0;
    }
    
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", points=" + points +
                '}';
    }
}