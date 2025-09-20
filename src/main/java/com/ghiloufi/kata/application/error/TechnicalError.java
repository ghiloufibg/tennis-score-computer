package com.ghiloufi.kata.application.error;

import com.ghiloufi.kata.common.error.ExceptionFactory;

public enum TechnicalError implements ExceptionFactory<TechnicalException> {
  NULL_SCOREBOARD_DISPLAY("ScoreboardDisplay cannot be null"),
  NULL_GAME_SEQUENCE("Game sequence cannot be null"),
  NULL_OUTPUT_CONSUMER("Output consumer cannot be null"),
  NULL_SCORE_RENDERER("Score renderer cannot be null"),
  NULL_GAME("Game cannot be null");

  private final String messageTemplate;

  TechnicalError(String messageTemplate) {
    this.messageTemplate = messageTemplate;
  }

  @Override
  public TechnicalException toException(Object... args) {
    String message = args.length > 0 ? String.format(messageTemplate, args) : messageTemplate;
    return new TechnicalException(this, message);
  }
}
