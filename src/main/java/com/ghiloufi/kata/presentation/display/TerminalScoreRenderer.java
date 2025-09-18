package com.ghiloufi.kata.presentation.display;

import static com.ghiloufi.kata.presentation.display.GameDisplayTemplates.*;

import com.ghiloufi.kata.domain.model.Game;
import com.ghiloufi.kata.domain.state.AdvantageGameState;
import com.ghiloufi.kata.domain.state.DeuceGameState;
import com.ghiloufi.kata.domain.state.GameWonState;
import java.util.function.Function;

public class TerminalScoreRenderer implements Function<Game, String> {

  @Override
  public String apply(Game game) {
    return switch (game.getGameState()) {
      case DeuceGameState __ -> DEUCE_DISPLAY;

      case AdvantageGameState a -> getAdvantageMessage(a.getPlayerWithAdvantage());

      case GameWonState g -> getGameWonMessage(g.getWinner());

      default ->
          formatScore(game.getPlayerA().score().toString(), game.getPlayerB().score().toString());
    };
  }
}
