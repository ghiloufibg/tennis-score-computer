package com.ghiloufi.kata.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.testutil.helpers.GameStateMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests du Game Aggregate Root")
class GameTest {

  @Test
  @DisplayName("Devrait créer un nouveau jeu")
  void should_create_new_game() {
    Game game = Game.newGame();

    assertEquals(Player.A.name(), game.getPlayerA().name());
    assertEquals(Player.B.name(), game.getPlayerB().name());
    assertTrue(GameStateMatchers.isInProgress(game.getGameState()));
    assertEquals(Score.from(0), game.getPlayerA().score());
    assertEquals(Score.from(0), game.getPlayerB().score());
  }

  @Test
  @DisplayName("Devrait créer un jeu avec noms personnalisés")
  void should_create_game_with_custom_names() {
    Game game = Game.withPlayers("Alice", "Bob");

    assertEquals("Alice", game.getPlayerA().name());
    assertEquals("Bob", game.getPlayerB().name());
    assertTrue(GameStateMatchers.isInProgress(game.getGameState()));
  }

  @Test
  @DisplayName("Devrait marquer un point pour le joueur A")
  void should_score_point_for_player_A() {
    Game game = Game.newGame();

    Game newGame = game.scorePoint(Point.PLAYER_A);

    assertEquals(Score.from(1), newGame.getPlayerA().score());
    assertEquals(Score.from(0), newGame.getPlayerB().score());
    assertTrue(GameStateMatchers.isInProgress(newGame.getGameState()));
  }

  @Test
  @DisplayName("Devrait marquer un point pour le joueur B")
  void should_score_point_for_player_B() {
    Game game = Game.newGame();

    Game newGame = game.scorePoint(Point.PLAYER_B);

    assertEquals(Score.from(0), newGame.getPlayerA().score());
    assertEquals(Score.from(1), newGame.getPlayerB().score());
    assertTrue(GameStateMatchers.isInProgress(newGame.getGameState()));
  }

  @Test
  @DisplayName("Devrait atteindre l'état deuce")
  void should_reach_deuce_state() {
    Game game =
        Game.newGame()
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
    Game game =
        Game.newGame()
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
    Game game =
        Game.newGame()
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
    Game game =
        Game.newGame()
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
    Game game =
        Game.newGame()
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
    Game game =
        Game.newGame()
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
    Game game =
        Game.newGame()
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_A);

    Game gameAfterExtraPoint = game.scorePoint(Point.PLAYER_B);

    assertEquals(game, gameAfterExtraPoint);
  }

  @Test
  @DisplayName("Devrait réinitialiser le jeu")
  void should_reset_game() {
    Game game =
        Game.newGame()
            .scorePoint(Point.PLAYER_A)
            .scorePoint(Point.PLAYER_B)
            .scorePoint(Point.PLAYER_A);

    Game resetGame = game.reset();

    assertEquals(Score.from(0), resetGame.getPlayerA().score());
    assertEquals(Score.from(0), resetGame.getPlayerB().score());
    assertTrue(GameStateMatchers.isInProgress(resetGame.getGameState()));
    assertFalse(resetGame.isGameFinished());
  }

  @Test
  @DisplayName("Devrait être immutable")
  void should_be_immutable() {
    Game originalGame = Game.newGame();
    Game newGame = originalGame.scorePoint(Point.PLAYER_A);

    assertNotSame(originalGame, newGame);
    assertEquals(Score.from(0), originalGame.getPlayerA().score());
    assertEquals(Score.from(1), newGame.getPlayerA().score());
  }
}
