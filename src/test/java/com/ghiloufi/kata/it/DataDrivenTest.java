package com.ghiloufi.kata.it;

import static com.ghiloufi.kata.testutil.assertions.Assertions.assertGameState;
import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.testutil.base.TennisTestBase;
import com.ghiloufi.kata.testutil.data.TennisTestCase;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Tests pilotés par les données")
class DataDrivenTest extends TennisTestBase {

  @ParameterizedTest
  @MethodSource("provideTestCases")
  @DisplayName("Devrait traiter tous les scénarios de jeu de tennis avec succès")
  void should_process_all_tennis_game_scenarios_successfully(final TennisTestCase testCase) {

    System.out.println("Testing: " + testCase.description());

    testEnvironment.getComputer().processGame(testCase.input());

    assertGameState(testEnvironment.getComputer(), testCase.expectedState());
  }

  @Test
  @DisplayName("Devrait valider le format du fichier de cas de test")
  void should_validate_test_case_file_format() {
    var testCases = provideTestCases().toList();
    assertFalse(testCases.isEmpty(), "Should load test cases from file");
  }

  static Stream<TennisTestCase> provideTestCases() {
    return com.ghiloufi.kata.testutil.data.DataProvider.dataFileTestCases();
  }
}
