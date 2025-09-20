package com.ghiloufi.kata.presentation.display;

import static com.ghiloufi.kata.presentation.display.GameDisplayTemplate.*;

import com.ghiloufi.kata.domain.model.Game;
import com.ghiloufi.kata.domain.model.Player;
import java.util.function.Function;

public class TerminalScoreRenderer implements Function<Game, String> {

  @Override
  public String apply(Game game) {
    return switch (game.gameState()) {
      case DEUCE -> DEUCE.format();
      case ADVANTAGE_A -> getAdvantageMessage(Player.A);
      case ADVANTAGE_B -> getAdvantageMessage(Player.B);
      case WON_A -> getGameWonMessage(Player.A);
      case WON_B -> getGameWonMessage(Player.B);
      case PLAYING ->
          formatScore(game.playerA().score().toString(), game.playerB().score().toString());
    };
  }
}
