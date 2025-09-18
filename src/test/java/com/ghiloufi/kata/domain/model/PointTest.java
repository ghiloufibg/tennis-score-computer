package com.ghiloufi.kata.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.error.GameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests du Value Object Point")
class PointTest {

  @Test
  @DisplayName("Devrait créer un point pour le joueur A")
  void should_create_point_for_player_A() {
    Point point = Point.from('A');

    assertTrue(point.isWonBy(Player.A));
    assertFalse(point.isWonBy(Player.B));
  }

  @Test
  @DisplayName("Devrait créer un point pour le joueur B")
  void should_create_point_for_player_B() {
    Point point = Point.from('B');

    assertTrue(point.isWonBy(Player.B));
    assertFalse(point.isWonBy(Player.A));
  }

  @Test
  @DisplayName("Devrait utiliser les constantes pour les joueurs")
  void should_use_constants_for_players() {
    Point pointA = Point.from('A');
    Point pointB = Point.from('B');

    assertSame(Point.PLAYER_A, pointA);
    assertSame(Point.PLAYER_B, pointB);
  }

  @Test
  @DisplayName("Devrait rejeter un caractère invalide")
  void should_reject_invalid_character() {
    GameException exception = assertThrows(GameException.class, () -> Point.from('X'));
    assertEquals("Invalid player: X. Only 'A' or 'B' are allowed.", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter les lettres minuscules")
  void should_reject_lowercase_letters() {
    GameException exception = assertThrows(GameException.class, () -> Point.from('a'));
    assertEquals("Invalid player: a. Only 'A' or 'B' are allowed.", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter les chiffres")
  void should_reject_numbers() {
    GameException exception = assertThrows(GameException.class, () -> Point.from('1'));
    assertEquals("Invalid player: 1. Only 'A' or 'B' are allowed.", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait implémenter equals correctement")
  void should_implement_equals_correctly() {
    Point pointA1 = Point.from('A');
    Point pointA2 = Point.from('A');
    Point pointB = Point.from('B');

    assertEquals(pointA1, pointA2);
    assertNotEquals(pointA1, pointB);
    assertNotEquals(pointA1, null);
    assertNotEquals(pointA1, "A");
  }

  @Test
  @DisplayName("Devrait implémenter hashCode correctement")
  void should_implement_hashCode_correctly() {
    Point pointA1 = Point.from('A');
    Point pointA2 = Point.from('A');

    assertEquals(pointA1.hashCode(), pointA2.hashCode());
  }

  @Test
  @DisplayName("Devrait implémenter toString correctement")
  void should_implement_toString_correctly() {
    Point pointA = Point.from('A');
    Point pointB = Point.from('B');

    assertEquals("A", pointA.toString());
    assertEquals("B", pointB.toString());
  }

  @Test
  @DisplayName("Devrait être identique à lui-même")
  void should_be_equal_to_itself() {
    Point point = Point.from('A');

    assertEquals(point, point);
  }

  @Test
  @DisplayName("Devrait vérifier correctement le gagnant avec isWonBy")
  void should_verify_winner_correctly_with_isWonBy() {
    Point pointA = Point.from('A');
    Point pointB = Point.from('B');

    assertTrue(pointA.isWonBy(Player.A));
    assertFalse(pointA.isWonBy(Player.B));
    assertTrue(pointB.isWonBy(Player.B));
    assertFalse(pointB.isWonBy(Player.A));
  }
}
