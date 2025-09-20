package com.ghiloufi.kata.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.state.GameState;
import com.ghiloufi.kata.testutil.helpers.GameStateMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests du Game Aggregate Root")
class GameTest {

  @Test
  @DisplayName("Devrait créer un nouveau jeu")
  void should_create_new_game() {
    Game game = Game.newGame();

    assertEquals(Player.A.name(), game.playerA().name());
    assertEquals(Player.B.name(), game.playerB().name());
    assertTrue(GameStateMatchers.isInProgress(game.gameState()));
    assertEquals(new Score(0), game.playerA().score());
    assertEquals(new Score(0), game.playerB().score());
  }

  @Test
  @DisplayName("Devrait créer un jeu avec noms personnalisés")
  void should_create_game_with_custom_names() {
    Game game =
        new Game(
            new Player("Alice", new Score(0)), new Player("Bob", new Score(0)), GameState.PLAYING);

    assertEquals("Alice", game.playerA().name());
    assertEquals("Bob", game.playerB().name());
    assertTrue(GameStateMatchers.isInProgress(game.gameState()));
  }

  @Test
  @DisplayName("Devrait marquer un point pour le joueur A")
  void should_score_point_for_player_A() {
    Game game = Game.newGame();

    Game newGame = game.scorePoint(Point.A);

    assertEquals(new Score(1), newGame.playerA().score());
    assertEquals(new Score(0), newGame.playerB().score());
    assertTrue(GameStateMatchers.isInProgress(newGame.gameState()));
  }

  @Test
  @DisplayName("Devrait marquer un point pour le joueur B")
  void should_score_point_for_player_B() {
    Game game = Game.newGame();

    Game newGame = game.scorePoint(Point.B);

    assertEquals(new Score(0), newGame.playerA().score());
    assertEquals(new Score(1), newGame.playerB().score());
    assertTrue(GameStateMatchers.isInProgress(newGame.gameState()));
  }

  @Test
  @DisplayName("Devrait atteindre l'état deuce")
  void should_reach_deuce_state() {
    Game game =
        Game.newGame()
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.B)
            .scorePoint(Point.B)
            .scorePoint(Point.B);

    assertTrue(GameStateMatchers.isDeuce(game.gameState()));
    assertEquals(new Score(3), game.playerA().score());
    assertEquals(new Score(3), game.playerB().score());
  }

  @Test
  @DisplayName("Devrait donner l'avantage au joueur A")
  void should_give_advantage_to_player_A() {
    Game game =
        Game.newGame()
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.B)
            .scorePoint(Point.B)
            .scorePoint(Point.B)
            .scorePoint(Point.A);

    assertTrue(GameStateMatchers.isAdvantageA(game.gameState()));
    assertEquals(new Score(4), game.playerA().score());
    assertEquals(new Score(3), game.playerB().score());
  }

  @Test
  @DisplayName("Devrait donner l'avantage au joueur B")
  void should_give_advantage_to_player_B() {
    Game game =
        Game.newGame()
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.B)
            .scorePoint(Point.B)
            .scorePoint(Point.B)
            .scorePoint(Point.B);

    assertTrue(GameStateMatchers.isAdvantageB(game.gameState()));
    assertEquals(new Score(3), game.playerA().score());
    assertEquals(new Score(4), game.playerB().score());
  }

  @Test
  @DisplayName("Devrait permettre au joueur A de gagner")
  void should_allow_player_A_to_win() {
    Game game =
        Game.newGame()
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.A);

    assertTrue(GameStateMatchers.isGameWonA(game.gameState()));
    assertTrue(game.isGameFinished());
  }

  @Test
  @DisplayName("Devrait permettre au joueur B de gagner")
  void should_allow_player_B_to_win() {
    Game game =
        Game.newGame()
            .scorePoint(Point.B)
            .scorePoint(Point.B)
            .scorePoint(Point.B)
            .scorePoint(Point.B);

    assertTrue(GameStateMatchers.isGameWonB(game.gameState()));
    assertTrue(game.isGameFinished());
  }

  @Test
  @DisplayName("Devrait gagner depuis l'avantage")
  void should_win_from_advantage() {
    Game game =
        Game.newGame()
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.B)
            .scorePoint(Point.B)
            .scorePoint(Point.B)
            .scorePoint(Point.A)
            .scorePoint(Point.A);

    assertTrue(GameStateMatchers.isGameWonA(game.gameState()));
    assertTrue(game.isGameFinished());
  }

  @Test
  @DisplayName("Devrait ignorer les points après la fin du jeu")
  void should_ignore_points_after_game_finished() {
    Game game =
        Game.newGame()
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.A)
            .scorePoint(Point.A);

    Game gameAfterExtraPoint = game.scorePoint(Point.B);

    assertEquals(game, gameAfterExtraPoint);
  }

  @Test
  @DisplayName("Devrait réinitialiser le jeu")
  void should_reset_game() {
    Game game = Game.newGame().scorePoint(Point.A).scorePoint(Point.B).scorePoint(Point.A);

    Game resetGame = game.reset();

    assertEquals(new Score(0), resetGame.playerA().score());
    assertEquals(new Score(0), resetGame.playerB().score());
    assertTrue(GameStateMatchers.isInProgress(resetGame.gameState()));
    assertFalse(resetGame.isGameFinished());
  }

  @Test
  @DisplayName("Devrait être immutable")
  void should_be_immutable() {
    Game originalGame = Game.newGame();
    Game newGame = originalGame.scorePoint(Point.A);

    assertNotSame(originalGame, newGame);
    assertEquals(new Score(0), originalGame.playerA().score());
    assertEquals(new Score(1), newGame.playerA().score());
  }
}
