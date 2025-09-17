package com.ghiloufi.kata.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.testutil.helpers.GameStateMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests du TennisGame Aggregate Root")
class TennisGameTest {

  @Test
  @DisplayName("Devrait créer un nouveau jeu")
  void should_create_new_game() {
    TennisGame game = TennisGame.newGame();

    assertEquals(Player.A.name(), game.getPlayerA().name());
    assertEquals(Player.B.name(), game.getPlayerB().name());
    assertTrue(GameStateMatchers.isInProgress(game.getGameState()));
    assertEquals(Score.from(0), game.getPlayerA().score());
    assertEquals(Score.from(0), game.getPlayerB().score());
  }

  @Test
  @DisplayName("Devrait créer un jeu avec noms personnalisés")
  void should_create_game_with_custom_names() {
    TennisGame game = TennisGame.withPlayers("Alice", "Bob");

    assertEquals("Alice", game.getPlayerA().name());
    assertEquals("Bob", game.getPlayerB().name());
    assertTrue(GameStateMatchers.isInProgress(game.getGameState()));
  }

  @Test
  @DisplayName("Devrait marquer un point pour le joueur A")
  void should_score_point_for_player_A() {
    TennisGame game = TennisGame.newGame();

    TennisGame newGame = game.scorePoint(Point.PLAYER_A);

    assertEquals(Score.from(1), newGame.getPlayerA().score());
    assertEquals(Score.from(0), newGame.getPlayerB().score());
    assertTrue(GameStateMatchers.isInProgress(newGame.getGameState()));
  }

  @Test
  @DisplayName("Devrait marquer un point pour le joueur B")
  void should_score_point_for_player_B() {
    TennisGame game = TennisGame.newGame();

    TennisGame newGame = game.scorePoint(Point.PLAYER_B);

    assertEquals(Score.from(0), newGame.getPlayerA().score());
    assertEquals(Score.from(1), newGame.getPlayerB().score());
    assertTrue(GameStateMatchers.isInProgress(newGame.getGameState()));
  }

  @Test
  @DisplayName("Devrait atteindre l'état deuce")
  void should_reach_deuce_state() {
    TennisGame game =
        TennisGame.newGame()
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B);

    assertTrue(GameStateMatchers.isDeuce(game.getGameState()));
    assertEquals(Score.from(3), game.getPlayerA().score());
    assertEquals(Score.from(3), game.getPlayerB().score());
  }

  @Test
  @DisplayName("Devrait donner l'avantage au joueur A")
  void should_give_advantage_to_player_A() {
    TennisGame game =
        TennisGame.newGame()
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_A);

    assertTrue(GameStateMatchers.isAdvantageA(game.getGameState()));
    assertEquals(Score.from(4), game.getPlayerA().score());
    assertEquals(Score.from(3), game.getPlayerB().score());
  }

  @Test
  @DisplayName("Devrait donner l'avantage au joueur B")
  void should_give_advantage_to_player_B() {
    TennisGame game =
        TennisGame.newGame()
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B);

    assertTrue(GameStateMatchers.isAdvantageB(game.getGameState()));
    assertEquals(Score.from(3), game.getPlayerA().score());
    assertEquals(Score.from(4), game.getPlayerB().score());
  }

  @Test
  @DisplayName("Devrait permettre au joueur A de gagner")
  void should_allow_player_A_to_win() {
    TennisGame game =
        TennisGame.newGame()
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A);

    assertTrue(GameStateMatchers.isGameWonA(game.getGameState()));
    assertTrue(game.isGameFinished());
  }

  @Test
  @DisplayName("Devrait permettre au joueur B de gagner")
  void should_allow_player_B_to_win() {
    TennisGame game =
        TennisGame.newGame()
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B);

    assertTrue(GameStateMatchers.isGameWonB(game.getGameState()));
    assertTrue(game.isGameFinished());
  }

  @Test
  @DisplayName("Devrait gagner depuis l'avantage")
  void should_win_from_advantage() {
    TennisGame game =
        TennisGame.newGame()
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A);

    assertTrue(GameStateMatchers.isGameWonA(game.getGameState()));
    assertTrue(game.isGameFinished());
  }

  @Test
  @DisplayName("Devrait ignorer les points après la fin du jeu")
  void should_ignore_points_after_game_finished() {
    TennisGame game =
        TennisGame.newGame()
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A);

    TennisGame gameAfterExtraPoint = game.scorePoint(Point.PLAYER_B);

    assertEquals(game, gameAfterExtraPoint);
  }

  @Test
  @DisplayName("Devrait réinitialiser le jeu")
  void should_reset_game() {
    TennisGame game =
        TennisGame.newGame()
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_A);

    TennisGame resetGame = game.reset();

    assertEquals(Score.from(0), resetGame.getPlayerA().score());
    assertEquals(Score.from(0), resetGame.getPlayerB().score());
    assertTrue(GameStateMatchers.isInProgress(resetGame.getGameState()));
    assertFalse(resetGame.isGameFinished());
  }

  @Test
  @DisplayName("Devrait être immutable")
  void should_be_immutable() {
    TennisGame originalGame = TennisGame.newGame();
    TennisGame newGame = originalGame.scorePoint(Point.PLAYER_A);

    assertNotSame(originalGame, newGame);
    assertEquals(Score.from(0), originalGame.getPlayerA().score());
    assertEquals(Score.from(1), newGame.getPlayerA().score());
  }
}
