package com.ghiloufi.kata.input;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.Point;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests de l'itérateur de séquence de jeu")
class GameSequenceIteratorTest {

  @Test
  @DisplayName("Devrait créer un itérateur avec une séquence valide")
  void should_create_iterator_with_valid_sequence() {
    GameSequenceIterator iterator = new GameSequenceIterator("AAABB");

    assertNotNull(iterator);
    assertTrue(iterator.hasNext());
  }

  @Test
  @DisplayName("Devrait itérer correctement sur une séquence simple")
  void should_iterate_correctly_over_simple_sequence() {
    GameSequenceIterator iterator = new GameSequenceIterator("AB");

    assertTrue(iterator.hasNext());
    assertEquals(Point.PLAYER_A, iterator.next());

    assertTrue(iterator.hasNext());
    assertEquals(Point.PLAYER_B, iterator.next());

    assertFalse(iterator.hasNext());
  }

  @Test
  @DisplayName("Devrait itérer correctement sur une séquence plus longue")
  void should_iterate_correctly_over_longer_sequence() {
    GameSequenceIterator iterator = new GameSequenceIterator("AAABBBAAA");
    String result = "";

    while (iterator.hasNext()) {
      result += iterator.next().toString();
    }

    assertEquals("AAABBBAAA", result);
    assertFalse(iterator.hasNext());
  }

  @Test
  @DisplayName("Devrait gérer une séquence vide")
  void should_handle_empty_sequence() {
    GameSequenceIterator iterator = new GameSequenceIterator("");

    assertFalse(iterator.hasNext());
  }

  @Test
  @DisplayName("Devrait gérer une séquence null")
  void should_handle_null_sequence() {
    GameSequenceIterator iterator = new GameSequenceIterator(null);

    assertFalse(iterator.hasNext());
  }

  @Test
  @DisplayName("Devrait lancer une exception quand il n'y a plus d'éléments")
  void should_throw_exception_when_no_more_elements() {
    GameSequenceIterator iterator = new GameSequenceIterator("A");

    iterator.next();

    assertThrows(NoSuchElementException.class, iterator::next);
  }

  @Test
  @DisplayName("Devrait lancer une exception sur séquence vide")
  void should_throw_exception_on_empty_sequence() {
    GameSequenceIterator iterator = new GameSequenceIterator("");

    assertThrows(NoSuchElementException.class, iterator::next);
  }

  @Test
  @DisplayName("Devrait lancer une exception sur séquence null")
  void should_throw_exception_on_null_sequence() {
    GameSequenceIterator iterator = new GameSequenceIterator(null);

    assertThrows(NoSuchElementException.class, iterator::next);
  }

  @Test
  @DisplayName("Devrait itérer correctement avec une séquence d'un seul caractère")
  void should_iterate_correctly_with_single_character() {
    GameSequenceIterator iterator = new GameSequenceIterator("A");

    assertTrue(iterator.hasNext());
    assertEquals(Point.PLAYER_A, iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  @DisplayName("Devrait maintenir l'état correct après plusieurs appels hasNext")
  void should_maintain_correct_state_after_multiple_hasNext_calls() {
    GameSequenceIterator iterator = new GameSequenceIterator("AB");

    assertTrue(iterator.hasNext());
    assertTrue(iterator.hasNext());
    assertTrue(iterator.hasNext());

    assertEquals(Point.PLAYER_A, iterator.next());

    assertTrue(iterator.hasNext());
    assertTrue(iterator.hasNext());

    assertEquals(Point.PLAYER_B, iterator.next());

    assertFalse(iterator.hasNext());
    assertFalse(iterator.hasNext());
  }

}
