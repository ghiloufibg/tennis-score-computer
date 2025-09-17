package com.ghiloufi.kata.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests du Value Object Score")
class ScoreTest {

  @Test
  @DisplayName("Devrait créer un score depuis points")
  void should_create_score_from_points() {
    Score score = Score.from(2);

    assertEquals(2, score.points());
    assertEquals("30", score.toDisplayString());
  }

  @Test
  @DisplayName("Devrait utiliser les constantes pour les scores communs")
  void should_use_constants_for_common_scores() {
    assertEquals(Score.LOVE_SCORE, Score.from(0));
    assertEquals(Score.FIFTEEN_SCORE, Score.from(1));
    assertEquals(Score.THIRTY_SCORE, Score.from(2));
    assertEquals(Score.FORTY_SCORE, Score.from(3));
  }

  @Test
  @DisplayName("Devrait incrémenter le score correctement")
  void should_increment_score_correctly() {
    Score score = Score.LOVE_SCORE;

    score = score.increment();
    assertEquals(Score.FIFTEEN_SCORE, score);

    score = score.increment();
    assertEquals(Score.THIRTY_SCORE, score);

    score = score.increment();
    assertEquals(Score.FORTY_SCORE, score);
  }

  @Test
  @DisplayName("Devrait afficher le score tennis correctement")
  void should_display_tennis_score_correctly() {
    assertEquals("0", Score.LOVE_SCORE.toDisplayString());
    assertEquals("15", Score.FIFTEEN_SCORE.toDisplayString());
    assertEquals("30", Score.THIRTY_SCORE.toDisplayString());
    assertEquals("40", Score.FORTY_SCORE.toDisplayString());
  }

  @Test
  @DisplayName("Devrait gérer les scores au-delà de 40")
  void should_handle_scores_beyond_forty() {
    Score score = Score.from(5);

    assertEquals(5, score.points());
    assertEquals("40", score.toDisplayString());
    assertTrue(score.isAtLeastForty());
  }

  @Test
  @DisplayName("Devrait vérifier si le score est 40")
  void should_check_if_score_is_forty() {
    assertTrue(Score.FORTY_SCORE.isForty());
    assertFalse(Score.THIRTY_SCORE.isForty());
    assertFalse(Score.from(4).isForty());
  }

  @Test
  @DisplayName("Devrait vérifier si le score est au moins 40")
  void should_check_if_score_is_at_least_forty() {
    assertTrue(Score.FORTY_SCORE.isAtLeastForty());
    assertTrue(Score.from(4).isAtLeastForty());
    assertTrue(Score.from(5).isAtLeastForty());
    assertFalse(Score.THIRTY_SCORE.isAtLeastForty());
  }

  @Test
  @DisplayName("Devrait implémenter equals correctement")
  void should_implement_equals_correctly() {
    Score score1 = Score.from(2);
    Score score2 = Score.from(2);
    Score score3 = Score.from(1);

    assertEquals(score1, score2);
    assertNotEquals(score1, score3);
    assertNotEquals(score1, null);
    assertNotEquals(score1, "30");
  }

  @Test
  @DisplayName("Devrait implémenter hashCode correctement")
  void should_implement_hashCode_correctly() {
    Score score1 = Score.from(2);
    Score score2 = Score.from(2);

    assertEquals(score1.hashCode(), score2.hashCode());
  }

  @Test
  @DisplayName("Devrait implémenter toString correctement")
  void should_implement_toString_correctly() {
    assertEquals("30", Score.THIRTY_SCORE.toString());
    assertEquals("40", Score.FORTY_SCORE.toString());
  }
}
