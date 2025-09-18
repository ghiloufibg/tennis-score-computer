package com.ghiloufi.kata.domain.state;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.domain.model.Score;
import com.ghiloufi.kata.domain.model.ScoringSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests de l'état DeuceGameState")
class DeuceGameStateTest {

  private DeuceGameState deuceState;
  private ScoringSystem standardScoring;
  private Player playerA;
  private Player playerB;

  @BeforeEach
  void setUp() {
    deuceState = DeuceGameState.INSTANCE;
    standardScoring = ScoringSystem.STANDARD;
    playerA = new Player("A", Score.from(3));
    playerB = new Player("B", Score.from(3));
  }

  @Test
  @DisplayName("Devrait être un singleton")
  void should_be_singleton() {
    DeuceGameState instance1 = DeuceGameState.INSTANCE;
    DeuceGameState instance2 = DeuceGameState.INSTANCE;

    assertSame(instance1, instance2);
  }

  @Test
  @DisplayName("Le jeu ne devrait pas être terminé")
  void game_should_not_be_finished() {
    assertFalse(deuceState.isGameFinished());
  }

  @Test
  @DisplayName("Devrait retourner null comme gagnant")
  void should_return_null_as_winner() {
    assertNull(deuceState.getWinner());
  }

  @Test
  @DisplayName("Devrait avoir le bon nom d'affichage")
  void should_have_correct_display_name() {
    assertEquals("DEUCE", deuceState.getDisplayName());
  }

  @Test
  @DisplayName("toString devrait retourner le nom d'affichage")
  void toString_should_return_display_name() {
    assertEquals("DEUCE", deuceState.toString());
  }

  @Test
  @DisplayName("Devrait transitionner vers AdvantageGameState quand le joueur A marque")
  void should_transition_to_advantage_when_player_A_scores() {
    GameState nextState = deuceState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertInstanceOf(AdvantageGameState.class, nextState);
    AdvantageGameState advantageState = (AdvantageGameState) nextState;
    assertEquals(Player.A, advantageState.getPlayerWithAdvantage());
  }

  @Test
  @DisplayName("Devrait transitionner vers AdvantageGameState quand le joueur B marque")
  void should_transition_to_advantage_when_player_B_scores() {
    GameState nextState = deuceState.scorePoint(playerA, playerB, Point.PLAYER_B, standardScoring);

    assertInstanceOf(AdvantageGameState.class, nextState);
    AdvantageGameState advantageState = (AdvantageGameState) nextState;
    assertEquals(Player.B, advantageState.getPlayerWithAdvantage());
  }

  @Test
  @DisplayName("Devrait créer de nouvelles instances d'AdvantageGameState")
  void should_create_new_advantage_instances() {
    GameState advantageA1 =
        deuceState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    GameState advantageA2 =
        deuceState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertInstanceOf(AdvantageGameState.class, advantageA1);
    assertInstanceOf(AdvantageGameState.class, advantageA2);
    assertNotSame(advantageA1, advantageA2);

    AdvantageGameState advA1 = (AdvantageGameState) advantageA1;
    AdvantageGameState advA2 = (AdvantageGameState) advantageA2;
    assertEquals(advA1.getPlayerWithAdvantage(), advA2.getPlayerWithAdvantage());
  }

  @Test
  @DisplayName("Devrait être immutable")
  void should_be_immutable() {
    Player originalPlayerA = new Player("A", Score.from(3));
    Player originalPlayerB = new Player("B", Score.from(3));

    GameState nextState =
        deuceState.scorePoint(originalPlayerA, originalPlayerB, Point.PLAYER_A, standardScoring);

    assertInstanceOf(AdvantageGameState.class, nextState);
    assertNotSame(deuceState, nextState);

    assertEquals(3, originalPlayerA.score().points());
    assertEquals(3, originalPlayerB.score().points());
  }

  @Test
  @DisplayName("Devrait ignorer le système de scoring (non utilisé dans deuce)")
  void should_ignore_scoring_system() {
    ScoringSystem customScoring = new ScoringSystem(2, 3, 1, 2);

    GameState nextStateStandard =
        deuceState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    GameState nextStateCustom =
        deuceState.scorePoint(playerA, playerB, Point.PLAYER_A, customScoring);

    assertInstanceOf(AdvantageGameState.class, nextStateStandard);
    assertInstanceOf(AdvantageGameState.class, nextStateCustom);

    AdvantageGameState advStandard = (AdvantageGameState) nextStateStandard;
    AdvantageGameState advCustom = (AdvantageGameState) nextStateCustom;
    assertEquals(advStandard.getPlayerWithAdvantage(), advCustom.getPlayerWithAdvantage());
  }

  @Test
  @DisplayName("Devrait ignorer les scores des joueurs (logique basée uniquement sur le point)")
  void should_ignore_player_scores() {
    Player playerA_HighScore = new Player("A", Score.from(10));
    Player playerB_LowScore = new Player("B", Score.from(1));

    GameState nextState =
        deuceState.scorePoint(playerA_HighScore, playerB_LowScore, Point.PLAYER_B, standardScoring);

    assertInstanceOf(AdvantageGameState.class, nextState);
    AdvantageGameState advantageState = (AdvantageGameState) nextState;
    assertEquals(Player.B, advantageState.getPlayerWithAdvantage());
  }

  @Test
  @DisplayName("Devrait gérer les transitions multiples correctement")
  void should_handle_multiple_transitions_correctly() {
    GameState advantage1 = deuceState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    GameState advantage2 = deuceState.scorePoint(playerA, playerB, Point.PLAYER_B, standardScoring);
    GameState advantage3 = deuceState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertInstanceOf(AdvantageGameState.class, advantage1);
    assertInstanceOf(AdvantageGameState.class, advantage2);
    assertInstanceOf(AdvantageGameState.class, advantage3);

    assertEquals(Player.A, ((AdvantageGameState) advantage1).getPlayerWithAdvantage());
    assertEquals(Player.B, ((AdvantageGameState) advantage2).getPlayerWithAdvantage());
    assertEquals(Player.A, ((AdvantageGameState) advantage3).getPlayerWithAdvantage());
  }
}
