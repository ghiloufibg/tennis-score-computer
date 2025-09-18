package com.ghiloufi.kata.domain.state;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.domain.model.Score;
import com.ghiloufi.kata.domain.model.ScoringSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Tests de l'état InProgressGameState")
class InProgressGameStateTest {

  private InProgressGameState inProgressState;
  private ScoringSystem standardScoring;

  @BeforeEach
  void setUp() {
    inProgressState = InProgressGameState.INSTANCE;
    standardScoring = ScoringSystem.STANDARD;
  }

  @Test
  @DisplayName("Devrait être un singleton")
  void should_be_singleton() {
    InProgressGameState instance1 = InProgressGameState.INSTANCE;
    InProgressGameState instance2 = InProgressGameState.INSTANCE;

    assertSame(instance1, instance2);
  }

  @Test
  @DisplayName("Le jeu ne devrait pas être terminé")
  void game_should_not_be_finished() {
    assertFalse(inProgressState.isGameFinished());
  }

  @Test
  @DisplayName("Devrait retourner null comme gagnant")
  void should_return_null_as_winner() {
    assertNull(inProgressState.getWinner());
  }

  @Test
  @DisplayName("Devrait avoir le bon nom d'affichage")
  void should_have_correct_display_name() {
    assertEquals("IN_PROGRESS", inProgressState.getDisplayName());
  }

  @Test
  @DisplayName("toString devrait retourner le nom d'affichage")
  void toString_should_return_display_name() {
    assertEquals("IN_PROGRESS", inProgressState.toString());
  }

  @ParameterizedTest
  @DisplayName("Devrait rester InProgressGameState pour les scores normaux")
  @CsvSource({"0, 0", "1, 0", "0, 1", "1, 1", "2, 0", "0, 2", "2, 1", "1, 2", "2, 2"})
  void should_remain_in_progress_for_normal_scores(int pointsA, int pointsB) {
    Player playerA = new Player("A", Score.from(pointsA));
    Player playerB = new Player("B", Score.from(pointsB));

    GameState nextState =
        inProgressState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertSame(inProgressState, nextState);
  }

  @Test
  @DisplayName("Devrait transitionner vers DeuceGameState quand deuce est atteint")
  void should_transition_to_deuce_when_deuce_reached() {
    Player playerA = new Player("A", Score.from(3));
    Player playerB = new Player("B", Score.from(3));

    GameState nextState =
        inProgressState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertInstanceOf(DeuceGameState.class, nextState);
  }

  @Test
  @DisplayName("Devrait transitionner vers AdvantageGameState quand avantage est atteint")
  void should_transition_to_advantage_when_advantage_reached() {
    Player playerA = new Player("A", Score.from(4));
    Player playerB = new Player("B", Score.from(3));

    GameState nextState =
        inProgressState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertInstanceOf(AdvantageGameState.class, nextState);
  }

  @Test
  @DisplayName("Devrait transitionner vers GameWonState quand victoire normale est atteinte")
  void should_transition_to_game_won_for_normal_win() {
    Player playerA = new Player("A", Score.from(4));
    Player playerB = new Player("B", Score.from(2));

    GameState nextState =
        inProgressState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertInstanceOf(GameWonState.class, nextState);
    assertEquals(Player.A, nextState.getWinner());
  }

  @ParameterizedTest
  @DisplayName("Devrait transitionner correctement selon le point marqué")
  @CsvSource({
    "3, 2, A, InProgressGameState",
    "3, 3, A, DeuceGameState",
    "4, 2, A, GameWonState",
    "4, 3, A, AdvantageGameState"
  })
  void should_transition_correctly_based_on_scored_point(
      int pointsA, int pointsB, String scoringPlayer, String expectedStateType) {
    Player playerA = new Player("A", Score.from(pointsA));
    Player playerB = new Player("B", Score.from(pointsB));
    Point point = "A".equals(scoringPlayer) ? Point.PLAYER_A : Point.PLAYER_B;

    GameState nextState = inProgressState.scorePoint(playerA, playerB, point, standardScoring);

    switch (expectedStateType) {
      case "InProgressGameState" -> assertSame(inProgressState, nextState);
      case "DeuceGameState" -> assertInstanceOf(DeuceGameState.class, nextState);
      case "GameWonState" -> assertInstanceOf(GameWonState.class, nextState);
      case "AdvantageGameState" -> assertInstanceOf(AdvantageGameState.class, nextState);
      default -> fail("Type d'état inattendu: " + expectedStateType);
    }
  }

  @Test
  @DisplayName("Devrait utiliser le système de scoring fourni")
  void should_use_provided_scoring_system() {
    ScoringSystem customScoring = new ScoringSystem(2, 3, 1, 2);
    Player playerA = new Player("A", Score.from(2));
    Player playerB = new Player("B", Score.from(2));

    GameState nextState =
        inProgressState.scorePoint(playerA, playerB, Point.PLAYER_A, customScoring);

    assertInstanceOf(DeuceGameState.class, nextState);
  }

  @Test
  @DisplayName("Devrait gérer différents points correctement")
  void should_handle_different_points_correctly() {
    Player playerA = new Player("A", Score.from(1));
    Player playerB = new Player("B", Score.from(1));

    GameState nextStateA =
        inProgressState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    assertSame(inProgressState, nextStateA);

    GameState nextStateB =
        inProgressState.scorePoint(playerA, playerB, Point.PLAYER_B, standardScoring);
    assertSame(inProgressState, nextStateB);
  }

  @Test
  @DisplayName("Devrait maintenir l'immutabilité")
  void should_maintain_immutability() {
    Player originalPlayerA = new Player("A", Score.from(1));
    Player originalPlayerB = new Player("B", Score.from(1));

    GameState nextState =
        inProgressState.scorePoint(
            originalPlayerA, originalPlayerB, Point.PLAYER_A, standardScoring);

    assertSame(inProgressState, nextState);
    assertEquals(1, originalPlayerA.score().points());
    assertEquals(1, originalPlayerB.score().points());
  }
}
