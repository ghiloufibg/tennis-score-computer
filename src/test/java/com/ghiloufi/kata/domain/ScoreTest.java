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
    assertEquals("30", score.toString());
  }

  @Test
  @DisplayName("Devrait créer des scores corrects avec from()")
  void should_create_correct_scores_with_from() {
    assertEquals(Score.from(0), Score.from(0));
    assertEquals(Score.from(1), Score.from(1));
    assertEquals(Score.from(2), Score.from(2));
    assertEquals(Score.from(3), Score.from(3));
  }

  @Test
  @DisplayName("Devrait incrémenter le score correctement")
  void should_increment_score_correctly() {
    Score score = Score.from(0);

    score = score.increment();
    assertEquals(Score.from(1), score);

    score = score.increment();
    assertEquals(Score.from(2), score);

    score = score.increment();
    assertEquals(Score.from(3), score);
  }

  @Test
  @DisplayName("Devrait afficher le score tennis correctement")
  void should_display_tennis_score_correctly() {
    assertEquals("0", Score.from(0).toString());
    assertEquals("15", Score.from(1).toString());
    assertEquals("30", Score.from(2).toString());
    assertEquals("40", Score.from(3).toString());
  }

  @Test
  @DisplayName("Devrait gérer les scores au-delà de 40")
  void should_handle_scores_beyond_forty() {
    Score score = Score.from(5);

    assertEquals(5, score.points());
    assertEquals("40", score.toString());
    assertTrue(score.isAtLeastForty());
  }

  @Test
  @DisplayName("Devrait vérifier si le score est 40")
  void should_check_if_score_is_forty() {
    assertTrue(Score.from(3).isForty());
    assertFalse(Score.from(2).isForty());
    assertFalse(Score.from(4).isForty());
  }

  @Test
  @DisplayName("Devrait vérifier si le score est au moins 40")
  void should_check_if_score_is_at_least_forty() {
    assertTrue(Score.from(3).isAtLeastForty());
    assertTrue(Score.from(4).isAtLeastForty());
    assertTrue(Score.from(5).isAtLeastForty());
    assertFalse(Score.from(2).isAtLeastForty());
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
    assertEquals("30", Score.from(2).toString());
    assertEquals("40", Score.from(3).toString());
  }
}
