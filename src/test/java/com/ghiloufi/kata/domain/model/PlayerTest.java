package com.ghiloufi.kata.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests du Value Object Player")
class PlayerTest {

  @Test
  @DisplayName("Devrait créer un joueur avec le nom et score initial")
  void should_create_player() {
    Player player = new Player("TestPlayer", new Score(0));

    assertEquals("TestPlayer", player.name());
    assertEquals(new Score(0), player.score());
  }

  @Test
  @DisplayName("Devrait marquer des points immutablement")
  void should_score_points_immutably() {
    Player player = new Player("TestPlayer", new Score(0));

    Player afterFirstPoint = player.scorePoint();
    assertEquals(new Score(1), afterFirstPoint.score());
    assertEquals(new Score(0), player.score());

    Player afterSecondPoint = afterFirstPoint.scorePoint();
    assertEquals(new Score(2), afterSecondPoint.score());

    Player afterThirdPoint = afterSecondPoint.scorePoint();
    assertEquals(new Score(3), afterThirdPoint.score());
  }

  @Test
  @DisplayName("Devrait remettre à zéro le score immutablement")
  void should_reset_score_immutably() {
    Player player = new Player("TestPlayer", new Score(0)).scorePoint().scorePoint();
    assertEquals(new Score(2), player.score());

    Player resetPlayer = player.reset();
    assertEquals(new Score(0), resetPlayer.score());
    assertEquals(new Score(2), player.score());
  }

  @Test
  @DisplayName("Devrait implémenter equals correctement")
  void should_implement_equals_correctly() {
    Player player1 = new Player("Alice", new Score(0)).scorePoint();
    Player player2 = new Player("Alice", new Score(0)).scorePoint();
    Player player3 = new Player("Bob", new Score(0)).scorePoint();
    Player player4 = new Player("Alice", new Score(0));

    assertEquals(player1, player2);
    assertNotEquals(player1, player3);
    assertNotEquals(player1, player4);
    assertNotEquals(player1, null);
    assertNotEquals(player1, "Alice");
  }
}
