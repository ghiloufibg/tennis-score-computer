package com.ghiloufi.kata.display;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.testutil.helpers.TestGameStates;
import com.ghiloufi.kata.domain.TennisGame;
import com.ghiloufi.kata.testutil.builders.TennisTestBuilder;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Tests du rendu de score en terminal")
class TerminalScoreRendererTest {

  private TerminalScoreRenderer renderer;

  @BeforeEach
  void setUp() {
    renderer = new TerminalScoreRenderer();
  }

  @Nested
  @DisplayName("Formatage des scores réguliers")
  class RegularScoreFormatting {

    static Stream<Arguments> regularScoreScenarios() {
      return Stream.of(
          Arguments.of(0, 0, "Player A : 0 / Player B : 0"),
          Arguments.of(1, 0, "Player A : 15 / Player B : 0"),
          Arguments.of(0, 1, "Player A : 0 / Player B : 15"),
          Arguments.of(1, 1, "Player A : 15 / Player B : 15"),
          Arguments.of(2, 0, "Player A : 30 / Player B : 0"),
          Arguments.of(0, 2, "Player A : 0 / Player B : 30"),
          Arguments.of(2, 2, "Player A : 30 / Player B : 30"),
          Arguments.of(3, 0, "Player A : 40 / Player B : 0"),
          Arguments.of(0, 3, "Player A : 0 / Player B : 40"),
          Arguments.of(3, 1, "Player A : 40 / Player B : 15"),
          Arguments.of(1, 3, "Player A : 15 / Player B : 40"),
          Arguments.of(3, 2, "Player A : 40 / Player B : 30"),
          Arguments.of(2, 3, "Player A : 30 / Player B : 40"));
    }

    @ParameterizedTest
    @DisplayName("Devrait formater les scores réguliers correctement")
    @MethodSource("regularScoreScenarios")
    void should_format_regular_scores_correctly(int pointsA, int pointsB, String expectedScore) {

      var playerA = TennisTestBuilder.playerWithPoints("A", pointsA);
      var playerB = TennisTestBuilder.playerWithPoints("B", pointsB);

      String actualScore = renderer.apply(new TennisGame(playerA, playerB, TestGameStates.IN_PROGRESS));

      assertEquals(expectedScore, actualScore);
    }
  }

  @Nested
  @DisplayName("États spéciaux du jeu")
  class SpecialGameStates {

    @Test
    @DisplayName("Devrait formater l'égalité correctement")
    void should_format_deuce_correctly() {
      var playerA = TennisTestBuilder.playerWithPoints("A", 3);
      var playerB = TennisTestBuilder.playerWithPoints("B", 3);
      String score = renderer.apply(new TennisGame(playerA, playerB, TestGameStates.DEUCE));
      assertEquals("Player A : 40 / Player B : 40 (Deuce)", score);
    }

    @Test
    @DisplayName("Devrait formater l'avantage du joueur A correctement")
    void should_format_advantage_a_correctly() {
      var playerA = TennisTestBuilder.playerWithPoints("A", 4);
      var playerB = TennisTestBuilder.playerWithPoints("B", 3);
      String score = renderer.apply(new TennisGame(playerA, playerB, TestGameStates.ADVANTAGE_A));
      assertEquals("Player A : 40 / Player B : 40 (Advantage Player A)", score);
    }

    @Test
    @DisplayName("Devrait formater l'avantage du joueur B correctement")
    void should_format_advantage_b_correctly() {
      var playerA = TennisTestBuilder.playerWithPoints("A", 3);
      var playerB = TennisTestBuilder.playerWithPoints("B", 4);
      String score = renderer.apply(new TennisGame(playerA, playerB, TestGameStates.ADVANTAGE_B));
      assertEquals("Player A : 40 / Player B : 40 (Advantage Player B)", score);
    }

    @Test
    @DisplayName("Devrait formater la victoire du joueur A correctement")
    void should_format_game_won_a_correctly() {
      var playerA = TennisTestBuilder.playerWithPoints("A", 4);
      var playerB = TennisTestBuilder.playerWithPoints("B", 0);
      String score = renderer.apply(new TennisGame(playerA, playerB, TestGameStates.GAME_WON_A));
      assertEquals("Player A wins the game", score);
    }

    @Test
    @DisplayName("Devrait formater la victoire du joueur B correctement")
    void should_format_game_won_b_correctly() {
      var playerA = TennisTestBuilder.playerWithPoints("A", 0);
      var playerB = TennisTestBuilder.playerWithPoints("B", 4);
      String score = renderer.apply(new TennisGame(playerA, playerB, TestGameStates.GAME_WON_B));
      assertEquals("Player B wins the game", score);
    }
  }
}
