package com.ghiloufi.kata.display;

import com.ghiloufi.kata.domain.Player;
import com.ghiloufi.kata.domain.TennisGame;
import java.util.function.Function;

public class TerminalScoreRenderer implements Function<TennisGame, String> {

  @Override
  public String apply(TennisGame game) {
    return render(game);
  }

  private String render(TennisGame game) {
    return switch (game.getGameState()) {
      case DEUCE -> "Player A : 40 / Player B : 40 (Deuce)";
      case ADVANTAGE_A -> "Player A : 40 / Player B : 40 (Advantage Player A)";
      case ADVANTAGE_B -> "Player A : 40 / Player B : 40 (Advantage Player B)";
      case GAME_WON_A -> "Player A wins the game";
      case GAME_WON_B -> "Player B wins the game";
      case IN_PROGRESS -> formatRegularScore(game.getPlayerA(), game.getPlayerB());
    };
  }

  private String formatRegularScore(Player playerA, Player playerB) {
    return String.format(
        "Player A : %s / Player B : %s",
        playerA.score().toDisplayString(), playerB.score().toDisplayString());
  }
}
