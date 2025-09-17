package com.ghiloufi.kata.testutil.builders;

import com.ghiloufi.kata.computer.TennisScoreComputer;
import com.ghiloufi.kata.display.ScoreboardDisplay;
import com.ghiloufi.kata.display.TerminalScoreRenderer;
import com.ghiloufi.kata.domain.Player;
import com.ghiloufi.kata.input.GameSequenceProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TennisTestBuilder {

  public static TestEnvironment createTestEnvironment() {
    return new TestEnvironment();
  }

  public static TestEnvironment createTestEnvironment(String gameSequence) {
    return new TestEnvironment(gameSequence);
  }

  public static Player playerWithPoints(String name, int points) {
    Player player = new Player(name);
    for (int i = 0; i < points; i++) {
      player.incrementPoints();
    }
    return player;
  }

  public static class TestEnvironment {
    private final TennisScoreComputer computer;
    private final List<String> capturedOutput;
    private final ScoreboardDisplay display;
    private final String gameSequence;

    public TestEnvironment() {
      this.capturedOutput = new ArrayList<>();
      Consumer<String> capturingConsumer = capturedOutput::add;
      this.display = new ScoreboardDisplay(capturingConsumer, new TerminalScoreRenderer());
      this.computer = new TennisScoreComputer(display);
      this.gameSequence = "A";
    }

    public TestEnvironment(String gameSequence) {
      this.capturedOutput = new ArrayList<>();
      Consumer<String> capturingConsumer = capturedOutput::add;
      this.display = new ScoreboardDisplay(capturingConsumer, new TerminalScoreRenderer());
      this.computer = new TennisScoreComputer(display);
      this.gameSequence = gameSequence;
    }

    public void playMatch() {
      computer.playMatch(GameSequenceProvider.fromString(gameSequence));
    }

    public TennisScoreComputer getComputer() {
      return computer;
    }

    public List<String> getCapturedOutput() {
      return capturedOutput;
    }

    public String[] getOutputAsArray() {
      return capturedOutput.toArray(String[]::new);
    }

    public ScoreboardDisplay getDisplay() {
      return display;
    }
  }
}
