package com.ghiloufi.kata.domain.state;

import static org.junit.jupiter.api.Assertions.*;

import com.ghiloufi.kata.domain.model.Player;
import com.ghiloufi.kata.domain.model.Point;
import com.ghiloufi.kata.domain.model.Score;
import com.ghiloufi.kata.domain.model.ScoringSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests de l'état AdvantageGameState")
class AdvantageGameStateTest {

  private ScoringSystem standardScoring;
  private Player playerA;
  private Player playerB;

  @BeforeEach
  void setUp() {
    standardScoring = ScoringSystem.STANDARD;
    playerA = new Player("A", Score.from(4));
    playerB = new Player("B", Score.from(3));
  }

  @Test
  @DisplayName("Devrait créer un état d'avantage pour le joueur A")
  void should_create_advantage_state_for_player_A() {
    AdvantageGameState advantageState = new AdvantageGameState(Player.A);

    assertEquals(Player.A, advantageState.getPlayerWithAdvantage());
    assertEquals("ADVANTAGE_A", advantageState.getDisplayName());
  }

  @Test
  @DisplayName("Devrait créer un état d'avantage pour le joueur B")
  void should_create_advantage_state_for_player_B() {
    AdvantageGameState advantageState = new AdvantageGameState(Player.B);

    assertEquals(Player.B, advantageState.getPlayerWithAdvantage());
    assertEquals("ADVANTAGE_B", advantageState.getDisplayName());
  }

  @Test
  @DisplayName("Ne devrait pas accepter de joueur null")
  void should_not_accept_null_player() {
    assertThrows(NullPointerException.class, () -> new AdvantageGameState(null));
  }

  @Test
  @DisplayName("Le jeu ne devrait pas être terminé")
  void game_should_not_be_finished() {
    AdvantageGameState advantageState = new AdvantageGameState(Player.A);
    assertFalse(advantageState.isGameFinished());
  }

  @Test
  @DisplayName("Devrait retourner null comme gagnant")
  void should_return_null_as_winner() {
    AdvantageGameState advantageState = new AdvantageGameState(Player.A);
    assertNull(advantageState.getWinner());
  }

  @Test
  @DisplayName("toString devrait retourner le nom d'affichage")
  void toString_should_return_display_name() {
    AdvantageGameState advantageA = new AdvantageGameState(Player.A);
    AdvantageGameState advantageB = new AdvantageGameState(Player.B);

    assertEquals("ADVANTAGE_A", advantageA.toString());
    assertEquals("ADVANTAGE_B", advantageB.toString());
  }

  @Test
  @DisplayName("Devrait transitionner vers GameWonState quand le joueur avec avantage marque")
  void should_transition_to_game_won_when_advantage_player_scores() {
    AdvantageGameState advantageA = new AdvantageGameState(Player.A);

    GameState nextState = advantageA.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertInstanceOf(GameWonState.class, nextState);
    GameWonState gameWonState = (GameWonState) nextState;
    assertEquals(Player.A, gameWonState.getWinner());
  }

  @Test
  @DisplayName("Devrait transitionner vers DeuceGameState quand l'adversaire marque")
  void should_transition_to_deuce_when_opponent_scores() {
    AdvantageGameState advantageA = new AdvantageGameState(Player.A);

    GameState nextState = advantageA.scorePoint(playerA, playerB, Point.PLAYER_B, standardScoring);

    assertSame(DeuceGameState.INSTANCE, nextState);
  }

  @Test
  @DisplayName("Joueur B avec avantage - Devrait transitionner vers GameWonState quand B marque")
  void player_B_advantage_should_transition_to_game_won_when_B_scores() {
    AdvantageGameState advantageB = new AdvantageGameState(Player.B);

    GameState nextState = advantageB.scorePoint(playerA, playerB, Point.PLAYER_B, standardScoring);

    assertInstanceOf(GameWonState.class, nextState);
    GameWonState gameWonState = (GameWonState) nextState;
    assertEquals(Player.B, gameWonState.getWinner());
  }

  @Test
  @DisplayName("Joueur B avec avantage - Devrait transitionner vers DeuceGameState quand A marque")
  void player_B_advantage_should_transition_to_deuce_when_A_scores() {
    AdvantageGameState advantageB = new AdvantageGameState(Player.B);

    GameState nextState = advantageB.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertSame(DeuceGameState.INSTANCE, nextState);
  }

  @Test
  @DisplayName("Devrait implémenter equals correctement")
  void should_implement_equals_correctly() {
    AdvantageGameState advantageA1 = new AdvantageGameState(Player.A);
    AdvantageGameState advantageA2 = new AdvantageGameState(Player.A);
    AdvantageGameState advantageB = new AdvantageGameState(Player.B);

    assertEquals(advantageA1, advantageA2);
    assertNotEquals(advantageA1, advantageB);
    assertNotEquals(advantageA1, null);
    assertNotEquals(advantageA1, "ADVANTAGE_A");
    assertEquals(advantageA1, advantageA1);
  }

  @Test
  @DisplayName("Devrait implémenter hashCode correctement")
  void should_implement_hashCode_correctly() {
    AdvantageGameState advantageA1 = new AdvantageGameState(Player.A);
    AdvantageGameState advantageA2 = new AdvantageGameState(Player.A);
    AdvantageGameState advantageB = new AdvantageGameState(Player.B);

    assertEquals(advantageA1.hashCode(), advantageA2.hashCode());
    assertNotEquals(advantageA1.hashCode(), advantageB.hashCode());
  }

  @Test
  @DisplayName("Devrait être immutable")
  void should_be_immutable() {
    AdvantageGameState advantageA = new AdvantageGameState(Player.A);
    Player originalPlayerA = new Player("A", Score.from(4));
    Player originalPlayerB = new Player("B", Score.from(3));

    GameState nextState =
        advantageA.scorePoint(originalPlayerA, originalPlayerB, Point.PLAYER_A, standardScoring);

    assertEquals(Player.A, advantageA.getPlayerWithAdvantage());
    assertInstanceOf(GameWonState.class, nextState);
    assertEquals(4, originalPlayerA.score().points());
    assertEquals(3, originalPlayerB.score().points());
  }

  @Test
  @DisplayName("Devrait ignorer le système de scoring (non utilisé)")
  void should_ignore_scoring_system() {
    AdvantageGameState advantageA = new AdvantageGameState(Player.A);
    ScoringSystem customScoring = new ScoringSystem(2, 3, 1, 2);

    GameState nextStateStandard =
        advantageA.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    GameState nextStateCustom =
        advantageA.scorePoint(playerA, playerB, Point.PLAYER_A, customScoring);

    assertInstanceOf(GameWonState.class, nextStateStandard);
    assertInstanceOf(GameWonState.class, nextStateCustom);

    GameWonState winStandard = (GameWonState) nextStateStandard;
    GameWonState winCustom = (GameWonState) nextStateCustom;
    assertEquals(winStandard.getWinner(), winCustom.getWinner());
  }

  @Test
  @DisplayName("Devrait ignorer les scores des joueurs (logique basée uniquement sur le point)")
  void should_ignore_player_scores() {
    AdvantageGameState advantageA = new AdvantageGameState(Player.A);

    Player playerA_LowScore = new Player("A", Score.from(1));
    Player playerB_HighScore = new Player("B", Score.from(10));

    GameState nextState =
        advantageA.scorePoint(playerA_LowScore, playerB_HighScore, Point.PLAYER_A, standardScoring);

    assertInstanceOf(GameWonState.class, nextState);
    GameWonState gameWonState = (GameWonState) nextState;
    assertEquals(Player.A, gameWonState.getWinner());
  }

  @Test
  @DisplayName("Devrait créer de nouvelles instances de GameWonState à chaque victoire")
  void should_create_new_game_won_instances() {
    AdvantageGameState advantageA = new AdvantageGameState(Player.A);

    GameState win1 = advantageA.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    GameState win2 = advantageA.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);

    assertInstanceOf(GameWonState.class, win1);
    assertInstanceOf(GameWonState.class, win2);
    assertNotSame(win1, win2);

    GameWonState gameWon1 = (GameWonState) win1;
    GameWonState gameWon2 = (GameWonState) win2;
    assertEquals(gameWon1.getWinner(), gameWon2.getWinner());
  }

  @Test
  @DisplayName("Devrait gérer les transitions multiples correctement")
  void should_handle_multiple_transitions_correctly() {
    AdvantageGameState advantageA = new AdvantageGameState(Player.A);

    GameState win = advantageA.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    assertInstanceOf(GameWonState.class, win);

    GameState deuce = advantageA.scorePoint(playerA, playerB, Point.PLAYER_B, standardScoring);
    assertSame(DeuceGameState.INSTANCE, deuce);

    AdvantageGameState advantageB = new AdvantageGameState(Player.B);

    GameState winB = advantageB.scorePoint(playerA, playerB, Point.PLAYER_B, standardScoring);
    assertInstanceOf(GameWonState.class, winB);
    assertEquals(Player.B, ((GameWonState) winB).getWinner());

    GameState deuceB = advantageB.scorePoint(playerA, playerB, Point.PLAYER_A, standardScoring);
    assertSame(DeuceGameState.INSTANCE, deuceB);
  }
}
