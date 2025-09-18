package com.ghiloufi.kata.presentation.display;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.model.Game;
import com.ghiloufi.kata.testutil.base.TennisTestBase;
import com.ghiloufi.kata.testutil.builders.TennisTestBuilder;
import com.ghiloufi.kata.testutil.helpers.TestGameStates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests de l'affichage du tableau de score")
class ScoreboardDisplayTest extends TennisTestBase {

  @Test
  @DisplayName("Devrait afficher un seul message de score")
  void should_display_single_score_message() {
    String scoreMessage = "Player A : 15 / Player B : 0";

    final var playerA = TennisTestBuilder.playerWithPoints("A", 1);
    final var playerB = TennisTestBuilder.playerWithPoints("B", 0);

    testEnvironment
        .getDisplay()
        .displayScore(new Game(playerA, playerB, TestGameStates.IN_PROGRESS));

    assertEquals(1, testEnvironment.getCapturedOutput().size());
    assertEquals(scoreMessage, testEnvironment.getCapturedOutput().get(0));
  }
}
