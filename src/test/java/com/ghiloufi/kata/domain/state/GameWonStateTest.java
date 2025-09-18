package com.ghiloufi.kata.domain.state;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.domain.model.Score;
import com.ghiloufi.kata.domain.model.ScoringSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests de l'état GameWonState")
class GameWonStateTest {

  private ScoringSystem standardScoring;
  private Player playerA;
  private Player playerB;

  @BeforeEach
  void setUp() {
    standardScoring = ScoringSystem.STANDARD;
    playerA = new Player("A", Score.from(4));
    playerB = new Player("B", Score.from(2));
  }

  @Test
  @DisplayName("Devrait créer un état de victoire pour le joueur A")
  void should_create_game_won_state_for_player_A() {
    GameWonState gameWonState = new GameWonState(Player.A);

    assertEquals(Player.A, gameWonState.getWinner());
    assertEquals("GAME_WON_A", gameWonState.getDisplayName());
  }

  @Test
  @DisplayName("Devrait créer un état de victoire pour le joueur B")
  void should_create_game_won_state_for_player_B() {
    GameWonState gameWonState = new GameWonState(Player.B);

    assertEquals(Player.B, gameWonState.getWinner());
    assertEquals("GAME_WON_B", gameWonState.getDisplayName());
  }

  @Test
  @DisplayName("Ne devrait pas accepter de gagnant null")
  void should_not_accept_null_winner() {
    assertThrows(NullPointerException.class, () -> new GameWonState(null));
  }

  @Test
  @DisplayName("Le jeu devrait être terminé")
  void game_should_be_finished() {
    GameWonState gameWonState = new GameWonState(Player.A);
    assertTrue(gameWonState.isGameFinished());
  }

  @Test
  @DisplayName("toString devrait retourner le nom d'affichage")
  void toString_should_return_display_name() {
    GameWonState gameWonA = new GameWonState(Player.A);
    GameWonState gameWonB = new GameWonState(Player.B);

    assertEquals("GAME_WON_A", gameWonA.toString());
    assertEquals("GAME_WON_B", gameWonB.toString());
  }

  @Test
  @DisplayName("Devrait toujours retourner le même état après un point marqué")
  void should_always_return_same_state_after_scoring_point() {
    GameWonState gameWonState = new GameWonState(Player.A);

    GameState nextStateA =
        gameWonState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    GameState nextStateB =
        gameWonState.scorePoint(playerA, playerB, Point.PLAYER_B, standardScoring);

    assertSame(gameWonState, nextStateA);
    assertSame(gameWonState, nextStateB);
  }

  @Test
  @DisplayName("Devrait ignorer tous les paramètres lors du marquage de points")
  void should_ignore_all_parameters_when_scoring_points() {
    GameWonState gameWonState = new GameWonState(Player.A);

    Player differentPlayerA = new Player("Alice", Score.from(0));
    Player differentPlayerB = new Player("Bob", Score.from(10));

    ScoringSystem customScoring = new ScoringSystem(2, 3, 1, 2);

    GameState nextState =
        gameWonState.scorePoint(differentPlayerA, differentPlayerB, Point.PLAYER_B, customScoring);

    assertSame(gameWonState, nextState);
    assertEquals(Player.A, gameWonState.getWinner());
  }

  @Test
  @DisplayName("Devrait implémenter equals correctement")
  void should_implement_equals_correctly() {
    GameWonState gameWonA1 = new GameWonState(Player.A);
    GameWonState gameWonA2 = new GameWonState(Player.A);
    GameWonState gameWonB = new GameWonState(Player.B);

    assertEquals(gameWonA1, gameWonA2);

    assertNotEquals(gameWonA1, gameWonB);

    assertNotEquals(gameWonA1, null);

    assertNotEquals(gameWonA1, "GAME_WON_A");

    assertEquals(gameWonA1, gameWonA1);
  }

  @Test
  @DisplayName("Devrait implémenter hashCode correctement")
  void should_implement_hashCode_correctly() {
    GameWonState gameWonA1 = new GameWonState(Player.A);
    GameWonState gameWonA2 = new GameWonState(Player.A);
    GameWonState gameWonB = new GameWonState(Player.B);

    assertEquals(gameWonA1.hashCode(), gameWonA2.hashCode());

    assertNotEquals(gameWonA1.hashCode(), gameWonB.hashCode());
  }

  @Test
  @DisplayName("Devrait être immutable")
  void should_be_immutable() {
    GameWonState gameWonState = new GameWonState(Player.A);
    Player originalPlayerA = new Player("A", Score.from(4));
    Player originalPlayerB = new Player("B", Score.from(2));

    GameState nextState =
        gameWonState.scorePoint(originalPlayerA, originalPlayerB, Point.PLAYER_B, standardScoring);

    assertSame(gameWonState, nextState);
    assertEquals(Player.A, gameWonState.getWinner());

    assertEquals(4, originalPlayerA.score().points());
    assertEquals(2, originalPlayerB.score().points());
  }

  @Test
  @DisplayName("Devrait maintenir la cohérence entre getWinner et getDisplayName")
  void should_maintain_consistency_between_winner_and_display_name() {
    GameWonState gameWonA = new GameWonState(Player.A);
    GameWonState gameWonB = new GameWonState(Player.B);

    assertEquals(Player.A, gameWonA.getWinner());
    assertEquals("GAME_WON_A", gameWonA.getDisplayName());

    assertEquals(Player.B, gameWonB.getWinner());
    assertEquals("GAME_WON_B", gameWonB.getDisplayName());
  }

  @Test
  @DisplayName("Devrait gérer de multiples tentatives de marquage de points")
  void should_handle_multiple_scoring_attempts() {
    GameWonState gameWonState = new GameWonState(Player.A);

    GameState state1 = gameWonState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    GameState state2 = state1.scorePoint(playerA, playerB, Point.PLAYER_B, standardScoring);
    GameState state3 = state2.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertSame(gameWonState, state1);
    assertSame(gameWonState, state2);
    assertSame(gameWonState, state3);

    assertEquals(Player.A, gameWonState.getWinner());
    assertTrue(gameWonState.isGameFinished());
  }

  @Test
  @DisplayName("Devrait fonctionner avec des joueurs personnalisés")
  void should_work_with_custom_players() {
    Player customPlayerA = new Player("Alice", Score.from(0));
    Player customPlayerB = new Player("Bob", Score.from(0));

    GameWonState gameWonCustomA = new GameWonState(customPlayerA);
    GameWonState gameWonCustomB = new GameWonState(customPlayerB);

    assertEquals(customPlayerA, gameWonCustomA.getWinner());
    assertEquals(customPlayerB, gameWonCustomB.getWinner());

    assertNotNull(gameWonCustomA.getDisplayName());
    assertNotNull(gameWonCustomB.getDisplayName());
  }

  @Test
  @DisplayName("Devrait gérer l'état terminal correctement")
  void should_handle_terminal_state_correctly() {
    GameWonState gameWonState = new GameWonState(Player.A);

    assertTrue(gameWonState.isGameFinished());

    GameState sameState =
        gameWonState.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    assertSame(gameWonState, sameState);

    assertEquals(Player.A, gameWonState.getWinner());
    assertTrue(gameWonState.isGameFinished());
  }

  @Test
  @DisplayName("Devrait créer des instances distinctes pour le même gagnant")
  void should_create_distinct_instances_for_same_winner() {
    GameWonState gameWon1 = new GameWonState(Player.A);
    GameWonState gameWon2 = new GameWonState(Player.A);

    assertNotSame(gameWon1, gameWon2);

    assertEquals(gameWon1, gameWon2);
    assertEquals(gameWon1.getWinner(), gameWon2.getWinner());
    assertEquals(gameWon1.getDisplayName(), gameWon2.getDisplayName());
  }
}
