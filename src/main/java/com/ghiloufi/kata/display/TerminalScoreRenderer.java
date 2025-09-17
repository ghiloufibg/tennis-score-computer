package com.ghiloufi.kata.display;

import static com.ghiloufi.kata.display.GameDisplayTemplates.*;

import com.ghiloufi.kata.domain.AdvantageGameState;
import com.ghiloufi.kata.domain.DeuceGameState;
import com.ghiloufi.kata.domain.GameWonState;
import com.ghiloufi.kata.domain.TennisGame;
import java.util.function.Function;

public class TerminalScoreRenderer implements Function<TennisGame, String> {

  @Override
  public String apply(TennisGame game) {
    return switch (game.getGameState()) {
      case DeuceGameState __ -> DEUCE_DISPLAY;

      case AdvantageGameState a -> getAdvantageMessage(a.getPlayerWithAdvantage());

      case GameWonState g -> getGameWonMessage(g.getWinner());

      default ->
          formatScore(
              game.getPlayerA().score().toString(),
              game.getPlayerB().score().toString());
    };
  }
}
