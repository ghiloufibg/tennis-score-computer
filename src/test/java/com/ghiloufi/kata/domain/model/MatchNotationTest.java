package com.ghiloufi.kata.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests du Value Object MatchNotation")
class MatchNotationTest {

  @Test
  @DisplayName("Devrait créer une notation valide")
  void should_create_valid_notation() {
    MatchNotation notation = MatchNotation.from("AAABB");

    assertEquals("AAABB", notation.getValue());
    assertEquals(5, notation.length());
  }

  @Test
  @DisplayName("Devrait sanitiser et créer une notation")
  void should_sanitize_and_create_notation() {
    MatchNotation notation = MatchNotation.from("A B A B");

    assertEquals("ABAB", notation.getValue());
    assertEquals(4, notation.length());
  }

  @Test
  @DisplayName("Devrait permettre l'accès aux caractères")
  void should_allow_character_access() {
    MatchNotation notation = MatchNotation.from("AB");

    assertEquals('A', notation.charAt(0));
    assertEquals('B', notation.charAt(1));
  }

  @Test
  @DisplayName("Devrait rejeter une notation null")
  void should_reject_null_notation() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> MatchNotation.from(null));
    assertEquals("Match notation cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter une notation vide")
  void should_reject_empty_notation() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> MatchNotation.from(""));
    assertEquals("Match notation cannot be empty", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter une notation avec espaces seulement")
  void should_reject_whitespace_only_notation() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> MatchNotation.from("   "));
    assertEquals("Match notation cannot be empty", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter un caractère invalide")
  void should_reject_invalid_character() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> MatchNotation.from("AAX"));
    assertEquals(
        "Invalid player: X at position 2. Only 'A' or 'B' are allowed.", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter une notation trop longue")
  void should_reject_notation_too_long() {
    String longNotation = "A".repeat(10001);

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> MatchNotation.from(longNotation));

    assertTrue(exception.getMessage().contains("Match notation too long"));
    assertTrue(exception.getMessage().contains("10001 points"));
    assertTrue(exception.getMessage().contains("Maximum allowed: 10000"));
  }

  @Test
  @DisplayName("Devrait implémenter equals correctement")
  void should_implement_equals_correctly() {
    MatchNotation notation1 = MatchNotation.from("AAABB");
    MatchNotation notation2 = MatchNotation.from("A A A B B");
    MatchNotation notation3 = MatchNotation.from("BBBAA");

    assertEquals(notation1, notation2);
    assertNotEquals(notation1, notation3);
    assertNotEquals(notation1, null);
    assertNotEquals(notation1, "AAABB");
  }

  @Test
  @DisplayName("Devrait implémenter hashCode correctement")
  void should_implement_hashCode_correctly() {
    MatchNotation notation1 = MatchNotation.from("AAABB");
    MatchNotation notation2 = MatchNotation.from("A A A B B");

    assertEquals(notation1.hashCode(), notation2.hashCode());
  }

  @Test
  @DisplayName("Devrait implémenter toString correctement")
  void should_implement_toString_correctly() {
    MatchNotation notation = MatchNotation.from("A B A B");

    assertEquals("ABAB", notation.toString());
  }

  @Test
  @DisplayName("Devrait être identique à elle-même")
  void should_be_equal_to_itself() {
    MatchNotation notation = MatchNotation.from("AAABB");

    assertEquals(notation, notation);
  }

  @Test
  @DisplayName("Devrait accepter une notation à la limite de longueur")
  void should_accept_notation_at_max_length() {
    String maxLengthNotation = "A".repeat(10000);

    assertDoesNotThrow(
        () -> {
          MatchNotation notation = MatchNotation.from(maxLengthNotation);
          assertEquals(10000, notation.length());
        });
  }
}
