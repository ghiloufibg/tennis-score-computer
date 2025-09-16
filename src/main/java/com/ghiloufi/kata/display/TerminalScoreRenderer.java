package com.ghiloufi.kata.display;

import com.ghiloufi.kata.domain.Game;
import com.ghiloufi.kata.domain.Player;
import java.util.function.Function;

public class TerminalScoreRenderer implements Function<Game, String> {

  @Override
  public String apply(Game game) {
    return render(game);
  }

  private String render(Game game) {
    return switch (game.gameState()) {
      case DEUCE -> "Player A : 40 / Player B : 40 (Deuce)";
      case ADVANTAGE_A -> "Player A : 40 / Player B : 40 (Advantage Player A)";
      case ADVANTAGE_B -> "Player A : 40 / Player B : 40 (Advantage Player B)";
      case GAME_WON_A -> "Player A wins the game";
      case GAME_WON_B -> "Player B wins the game";
      case IN_PROGRESS -> formatRegularScore(game.playerA(), game.playerB());
    };
  }

  private String formatRegularScore(Player playerA, Player playerB) {
    return String.format(
        "Player A : %s / Player B : %s",
        convertPointsToTennisScore(playerA.getPoints()),
        convertPointsToTennisScore(playerB.getPoints()));
  }

  private String convertPointsToTennisScore(int points) {
    return switch (points) {
      case 0 -> "0";
      case 1 -> "15";
      case 2 -> "30";
      default -> "40";
    };
  }
}
