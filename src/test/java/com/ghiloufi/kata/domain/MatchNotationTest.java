package com.ghiloufi.kata.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests de la notation de match")
class MatchNotationTest {

  @Test
  @DisplayName("Devrait sanitiser la notation en supprimant les espaces")
  void should_sanitize_notation_by_removing_spaces() {
    assertEquals("ABAB", MatchNotation.sanitize("A B A B"));
  }

  @Test
  @DisplayName("Devrait sanitiser la notation en supprimant tous les espaces blancs")
  void should_sanitize_notation_by_removing_all_whitespace() {
    assertEquals("ABAB", MatchNotation.sanitize("A\tB\nA\rB"));
  }

  @Test
  @DisplayName("Devrait retourner null pour une notation null")
  void should_return_null_for_null_notation() {
    assertNull(MatchNotation.sanitize(null));
  }

  @Test
  @DisplayName("Devrait valider une notation correcte")
  void should_validate_correct_notation() {
    assertDoesNotThrow(() -> MatchNotation.validate("AAABB"));
  }

  @Test
  @DisplayName("Devrait rejeter une notation null")
  void should_reject_null_notation() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> MatchNotation.validate(null));
    assertEquals("Match notation cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter une notation vide")
  void should_reject_empty_notation() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> MatchNotation.validate(""));
    assertEquals("Match notation cannot be empty", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter un caractère invalide")
  void should_reject_invalid_character() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> MatchNotation.validate("AAX"));
    assertEquals(
        "Invalid player: X at position 2. Only 'A' or 'B' are allowed.", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait valider la longueur d'une notation courte")
  void should_validate_short_notation_length() {
    assertDoesNotThrow(() -> MatchNotation.validateLength("AAABB", 10));
  }

  @Test
  @DisplayName("Devrait rejeter une notation trop longue")
  void should_reject_notation_too_long() {
    String longNotation = "A".repeat(6);

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> MatchNotation.validateLength(longNotation, 5));

    assertTrue(exception.getMessage().contains("Match notation too long"));
    assertTrue(exception.getMessage().contains("6 points"));
    assertTrue(exception.getMessage().contains("Maximum allowed: 5"));
  }

  @Test
  @DisplayName("Devrait accepter une notation null pour la validation de longueur")
  void should_accept_null_notation_for_length_validation() {
    assertDoesNotThrow(() -> MatchNotation.validateLength(null, 10));
  }

  @Test
  @DisplayName("Devrait identifier un joueur valide")
  void should_identify_valid_player() {
    assertTrue(MatchNotation.isValidPlayer('A'));
    assertTrue(MatchNotation.isValidPlayer('B'));
  }

  @Test
  @DisplayName("Devrait identifier un joueur invalide")
  void should_identify_invalid_player() {
    assertFalse(MatchNotation.isValidPlayer('C'));
    assertFalse(MatchNotation.isValidPlayer('a'));
    assertFalse(MatchNotation.isValidPlayer('1'));
    assertFalse(MatchNotation.isValidPlayer(' '));
  }
}
