package com.tennis;

import com.tennis.service.TennisScoreComputer;

/** Main class to demonstrate the Tennis Score Computer */
public class Main {
  public static void main(String[] args) {
    TennisScoreComputer computer = new TennisScoreComputer();

    System.out.println("=== Tennis Score Computer Demo ===\n");

    // Example from the kata description
    System.out.println("Example 1: ABABAA");
    computer.processGame("ABABAA");

    System.out.println("\n" + "=".repeat(40) + "\n");

    // Additional examples
    System.out.println("Example 2: AAAA (Quick win for A)");
    computer.processGame("AAAA");

    System.out.println("\n" + "=".repeat(40) + "\n");

    System.out.println("Example 3: AAABBBAA (Deuce scenario)");
    computer.processGame("AAABBBAA");

    System.out.println("\n" + "=".repeat(40) + "\n");

    System.out.println("Example 4: AAABBBABA (Advantage switching)");
    computer.processGame("AAABBBABA");
  }
}
