package com.ghiloufi.kata.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.model.ScoringSystem;
import com.ghiloufi.kata.domain.state.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Tests du service de transition d'état GameStateTransitioner")
class GameStateTransitionerTest {

  private static final ScoringSystem STANDARD_SCORING = ScoringSystem.STANDARD;

  @Test
  @DisplayName("Devrait retourner InProgressGameState pour les scores initiaux")
  void should_return_in_progress_for_initial_scores() {
    GameState result = GameStateTransitioner.nextState(0, 0, STANDARD_SCORING);
    assertInstanceOf(InProgressGameState.class, result);
  }

  @ParameterizedTest
  @DisplayName("Devrait retourner InProgressGameState pour les scores normaux")
  @CsvSource({"0, 1", "1, 0", "1, 1", "2, 0", "0, 2", "2, 1", "1, 2", "2, 2"})
  void should_return_in_progress_for_normal_scores(int pointsA, int pointsB) {
    GameState result = GameStateTransitioner.nextState(pointsA, pointsB, STANDARD_SCORING);
    assertInstanceOf(InProgressGameState.class, result);
  }

  @Test
  @DisplayName("Devrait retourner DeuceGameState quand les deux joueurs ont 3 points")
  void should_return_deuce_when_both_players_have_3_points() {
    GameState result = GameStateTransitioner.nextState(3, 3, STANDARD_SCORING);
    assertInstanceOf(DeuceGameState.class, result);
  }

  @ParameterizedTest
  @DisplayName("Devrait retourner DeuceGameState pour égalité au-delà de 3 points")
  @CsvSource({"4, 4", "5, 5", "6, 6", "10, 10"})
  void should_return_deuce_for_tied_scores_beyond_3(int pointsA, int pointsB) {
    GameState result = GameStateTransitioner.nextState(pointsA, pointsB, STANDARD_SCORING);
    assertInstanceOf(DeuceGameState.class, result);
  }

  @Test
  @DisplayName("Devrait retourner AdvantageGameState pour le joueur A avec avantage")
  void should_return_advantage_for_player_A() {
    GameState result = GameStateTransitioner.nextState(4, 3, STANDARD_SCORING);

    assertInstanceOf(AdvantageGameState.class, result);
    AdvantageGameState advantageState = (AdvantageGameState) result;
    assertEquals(Player.A, advantageState.getPlayerWithAdvantage());
  }

  @Test
  @DisplayName("Devrait retourner AdvantageGameState pour le joueur B avec avantage")
  void should_return_advantage_for_player_B() {
    GameState result = GameStateTransitioner.nextState(3, 4, STANDARD_SCORING);

    assertInstanceOf(AdvantageGameState.class, result);
    AdvantageGameState advantageState = (AdvantageGameState) result;
    assertEquals(Player.B, advantageState.getPlayerWithAdvantage());
  }

  @ParameterizedTest
  @DisplayName("Devrait retourner AdvantageGameState pour différents scores d'avantage")
  @CsvSource({"5, 4, A", "4, 5, B", "6, 5, A", "5, 6, B", "8, 7, A", "7, 8, B"})
  void should_return_advantage_for_various_advantage_scores(
      int pointsA, int pointsB, String expectedPlayer) {
    GameState result = GameStateTransitioner.nextState(pointsA, pointsB, STANDARD_SCORING);

    assertInstanceOf(AdvantageGameState.class, result);
    AdvantageGameState advantageState = (AdvantageGameState) result;

    Player expectedWinner = "A".equals(expectedPlayer) ? Player.A : Player.B;
    assertEquals(expectedWinner, advantageState.getPlayerWithAdvantage());
  }

  @Test
  @DisplayName("Devrait retourner GameWonState quand le joueur A gagne avec 4-0")
  void should_return_game_won_when_player_A_wins_4_0() {
    GameState result = GameStateTransitioner.nextState(4, 0, STANDARD_SCORING);

    assertInstanceOf(GameWonState.class, result);
    GameWonState gameWonState = (GameWonState) result;
    assertEquals(Player.A, gameWonState.getWinner());
  }

  @Test
  @DisplayName("Devrait retourner GameWonState quand le joueur B gagne avec 0-4")
  void should_return_game_won_when_player_B_wins_0_4() {
    GameState result = GameStateTransitioner.nextState(0, 4, STANDARD_SCORING);

    assertInstanceOf(GameWonState.class, result);
    GameWonState gameWonState = (GameWonState) result;
    assertEquals(Player.B, gameWonState.getWinner());
  }

  @ParameterizedTest
  @DisplayName("Devrait retourner GameWonState pour victoires normales")
  @CsvSource({"4, 0, A", "4, 1, A", "4, 2, A", "0, 4, B", "1, 4, B", "2, 4, B"})
  void should_return_game_won_for_normal_wins(int pointsA, int pointsB, String expectedWinner) {
    GameState result = GameStateTransitioner.nextState(pointsA, pointsB, STANDARD_SCORING);

    assertInstanceOf(GameWonState.class, result);
    GameWonState gameWonState = (GameWonState) result;

    Player expectedPlayer = "A".equals(expectedWinner) ? Player.A : Player.B;
    assertEquals(expectedPlayer, gameWonState.getWinner());
  }

  @ParameterizedTest
  @DisplayName("Devrait retourner GameWonState pour victoires avec 2 points d'écart")
  @CsvSource({"5, 3, A", "6, 4, A", "8, 6, A", "3, 5, B", "4, 6, B", "6, 8, B"})
  void should_return_game_won_for_two_point_margin_wins(
      int pointsA, int pointsB, String expectedWinner) {
    GameState result = GameStateTransitioner.nextState(pointsA, pointsB, STANDARD_SCORING);

    assertInstanceOf(GameWonState.class, result);
    GameWonState gameWonState = (GameWonState) result;

    Player expectedPlayer = "A".equals(expectedWinner) ? Player.A : Player.B;
    assertEquals(expectedPlayer, gameWonState.getWinner());
  }

  @Test
  @DisplayName("Devrait gérer les scores élevés correctement")
  void should_handle_high_scores_correctly() {
    GameState deuceResult = GameStateTransitioner.nextState(15, 15, STANDARD_SCORING);
    assertInstanceOf(DeuceGameState.class, deuceResult);

    GameState advantageResult = GameStateTransitioner.nextState(15, 14, STANDARD_SCORING);
    assertInstanceOf(AdvantageGameState.class, advantageResult);

    GameState winResult = GameStateTransitioner.nextState(15, 13, STANDARD_SCORING);
    assertInstanceOf(GameWonState.class, winResult);
  }

  @Test
  @DisplayName("Devrait utiliser le système de scoring fourni")
  void should_use_provided_scoring_system() {
    ScoringSystem customScoring = new ScoringSystem(2, 3, 1, 2);

    GameState deuceResult = GameStateTransitioner.nextState(2, 2, customScoring);
    assertInstanceOf(DeuceGameState.class, deuceResult);

    GameState winResult = GameStateTransitioner.nextState(3, 0, customScoring);
    assertInstanceOf(GameWonState.class, winResult);
  }
}
