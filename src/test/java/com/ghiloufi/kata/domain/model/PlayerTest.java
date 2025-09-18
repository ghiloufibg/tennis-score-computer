package com.ghiloufi.kata.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests du Value Object Player")
class PlayerTest {

  @Test
  @DisplayName("Devrait créer un joueur avec le nom et score initial")
  void should_create_player() {
    Player player = Player.withName("TestPlayer");

    assertEquals("TestPlayer", player.name());
    assertEquals(Score.from(0), player.score());
  }

  @Test
  @DisplayName("Devrait marquer des points immutablement")
  void should_score_points_immutably() {
    Player player = Player.withName("TestPlayer");

    Player afterFirstPoint = player.scorePoint();
    assertEquals(Score.from(1), afterFirstPoint.score());
    assertEquals(Score.from(0), player.score());

    Player afterSecondPoint = afterFirstPoint.scorePoint();
    assertEquals(Score.from(2), afterSecondPoint.score());

    Player afterThirdPoint = afterSecondPoint.scorePoint();
    assertEquals(Score.from(3), afterThirdPoint.score());
  }

  @Test
  @DisplayName("Devrait remettre à zéro le score immutablement")
  void should_reset_score_immutably() {
    Player player = Player.withName("TestPlayer").scorePoint().scorePoint();
    assertEquals(Score.from(2), player.score());

    Player resetPlayer = player.reset();
    assertEquals(Score.from(0), resetPlayer.score());
    assertEquals(Score.from(2), player.score());
  }

  @Test
  @DisplayName("Devrait implémenter equals correctement")
  void should_implement_equals_correctly() {
    Player player1 = Player.withName("Alice").scorePoint();
    Player player2 = Player.withName("Alice").scorePoint();
    Player player3 = Player.withName("Bob").scorePoint();
    Player player4 = Player.withName("Alice");

    assertEquals(player1, player2);
    assertNotEquals(player1, player3);
    assertNotEquals(player1, player4);
    assertNotEquals(player1, null);
    assertNotEquals(player1, "Alice");
  }
}
