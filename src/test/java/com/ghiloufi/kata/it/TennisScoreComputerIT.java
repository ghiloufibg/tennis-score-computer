package com.ghiloufi.kata.it;

import static com.ghiloufi.kata.testutil.assertions.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.computer.TennisScoreComputer;
import com.ghiloufi.kata.testutil.helpers.OutputCapture;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Tests d'intégration du calculateur de score de tennis")
class TennisScoreComputerIT {

  private TennisScoreComputer computer;
  private OutputCapture outputCapture;

  @BeforeEach
  void setUp() {
    computer = new TennisScoreComputer();
    outputCapture = new OutputCapture();
  }

  @AfterEach
  void tearDown() {
    outputCapture.restore();
  }

  @Test
  @DisplayName("Devrait traiter un scénario de jeu complet avec sortie console correcte")
  void should_process_complete_game_scenario_successfully() {
    computer.processGame("ABABAA");

    String[] outputLines = outputCapture.getCapturedOutputLines();

    String[] expectedLines = {
      "Player A : 15 / Player B : 0",
      "Player A : 15 / Player B : 15",
      "Player A : 30 / Player B : 15",
      "Player A : 30 / Player B : 30",
      "Player A : 40 / Player B : 30",
      "Player A wins the game"
    };

    assertOutputLinesMatch(expectedLines, outputLines);
    assertGameEndsWithWin(computer);
  }

  @ParameterizedTest
  @DisplayName("Devrait produire une sortie correcte pour différents scénarios de jeu")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#gameScenarioExpectedOutputs")
  void should_produce_correct_output_for_game_scenarios(String input, String[] expectedOutput) {
    computer.processGame(input);

    String[] actualOutput = outputCapture.getCapturedOutputLines();
    assertOutputLinesMatch(expectedOutput, actualOutput);
  }

  @Test
  @DisplayName("Devrait gérer un scénario complexe d'égalité avec plusieurs avantages")
  void should_handle_complete_deuce_scenario_correctly() {
    computer.processGame("AAABBBABAB");

    String[] outputLines = outputCapture.getCapturedOutputLines();

    assertTrue(containsDeuce(outputLines), "La sortie devrait contenir au moins un deuce");
    assertTrue(containsAdvantage(outputLines), "La sortie devrait contenir au moins un avantage");
    assertGameInProgress(computer);
  }

  private boolean containsDeuce(String[] outputLines) {
    return Stream.of(outputLines).anyMatch(line -> line.contains("(Deuce)"));
  }

  private boolean containsAdvantage(String[] outputLines) {
    return Stream.of(outputLines).anyMatch(line -> line.contains("(Advantage Player"));
  }
}
