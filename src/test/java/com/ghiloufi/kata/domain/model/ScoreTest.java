package com.ghiloufi.kata.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.error.GameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests du Value Object Score")
class ScoreTest {

  @Test
  @DisplayName("Devrait créer un score depuis points")
  void should_create_score_from_points() {
    Score score = new Score(2);

    assertEquals(2, score.points());
    assertEquals("30", score.toString());
  }

  @Test
  @DisplayName("Devrait créer des scores corrects avec constructor")
  void should_create_correct_scores_with_constructor() {
    assertEquals(new Score(0), new Score(0));
    assertEquals(new Score(1), new Score(1));
    assertEquals(new Score(2), new Score(2));
    assertEquals(new Score(3), new Score(3));
  }

  @Test
  @DisplayName("Devrait incrémenter le score correctement")
  void should_increment_score_correctly() {
    Score score = new Score(0);

    score = score.increment();
    assertEquals(new Score(1), score);

    score = score.increment();
    assertEquals(new Score(2), score);

    score = score.increment();
    assertEquals(new Score(3), score);
  }

  @Test
  @DisplayName("Devrait afficher le score tennis correctement")
  void should_display_tennis_score_correctly() {
    assertEquals("0", new Score(0).toString());
    assertEquals("15", new Score(1).toString());
    assertEquals("30", new Score(2).toString());
    assertEquals("40", new Score(3).toString());
  }

  @Test
  @DisplayName("Devrait gérer les scores au-delà de 40")
  void should_handle_scores_beyond_forty() {
    Score score = new Score(5);

    assertEquals(5, score.points());
    assertEquals("40", score.toString());
    assertTrue(score.points() >= 3);
  }

  @Test
  @DisplayName("Devrait vérifier si le score est 40")
  void should_check_if_score_is_forty() {
    assertTrue(new Score(3).points() == 3);
    assertFalse(new Score(2).points() == 3);
    assertFalse(new Score(4).points() == 3);
  }

  @Test
  @DisplayName("Devrait vérifier si le score est au moins 40")
  void should_check_if_score_is_at_least_forty() {
    assertTrue(new Score(3).points() >= 3);
    assertTrue(new Score(4).points() >= 3);
    assertTrue(new Score(5).points() >= 3);
    assertFalse(new Score(2).points() >= 3);
  }

  @Test
  @DisplayName("Devrait implémenter equals correctement")
  void should_implement_equals_correctly() {
    Score score1 = new Score(2);
    Score score2 = new Score(2);
    Score score3 = new Score(1);

    assertEquals(score1, score2);
    assertNotEquals(score1, score3);
    assertNotEquals(score1, null);
    assertNotEquals(score1, "30");
  }

  @Test
  @DisplayName("Devrait implémenter hashCode correctement")
  void should_implement_hashCode_correctly() {
    Score score1 = new Score(2);
    Score score2 = new Score(2);

    assertEquals(score1.hashCode(), score2.hashCode());
  }

  @Test
  @DisplayName("Devrait implémenter toString correctement")
  void should_implement_toString_correctly() {
    assertEquals("30", new Score(2).toString());
    assertEquals("40", new Score(3).toString());
  }

  @Test
  @DisplayName("Devrait rejeter les points négatifs")
  void should_reject_negative_points() {
    GameException exception = assertThrows(GameException.class, () -> new Score(-1));
    assertEquals("Points cannot be negative: -1", exception.getMessage());
  }

  @Test
  @DisplayName("Devrait créer les scores de base correctement")
  void should_create_basic_scores_correctly() {
    assertEquals(new Score(0), new Score(0));
    assertEquals(new Score(1), new Score(1));
    assertEquals(new Score(2), new Score(2));
    assertEquals(new Score(3), new Score(3));
  }

  @Test
  @DisplayName("Devrait identifier correctement les scores qui peuvent gagner directement")
  void should_identify_scores_that_can_win_directly() {
    assertFalse(new Score(0).points() >= 3);
    assertFalse(new Score(2).points() >= 3);
    assertTrue(new Score(3).points() >= 3);
    assertTrue(new Score(4).points() >= 3);
  }

  @Test
  @DisplayName("Devrait identifier les situations nécessitant un avantage")
  void should_identify_situations_needing_advantage() {
    Score love = new Score(0);
    Score forty = new Score(3);

    assertFalse(new Score(2).points() >= 3 && love.points() >= 3);
    assertFalse(forty.points() >= 3 && love.points() >= 3);
    assertTrue(forty.points() >= 3 && forty.points() >= 3);
    assertTrue(new Score(4).points() >= 3 && forty.points() >= 3);
  }
}
