package com.ghiloufi.kata.testutil.builders;

import com.ghiloufi.kata.application.service.ScoreComputer;
import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.infrastructure.input.GameSequenceProvider;
import com.ghiloufi.kata.presentation.display.ScoreboardDisplay;
import com.ghiloufi.kata.presentation.display.TerminalScoreRenderer;
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
    Player player = Player.withName(name);
    for (int i = 0; i < points; i++) {
      player = player.scorePoint();
    }
    return player;
  }

  public static class TestEnvironment {
    private final ScoreComputer computer;
    private final List<String> capturedOutput;
    private final ScoreboardDisplay display;
    private final String gameSequence;

    public TestEnvironment() {
      this.capturedOutput = new ArrayList<>();
      Consumer<String> capturingConsumer = capturedOutput::add;
      this.display = new ScoreboardDisplay(capturingConsumer, new TerminalScoreRenderer());
      this.computer = new ScoreComputer(display);
      this.gameSequence = "A";
    }

    public TestEnvironment(String gameSequence) {
      this.capturedOutput = new ArrayList<>();
      Consumer<String> capturingConsumer = capturedOutput::add;
      this.display = new ScoreboardDisplay(capturingConsumer, new TerminalScoreRenderer());
      this.computer = new ScoreComputer(display);
      this.gameSequence = gameSequence;
    }

    public void playMatch() {
      computer.playMatch(GameSequenceProvider.fromString(gameSequence));
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
