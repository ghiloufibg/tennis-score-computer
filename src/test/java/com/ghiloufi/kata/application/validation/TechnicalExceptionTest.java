package com.ghiloufi.kata.application.validation;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.application.error.TechnicalError;
import com.ghiloufi.kata.application.error.TechnicalException;
import com.ghiloufi.kata.application.service.ScoreComputer;
import com.ghiloufi.kata.presentation.display.ScoreboardDisplay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests des exceptions techniques pour la protection de l'API")
class TechnicalExceptionTest {

  @Test
  @DisplayName("Devrait rejeter ScoreboardDisplay null dans le constructeur ScoreComputer")
  void should_reject_null_scoreboard_display_in_score_computer_constructor() {
    TechnicalException exception =
        assertThrows(TechnicalException.class, () -> new ScoreComputer(null));

    assertEquals(TechnicalError.NULL_SCOREBOARD_DISPLAY, exception.getError());
    assertEquals("ScoreboardDisplay cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter game sequence null dans playMatch")
  void should_reject_null_game_sequence_in_play_match() {
    var display = new ScoreboardDisplay(System.out::println, game -> "test");
    var computer = new ScoreComputer(display);

    TechnicalException exception =
        assertThrows(TechnicalException.class, () -> computer.playMatch(null));

    assertEquals(TechnicalError.NULL_GAME_SEQUENCE, exception.getError());
    assertEquals("Game sequence cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter outputConsumer null dans ScoreboardDisplay")
  void should_reject_null_output_consumer_in_scoreboard_display() {
    TechnicalException exception =
        assertThrows(TechnicalException.class, () -> new ScoreboardDisplay(null, game -> "test"));

    assertEquals(TechnicalError.NULL_OUTPUT_CONSUMER, exception.getError());
    assertEquals("Output consumer cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter scoreRenderer null dans ScoreboardDisplay")
  void should_reject_null_score_renderer_in_scoreboard_display() {
    TechnicalException exception =
        assertThrows(
            TechnicalException.class, () -> new ScoreboardDisplay(System.out::println, null));

    assertEquals(TechnicalError.NULL_SCORE_RENDERER, exception.getError());
    assertEquals("Score renderer cannot be null", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait rejeter game null dans displayScore")
  void should_reject_null_game_in_display_score() {
    var display = new ScoreboardDisplay(System.out::println, game -> "test");

    TechnicalException exception =
        assertThrows(TechnicalException.class, () -> display.displayScore(null));

    assertEquals(TechnicalError.NULL_GAME, exception.getError());
    assertEquals("Game cannot be null", exception.getMessage());
  }
}
