package com.ghiloufi.kata.application.validation;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.application.error.TechnicalError;
import com.ghiloufi.kata.application.error.TechnicalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests de l'utilitaire de validation Require")
class RequireTest {

  @Test
  @DisplayName("Devrait retourner l'objet quand il n'est pas null")
  void should_return_object_when_not_null() {
    String testString = "test";

    String result = Require.nonNull(testString, TechnicalError.NULL_GAME_SEQUENCE);

    assertSame(testString, result);
  }

  @Test
  @DisplayName("Devrait retourner l'objet générique quand il n'est pas null")
  void should_return_generic_object_when_not_null() {
    Integer testInteger = 42;

    Integer result = Require.nonNull(testInteger, TechnicalError.NULL_GAME_SEQUENCE);

    assertSame(testInteger, result);
  }

  @Test
  @DisplayName("Devrait fonctionner avec différents types d'objets")
  void should_work_with_different_object_types() {
    Object testObject = new Object();
    StringBuilder testBuilder = new StringBuilder("test");

    assertSame(testObject, Require.nonNull(testObject, TechnicalError.NULL_GAME));
    assertSame(testBuilder, Require.nonNull(testBuilder, TechnicalError.NULL_OUTPUT_CONSUMER));
  }

  @Test
  @DisplayName(
      "Devrait lancer TechnicalException avec NULL_SCOREBOARD_DISPLAY quand objet est null")
  void should_throw_technical_exception_with_null_scoreboard_display_when_object_is_null() {
    TechnicalException exception =
        assertThrows(
            TechnicalException.class,
            () -> Require.nonNull(null, TechnicalError.NULL_SCOREBOARD_DISPLAY));

    assertEquals(TechnicalError.NULL_SCOREBOARD_DISPLAY, exception.getError());
    assertEquals("ScoreboardDisplay cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait lancer TechnicalException avec NULL_GAME_SEQUENCE quand objet est null")
  void should_throw_technical_exception_with_null_game_sequence_when_object_is_null() {
    TechnicalException exception =
        assertThrows(
            TechnicalException.class,
            () -> Require.nonNull(null, TechnicalError.NULL_GAME_SEQUENCE));

    assertEquals(TechnicalError.NULL_GAME_SEQUENCE, exception.getError());
    assertEquals("Game sequence cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait lancer TechnicalException avec NULL_OUTPUT_CONSUMER quand objet est null")
  void should_throw_technical_exception_with_null_output_consumer_when_object_is_null() {
    TechnicalException exception =
        assertThrows(
            TechnicalException.class,
            () -> Require.nonNull(null, TechnicalError.NULL_OUTPUT_CONSUMER));

    assertEquals(TechnicalError.NULL_OUTPUT_CONSUMER, exception.getError());
    assertEquals("Output consumer cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait lancer TechnicalException avec NULL_SCORE_RENDERER quand objet est null")
  void should_throw_technical_exception_with_null_score_renderer_when_object_is_null() {
    TechnicalException exception =
        assertThrows(
            TechnicalException.class,
            () -> Require.nonNull(null, TechnicalError.NULL_SCORE_RENDERER));

    assertEquals(TechnicalError.NULL_SCORE_RENDERER, exception.getError());
    assertEquals("Score renderer cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait lancer TechnicalException avec NULL_GAME quand objet est null")
  void should_throw_technical_exception_with_null_game_when_object_is_null() {
    TechnicalException exception =
        assertThrows(
            TechnicalException.class, () -> Require.nonNull(null, TechnicalError.NULL_GAME));

    assertEquals(TechnicalError.NULL_GAME, exception.getError());
    assertEquals("Game cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait préserver le type générique dans le retour")
  void should_preserve_generic_type_in_return() {
    String testString = "test";

    // Le compilateur devrait accepter ceci sans casting
    String result = Require.nonNull(testString, TechnicalError.NULL_GAME_SEQUENCE);

    assertEquals("test", result);
    assertInstanceOf(String.class, result);
  }

  @Test
  @DisplayName("Devrait fonctionner avec des objets complexes")
  void should_work_with_complex_objects() {
    StringBuilder builder = new StringBuilder("complex object");

    StringBuilder result = Require.nonNull(builder, TechnicalError.NULL_GAME);

    assertSame(builder, result);
    assertEquals("complex object", result.toString());
  }

  @Test
  @DisplayName("Devrait permettre l'enchaînement d'appels")
  void should_allow_method_chaining() {
    String testString = "chainable";

    // Test que le résultat peut être utilisé directement
    int length = Require.nonNull(testString, TechnicalError.NULL_GAME_SEQUENCE).length();

    assertEquals(9, length);
  }
}
