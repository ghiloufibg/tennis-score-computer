package com.ghiloufi.kata.domain;

import static org.junit.jupiter.api.Assertions.*;

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
    assertEquals(GameState.IN_PROGRESS, game.getGameState());
    assertEquals(Score.LOVE_SCORE, game.getPlayerA().score());
    assertEquals(Score.LOVE_SCORE, game.getPlayerB().score());
  }

  @Test
  @DisplayName("Devrait créer un jeu avec noms personnalisés")
  void should_create_game_with_custom_names() {
    TennisGame game = TennisGame.withPlayers("Alice", "Bob");

    assertEquals("Alice", game.getPlayerA().name());
    assertEquals("Bob", game.getPlayerB().name());
    assertEquals(GameState.IN_PROGRESS, game.getGameState());
  }

  @Test
  @DisplayName("Devrait marquer un point pour le joueur A")
  void should_score_point_for_player_A() {
    TennisGame game = TennisGame.newGame();

    TennisGame newGame = game.scorePoint(Point.PLAYER_A);

    assertEquals(Score.FIFTEEN_SCORE, newGame.getPlayerA().score());
    assertEquals(Score.LOVE_SCORE, newGame.getPlayerB().score());
    assertEquals(GameState.IN_PROGRESS, newGame.getGameState());
  }

  @Test
  @DisplayName("Devrait marquer un point pour le joueur B")
  void should_score_point_for_player_B() {
    TennisGame game = TennisGame.newGame();

    TennisGame newGame = game.scorePoint(Point.PLAYER_B);

    assertEquals(Score.LOVE_SCORE, newGame.getPlayerA().score());
    assertEquals(Score.FIFTEEN_SCORE, newGame.getPlayerB().score());
    assertEquals(GameState.IN_PROGRESS, newGame.getGameState());
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

    assertEquals(GameState.DEUCE, game.getGameState());
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

    assertEquals(GameState.ADVANTAGE_A, game.getGameState());
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

    assertEquals(GameState.ADVANTAGE_B, game.getGameState());
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

    assertEquals(GameState.GAME_WON_A, game.getGameState());
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

    assertEquals(GameState.GAME_WON_B, game.getGameState());
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

    assertEquals(GameState.GAME_WON_A, game.getGameState());
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

    assertEquals(Score.LOVE_SCORE, resetGame.getPlayerA().score());
    assertEquals(Score.LOVE_SCORE, resetGame.getPlayerB().score());
    assertEquals(GameState.IN_PROGRESS, resetGame.getGameState());
    assertFalse(resetGame.isGameFinished());
  }

  @Test
  @DisplayName("Devrait être immutable")
  void should_be_immutable() {
    TennisGame originalGame = TennisGame.newGame();
    TennisGame newGame = originalGame.scorePoint(Point.PLAYER_A);

    assertNotSame(originalGame, newGame);
    assertEquals(Score.LOVE_SCORE, originalGame.getPlayerA().score());
    assertEquals(Score.FIFTEEN_SCORE, newGame.getPlayerA().score());
  }
}
