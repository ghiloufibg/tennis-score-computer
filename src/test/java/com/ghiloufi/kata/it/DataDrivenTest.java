package com.ghiloufi.kata.it;

import static com.ghiloufi.kata.testutil.assertions.Assertions.assertGameState;
import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.computer.TennisScoreComputer;
import com.ghiloufi.kata.testutil.data.TennisTestCase;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Tests pilotés par les données")
class DataDrivenTest {

  private TennisScoreComputer computer;

  static Stream<TennisTestCase> provideTestCases() {
    return com.ghiloufi.kata.testutil.data.DataProvider.dataFileTestCases();
  }

  @BeforeEach
  void setUp() {
    computer = new TennisScoreComputer();
  }

  @ParameterizedTest
  @MethodSource("provideTestCases")
  @DisplayName("Devrait traiter tous les scénarios de jeu de tennis avec succès")
  void should_process_all_tennis_game_scenarios_successfully(final TennisTestCase testCase) {

    System.out.println("Testing: " + testCase.description());

    computer.processGame(testCase.input());

    assertGameState(computer, testCase.expectedState());
  }

  @Test
  @DisplayName("Devrait valider le format du fichier de cas de test")
  void should_validate_test_case_file_format() {
    var testCases = provideTestCases().toList();
    assertFalse(testCases.isEmpty(), "Should load test cases from file");
  }
}
