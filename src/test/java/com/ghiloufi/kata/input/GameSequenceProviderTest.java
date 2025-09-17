package com.ghiloufi.kata.input;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests du fournisseur de séquence de jeu")
class GameSequenceProviderTest {

  @Test
  @DisplayName("Devrait créer un itérateur depuis une séquence valide")
  void should_create_iterator_from_valid_sequence() {
    Iterator<Character> iterator = GameSequenceProvider.fromString("AAABB");

    assertNotNull(iterator);
    assertTrue(iterator.hasNext());
  }

  @Test
  @DisplayName("Devrait valider et créer un itérateur pour une séquence simple")
  void should_validate_and_create_iterator_for_simple_sequence() {
    Iterator<Character> iterator = GameSequenceProvider.fromString("AB");

    assertEquals('A', iterator.next());
    assertEquals('B', iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  @DisplayName("Devrait sanitiser l'entrée en supprimant les espaces")
  void should_sanitize_input_by_removing_spaces() {
    Iterator<Character> iterator = GameSequenceProvider.fromString("A B A B");

    assertEquals('A', iterator.next());
    assertEquals('B', iterator.next());
    assertEquals('A', iterator.next());
    assertEquals('B', iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  @DisplayName("Devrait sanitiser l'entrée en supprimant les tabulations et retours à la ligne")
  void should_sanitize_input_by_removing_tabs_and_newlines() {
    Iterator<Character> iterator = GameSequenceProvider.fromString("A\tB\nA\rB");

    assertEquals('A', iterator.next());
    assertEquals('B', iterator.next());
    assertEquals('A', iterator.next());
    assertEquals('B', iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  @DisplayName("Devrait rejeter une entrée null")
  void should_reject_null_input() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> GameSequenceProvider.fromString(null));

    assertEquals("Match notation cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter une entrée vide")
  void should_reject_empty_input() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> GameSequenceProvider.fromString(""));

    assertEquals("Match notation cannot be empty", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter une entrée avec des caractères invalides")
  void should_reject_input_with_invalid_characters() {
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> GameSequenceProvider.fromString("AAXBB"));

    assertEquals(
        "Invalid player: X at position 2. Only 'A' or 'B' are allowed.", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter une entrée avec des lettres minuscules")
  void should_reject_input_with_lowercase_letters() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> GameSequenceProvider.fromString("AaBb"));

    assertEquals(
        "Invalid player: a at position 1. Only 'A' or 'B' are allowed.", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter une entrée avec des chiffres")
  void should_reject_input_with_numbers() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> GameSequenceProvider.fromString("A1B"));

    assertEquals(
        "Invalid player: 1 at position 1. Only 'A' or 'B' are allowed.", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait accepter une longue séquence valide")
  void should_accept_long_valid_sequence() {
    String longSequence = "A".repeat(5000) + "B".repeat(4999);

    assertDoesNotThrow(
        () -> {
          Iterator<Character> iterator = GameSequenceProvider.fromString(longSequence);
          assertNotNull(iterator);
          assertTrue(iterator.hasNext());
        });
  }

  @Test
  @DisplayName("Devrait rejeter une séquence trop longue")
  void should_reject_sequence_too_long() {
    String tooLongSequence = "A".repeat(10001);

    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class, () -> GameSequenceProvider.fromString(tooLongSequence));

    assertTrue(exception.getMessage().contains("Match notation too long"));
    assertTrue(exception.getMessage().contains("Maximum allowed: 10000"));
  }

  @Test
  @DisplayName("Devrait créer un itérateur utilisable pour un jeu complet")
  void should_create_usable_iterator_for_complete_game() {
    Iterator<Character> iterator = GameSequenceProvider.fromString("AAAA");
    String result = "";

    while (iterator.hasNext()) {
      result += iterator.next();
    }

    assertEquals("AAAA", result);
  }

  @Test
  @DisplayName("Devrait gérer une séquence complexe avec validation")
  void should_handle_complex_sequence_with_validation() {
    String complexSequence = "AAABBBABAABBBAAABBBAAA";

    Iterator<Character> iterator = GameSequenceProvider.fromString(complexSequence);

    assertNotNull(iterator);
    assertTrue(iterator.hasNext());

    // Vérifier quelques premiers éléments
    assertEquals('A', iterator.next());
    assertEquals('A', iterator.next());
    assertEquals('A', iterator.next());
    assertEquals('B', iterator.next());
  }

  @Test
  @DisplayName("Devrait préserver l'ordre des caractères")
  void should_preserve_character_order() {
    String input = "ABBAABAB";
    Iterator<Character> iterator = GameSequenceProvider.fromString(input);

    StringBuilder result = new StringBuilder();
    while (iterator.hasNext()) {
      result.append(iterator.next());
    }

    assertEquals(input, result.toString());
  }
}
