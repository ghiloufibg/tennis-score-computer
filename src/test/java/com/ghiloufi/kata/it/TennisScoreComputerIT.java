package com.ghiloufi.kata.it;

import static com.ghiloufi.kata.testutil.assertions.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.testutil.base.TennisTestBase;
import com.ghiloufi.kata.testutil.builders.TennisTestBuilder;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Tests d'intégration du calculateur de score de tennis")
class TennisScoreComputerIT extends TennisTestBase {

  @Test
  @DisplayName("Devrait traiter un scénario de jeu complet avec sortie console correcte")
  void should_process_complete_game_scenario_successfully() {
    var testEnv = TennisTestBuilder.createTestEnvironment("ABABAA");
    testEnv.playMatch();

    String[] outputLines = testEnv.getOutputAsArray();

    String[] expectedLines = {
      "Player A : 15 / Player B : 0",
      "Player A : 15 / Player B : 15",
      "Player A : 30 / Player B : 15",
      "Player A : 30 / Player B : 30",
      "Player A : 40 / Player B : 30",
      "Player A wins the game"
    };

    assertOutputLinesMatch(expectedLines, outputLines);
    assertGameEndsWithWin(outputLines);
  }

  @ParameterizedTest
  @DisplayName("Devrait produire une sortie correcte pour différents scénarios de jeu")
  @MethodSource("com.ghiloufi.kata.testutil.data.DataProvider#gameScenarioExpectedOutputs")
  void should_produce_correct_output_for_game_scenarios(String input, String[] expectedOutput) {
    var testEnv = TennisTestBuilder.createTestEnvironment(input);
    testEnv.playMatch();

    String[] actualOutput = testEnv.getOutputAsArray();
    assertOutputLinesMatch(expectedOutput, actualOutput);
  }

  @Test
  @DisplayName("Devrait gérer un scénario complexe d'égalité avec plusieurs avantages")
  void should_handle_complete_deuce_scenario_correctly() {
    var testEnv = TennisTestBuilder.createTestEnvironment("AAABBBA");
    testEnv.playMatch();

    String[] outputLines = testEnv.getOutputAsArray();

    assertTrue(containsDeuce(outputLines), "La sortie devrait contenir au moins un deuce");
    assertTrue(containsAdvantage(outputLines), "La sortie devrait contenir au moins un avantage");
    assertGameInProgress(outputLines);
  }

  private boolean containsDeuce(String[] outputLines) {
    return Stream.of(outputLines).anyMatch(line -> line.contains("(Deuce)"));
  }

  private boolean containsAdvantage(String[] outputLines) {
    return Stream.of(outputLines).anyMatch(line -> line.contains("(Advantage Player"));
  }
}
