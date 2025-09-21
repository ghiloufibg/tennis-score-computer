package com.ghiloufi.kata.presentation.cli;

import com.ghiloufi.kata.application.service.ScoreComputer;
import com.ghiloufi.kata.domain.model.MatchNotation;
import com.ghiloufi.kata.presentation.display.ScoreboardDisplay;
import com.ghiloufi.kata.presentation.display.TerminalScoreRenderer;

public class ScoreCounterApplication {

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Usage: java ScoreCounterApplication <game_sequence>");
      System.out.println("Example: java ScoreCounterApplication ABABAA");
      return;
    }

    final var gameSequence = args[0];

    final var display = new ScoreboardDisplay(System.out::println, new TerminalScoreRenderer());

    ScoreComputer scoreComputer = new ScoreComputer(display);

    scoreComputer.playMatch(MatchNotation.from(gameSequence).points().iterator());
  }
}
