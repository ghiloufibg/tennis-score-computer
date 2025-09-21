package com.ghiloufi.kata;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.application.error.TechnicalException;
import com.ghiloufi.kata.application.service.ScoreComputer;
import com.ghiloufi.kata.domain.error.GameException;
import com.ghiloufi.kata.domain.model.*;
import com.ghiloufi.kata.domain.state.GameState;
import com.ghiloufi.kata.presentation.display.ScoreboardDisplay;
import com.ghiloufi.kata.presentation.display.TerminalScoreRenderer;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tennis Scoring Rules")
class TennisTest {

  /*
   * ╔═══════════════════════════════════════════════════════════════════════════════════════╗
   * ║                                 TENNIS BUSINESS RULES                                ║
   * ╚═══════════════════════════════════════════════════════════════════════════════════════╝
   */

  @Test
  @DisplayName("Player A wins with four points")
  void playerA_wins_with_four_points() {
    assertEquals(GameState.WON_A, game("AAAA").gameState());
  }

  @Test
  @DisplayName("Player B wins with four points")
  void playerB_wins_with_four_points() {
    assertEquals(GameState.WON_B, game("BBBB").gameState());
  }

  @Test
  @DisplayName("Deuce when both reach three points")
  void deuce_when_both_reach_three_points() {
    assertEquals(GameState.DEUCE, game("ABABAB").gameState());
  }

  @Test
  @DisplayName("Advantage A after deuce")
  void advantage_A_after_deuce() {
    assertEquals(GameState.ADVANTAGE_A, game("ABABABA").gameState());
  }

  @Test
  @DisplayName("Advantage B after deuce")
  void advantage_B_after_deuce() {
    assertEquals(GameState.ADVANTAGE_B, game("ABABABB").gameState());
  }

  @Test
  @DisplayName("Player A wins from advantage")
  void playerA_wins_from_advantage() {
    assertEquals(GameState.WON_A, game("ABABABAA").gameState());
  }

  @Test
  @DisplayName("Player B wins from advantage")
  void playerB_wins_from_advantage() {
    assertEquals(GameState.WON_B, game("ABABABBB").gameState());
  }

  @Test
  @DisplayName("Deuce after advantage lost")
  void deuce_after_advantage_lost() {
    assertEquals(GameState.DEUCE, game("ABABABAB").gameState());
  }

  @Test
  @DisplayName("Marathon game with multiple deuces")
  void marathon_game_with_multiple_deuces() {
    assertEquals(GameState.WON_A, game("ABABAB".repeat(3) + "AA").gameState());
    assertEquals(GameState.WON_B, game("ABABAB".repeat(3) + "BB").gameState());
  }

  @Test
  @DisplayName("Comeback victories")
  void comeback_victories() {
    assertEquals(GameState.WON_A, game("BBBAAAAA").gameState());
    assertEquals(GameState.WON_B, game("AAABBBBB").gameState());
  }

  @Test
  @DisplayName("Early game scoring progression")
  void early_game_scoring() {
    assertEquals(GameState.PLAYING, game("A").gameState());
    assertEquals(GameState.PLAYING, game("AB").gameState());
    assertEquals(GameState.PLAYING, game("AAB").gameState());
    assertEquals(GameState.PLAYING, game("BBA").gameState());
  }

  /*
   * ╔═══════════════════════════════════════════════════════════════════════════════════════╗
   * ║                              INPUT VALIDATION & ERRORS                               ║
   * ╚═══════════════════════════════════════════════════════════════════════════════════════╝
   */

  @Test
  @DisplayName("Rejects invalid point characters")
  void rejects_invalid_point_character() {
    assertThrows(GameException.class, () -> Point.from('X'));
    assertThrows(GameException.class, () -> Point.from('1'));
    assertThrows(GameException.class, () -> Point.from('!'));
  }

  @Test
  @DisplayName("Rejects negative scores")
  void rejects_negative_score() {
    assertThrows(GameException.class, () -> new Score(-1));
    assertThrows(GameException.class, () -> new Score(-10));
  }

  @Test
  @DisplayName("Rejects null display in ScoreComputer")
  void rejects_null_display() {
    assertThrows(TechnicalException.class, () -> new ScoreComputer(null));
  }

  @Test
  @DisplayName("Rejects null game sequence in ScoreComputer")
  void rejects_null_game_sequence() {
    var computer =
        new ScoreComputer(new ScoreboardDisplay(System.out::println, new TerminalScoreRenderer()));
    assertThrows(TechnicalException.class, () -> computer.playMatch(null));
  }

  @Test
  @DisplayName("Rejects empty match notation")
  void rejects_empty_match_notation() {
    assertThrows(GameException.class, () -> MatchNotation.from(""));
    assertThrows(GameException.class, () -> MatchNotation.from(null));
    assertThrows(GameException.class, () -> MatchNotation.from("   "));
  }

  @Test
  @DisplayName("Rejects too long match notation")
  void rejects_too_long_match_notation() {
    String tooLong = "A".repeat(10001);
    assertThrows(GameException.class, () -> MatchNotation.from(tooLong));
  }

  @Test
  @DisplayName("Handles invalid characters at specific positions")
  void handles_invalid_characters_at_positions() {
    assertThrows(GameException.class, () -> MatchNotation.from("AXB"));
    assertThrows(GameException.class, () -> MatchNotation.from("A1B"));
    assertThrows(GameException.class, () -> MatchNotation.from("AB!"));
  }

  @Test
  @DisplayName("Handles whitespace in match notation")
  void handles_whitespace_in_match_notation() {
    var notation = MatchNotation.from("A B A B");
    assertEquals(4, notation.points().count());
  }

  /*
   * ╔═══════════════════════════════════════════════════════════════════════════════════════╗
   * ║                                 SYSTEM INTEGRATION                                   ║
   * ╚═══════════════════════════════════════════════════════════════════════════════════════╝
   */

  @Test
  @DisplayName("Complete system integration works")
  void complete_system_integration() {
    var display = new ScoreboardDisplay(System.out::println, new TerminalScoreRenderer());
    var computer = new ScoreComputer(display);
    assertNotNull(computer);
  }

  @Test
  @DisplayName("Renderer produces valid output")
  void renderer_produces_output() {
    var renderer = new TerminalScoreRenderer();
    assertNotNull(renderer.apply(game("AAAA")));
    assertNotNull(renderer.apply(game("ABABAB")));
  }

  @Test
  @DisplayName("System handles various game scenarios")
  void system_handles_game_scenarios() {
    var renderer = new TerminalScoreRenderer();

    assertNotNull(renderer.apply(game("A")));
    assertNotNull(renderer.apply(game("ABABABA")));
    assertNotNull(renderer.apply(game("ABABABAA")));
  }

  @Test
  @DisplayName("ScoreComputer processes complete matches")
  void score_computer_processes_complete_matches() {
    var results = new ArrayList<String>();
    var display = new ScoreboardDisplay(results::add, new TerminalScoreRenderer());
    var computer = new ScoreComputer(display);

    computer.playMatch(MatchNotation.from("AAAA").points().iterator());

    assertFalse(results.isEmpty());
    assertTrue(results.getLast().contains("wins"));
  }

  @Test
  @DisplayName("ScoreComputer stops when game is finished")
  void score_computer_stops_when_game_finished() {
    var results = new ArrayList<String>();
    var display = new ScoreboardDisplay(results::add, new TerminalScoreRenderer());
    var computer = new ScoreComputer(display);

    // Game ends at 4th point but we provide more
    computer.playMatch(MatchNotation.from("AAAAAA").points().iterator());

    assertFalse(results.isEmpty());
    // Should stop after game is won, not process extra points
    assertTrue(results.getLast().contains("wins"));
  }

  @Test
  @DisplayName("MatchNotation creates valid point iterators")
  void match_notation_creates_valid_point_iterators() {
    var iterator = MatchNotation.from("AB").points().iterator();

    assertTrue(iterator.hasNext());
    assertEquals(Point.from('A'), iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals(Point.from('B'), iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test
  @DisplayName("Game handles scoring after reset")
  void game_handles_scoring_after_reset() {
    var game = game("AA").reset();
    assertEquals(GameState.PLAYING, game.gameState());
    assertEquals(0, game.playerA().score().points());
    assertEquals(0, game.playerB().score().points());
  }

  @Test
  @DisplayName("Game continues scoring until finished")
  void game_continues_scoring_until_finished() {
    var game = game("AAA");
    assertFalse(game.isGameFinished());

    game = game.scorePoint(Point.from('A'));
    assertTrue(game.isGameFinished());

    // Scoring when finished should not change state
    var finishedGame = game.scorePoint(Point.from('B'));
    assertEquals(game.gameState(), finishedGame.gameState());
  }

  @Test
  @DisplayName("ScoringSystem handles custom scoring rules")
  void scoring_system_handles_custom_rules() {
    var customScoring = new ScoringSystem(2, 3, 1, 2);
    assertEquals(2, customScoring.minimumPointsForDeuce());
    assertEquals(3, customScoring.minimumPointsForWin());

    assertTrue(customScoring.isDeuce(2, 2));
    assertTrue(customScoring.hasAdvantage(3, 2));
    assertTrue(customScoring.hasWonGame(3, 1));
  }

  @Test
  @DisplayName("MatchNotation provides point stream")
  void match_notation_provides_point_stream() {
    var notation = MatchNotation.from("AB");
    var points = notation.points().toList();

    assertEquals(2, points.size());
    assertEquals(Point.from('A'), points.get(0));
    assertEquals(Point.from('B'), points.get(1));
  }

  /*
   * ╔═══════════════════════════════════════════════════════════════════════════════════════╗
   * ║                                  HELPER METHODS                                      ║
   * ╚═══════════════════════════════════════════════════════════════════════════════════════╝
   */

  private Game game(String notation) {
    Game game = Game.newGame();
    for (char c : notation.toCharArray()) {
      game = game.scorePoint(Point.from(c));
    }
    return game;
  }
}
