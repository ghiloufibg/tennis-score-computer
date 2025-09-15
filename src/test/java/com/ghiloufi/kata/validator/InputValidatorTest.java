package com.ghiloufi.kata.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Tests de validation des entrées")
class InputValidatorTest {

  @Test
  @DisplayName("Devrait valider les entrées valides")
  void should_validate_valid_game_input() {
    assertDoesNotThrow(() -> InputValidator.validateGameInput("A"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("B"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("AB"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("AAAA"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("BBBB"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("ABABAA"));
    assertDoesNotThrow(() -> InputValidator.validateGameInput("AAABBBABABAB"));
  }

  @Test
  @DisplayName("Devrait lever une exception pour une entrée nulle")
  void should_throw_exception_for_null_input() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> InputValidator.validateGameInput(null));
    assertEquals("Input cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait lever une exception pour une entrée vide")
  void should_throw_exception_for_empty_input() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> InputValidator.validateGameInput(""));
    assertEquals("Input cannot be empty", exception.getMessage());
  }

  @ParameterizedTest
  @DisplayName("Devrait lever une exception pour des caractères invalides")
  @ValueSource(strings = {"C", "X", "1", "a", "b", "AB1", "AXB", "AA BB"})
  void should_throw_exception_for_invalid_character(String input) {
    assertThrows(IllegalArgumentException.class, () -> InputValidator.validateGameInput(input));
  }

  @Test
  @DisplayName("Devrait inclure la position dans l'exception de caractère invalide")
  void should_throw_exception_with_invalid_character_position() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> InputValidator.validateGameInput("AAXBB"));
    assertTrue(exception.getMessage().contains("position 2"));
    assertTrue(exception.getMessage().contains("Invalid character: X"));
  }

  @Test
  @DisplayName("Devrait retourner vrai pour des caractères de joueur valides")
  void should_validate_valid_player_character() {
    assertTrue(InputValidator.isValidPlayer('A'));
    assertTrue(InputValidator.isValidPlayer('B'));
  }

  @Test
  @DisplayName("Devrait retourner faux pour des caractères de joueur invalides")
  void should_return_false_for_invalid_player_character() {
    assertFalse(InputValidator.isValidPlayer('C'));
    assertFalse(InputValidator.isValidPlayer('a'));
    assertFalse(InputValidator.isValidPlayer('1'));
    assertFalse(InputValidator.isValidPlayer(' '));
  }

  @Test
  @DisplayName("Devrait valider la longueur de l'entrée")
  void should_validate_input_length() {
    assertDoesNotThrow(() -> InputValidator.validateInputLength("AB", 10));
    assertDoesNotThrow(() -> InputValidator.validateInputLength("AAAA", 4));
    assertDoesNotThrow(() -> InputValidator.validateInputLength(null, 10));

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> InputValidator.validateInputLength("AAAAA", 4));
    assertTrue(exception.getMessage().contains("Input too long: 5 characters"));
    assertTrue(exception.getMessage().contains("Maximum allowed: 4"));
  }
}
