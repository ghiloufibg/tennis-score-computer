package com.ghiloufi.kata.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Tests du Value Object ScoringSystem")
class ScoringSystemTest {

  private static final ScoringSystem STANDARD_SCORING = ScoringSystem.STANDARD;

  @Test
  @DisplayName("Devrait créer un système de score standard avec les bonnes valeurs")
  void should_create_standard_scoring_system_with_correct_values() {
    assertEquals(3, STANDARD_SCORING.minimumPointsForDeuce());
    assertEquals(4, STANDARD_SCORING.minimumPointsForWin());
    assertEquals(1, STANDARD_SCORING.advantagePointDifference());
    assertEquals(2, STANDARD_SCORING.winPointDifference());
  }

  @Test
  @DisplayName("Devrait créer un système de score personnalisé")
  void should_create_custom_scoring_system() {
    ScoringSystem customScoring = new ScoringSystem(2, 3, 1, 2);

    assertEquals(2, customScoring.minimumPointsForDeuce());
    assertEquals(3, customScoring.minimumPointsForWin());
    assertEquals(1, customScoring.advantagePointDifference());
    assertEquals(2, customScoring.winPointDifference());
  }

  @ParameterizedTest
  @DisplayName("Devrait retourner true pour isDeuce quand les conditions sont remplies")
  @CsvSource({"3, 3", "4, 4", "5, 5", "10, 10"})
  void should_return_true_for_isDeuce_when_conditions_met(int pointsA, int pointsB) {
    assertTrue(STANDARD_SCORING.isDeuce(pointsA, pointsB));
  }

  @ParameterizedTest
  @DisplayName("Devrait retourner false pour isDeuce quand les conditions ne sont pas remplies")
  @CsvSource({"0, 0", "1, 1", "2, 2", "3, 2", "2, 3", "4, 3", "3, 4", "5, 4", "4, 5"})
  void should_return_false_for_isDeuce_when_conditions_not_met(int pointsA, int pointsB) {
    assertFalse(STANDARD_SCORING.isDeuce(pointsA, pointsB));
  }

  @ParameterizedTest
  @DisplayName("Devrait retourner true pour hasAdvantage quand le joueur a un point d'avance")
  @CsvSource({"4, 3", "5, 4", "6, 5", "10, 9"})
  void should_return_true_for_hasAdvantage_when_player_has_one_point_lead(
      int playerPoints, int opponentPoints) {
    assertTrue(STANDARD_SCORING.hasAdvantage(playerPoints, opponentPoints));
  }

  @ParameterizedTest
  @DisplayName(
      "Devrait retourner false pour hasAdvantage quand les conditions ne sont pas remplies")
  @CsvSource({"0, 0", "1, 0", "2, 0", "3, 0", "3, 3", "4, 4", "4, 2", "5, 3", "3, 4"})
  void should_return_false_for_hasAdvantage_when_conditions_not_met(
      int playerPoints, int opponentPoints) {
    assertFalse(STANDARD_SCORING.hasAdvantage(playerPoints, opponentPoints));
  }

  @ParameterizedTest
  @DisplayName("Devrait retourner true pour hasWonGame quand le joueur a gagné")
  @CsvSource({"4, 0", "4, 1", "4, 2", "5, 3", "6, 4", "10, 8"})
  void should_return_true_for_hasWonGame_when_player_has_won(int playerPoints, int opponentPoints) {
    assertTrue(STANDARD_SCORING.hasWonGame(playerPoints, opponentPoints));
  }

  @ParameterizedTest
  @DisplayName("Devrait retourner false pour hasWonGame quand le joueur n'a pas gagné")
  @CsvSource({"0, 0", "1, 0", "2, 0", "3, 0", "3, 1", "3, 2", "3, 3", "4, 3", "4, 4", "5, 4"})
  void should_return_false_for_hasWonGame_when_player_has_not_won(
      int playerPoints, int opponentPoints) {
    assertFalse(STANDARD_SCORING.hasWonGame(playerPoints, opponentPoints));
  }

  @Test
  @DisplayName("Devrait fonctionner avec un système de score personnalisé pour deuce")
  void should_work_with_custom_scoring_system_for_deuce() {
    ScoringSystem customScoring = new ScoringSystem(2, 3, 1, 2);

    assertTrue(customScoring.isDeuce(2, 2));
    assertTrue(customScoring.isDeuce(3, 3));
    assertFalse(customScoring.isDeuce(1, 1));
    assertFalse(customScoring.isDeuce(2, 1));
  }

  @Test
  @DisplayName("Devrait fonctionner avec un système de score personnalisé pour avantage")
  void should_work_with_custom_scoring_system_for_advantage() {
    ScoringSystem customScoring = new ScoringSystem(2, 3, 1, 2);

    assertTrue(customScoring.hasAdvantage(3, 2));
    assertTrue(customScoring.hasAdvantage(4, 3));
    assertFalse(customScoring.hasAdvantage(2, 1));
    assertFalse(customScoring.hasAdvantage(2, 2));
  }

  @Test
  @DisplayName("Devrait fonctionner avec un système de score personnalisé pour victoire")
  void should_work_with_custom_scoring_system_for_win() {
    ScoringSystem customScoring = new ScoringSystem(2, 3, 1, 2);

    assertTrue(customScoring.hasWonGame(3, 0));
    assertTrue(customScoring.hasWonGame(3, 1));
    assertTrue(customScoring.hasWonGame(4, 2));
    assertFalse(customScoring.hasWonGame(2, 1));
    assertFalse(customScoring.hasWonGame(3, 2));
  }
}
