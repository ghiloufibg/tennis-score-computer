package com.ghiloufi.kata.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests de la classe Player")
class PlayerTest {

  private Player player;

  @BeforeEach
  void setUp() {
    player = new Player("TestPlayer");
  }

  @Test
  @DisplayName("Devrait créer un joueur avec le nom et les points initialisés")
  void should_create_player() {
    assertEquals("TestPlayer", player.getName());
    assertEquals(0, player.getPoints());
  }

  @Test
  @DisplayName("Devrait incrémenter les points du joueur")
  void should_increment_points() {
    player.incrementPoints();
    assertEquals(1, player.getPoints());

    player.incrementPoints();
    assertEquals(2, player.getPoints());

    player.incrementPoints();
    assertEquals(3, player.getPoints());
  }

  @Test
  @DisplayName("Devrait remettre à zéro les points du joueur")
  void should_reset_player_points() {
    player.incrementPoints();
    player.incrementPoints();
    assertEquals(2, player.getPoints());

    player.reset();
    assertEquals(0, player.getPoints());
  }

  @Test
  @DisplayName("Devrait convertir le joueur en chaîne de caractères")
  void should_convert_player_to_string() {
    String expected = "Player{name='TestPlayer', points=0}";
    assertEquals(expected, player.toString());
  }
}
