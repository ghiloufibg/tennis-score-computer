package com.ghiloufi.kata.application.error;

public enum TechnicalError {
  NULL_SCOREBOARD_DISPLAY("ScoreboardDisplay cannot be null"),
  NULL_GAME_SEQUENCE("Game sequence cannot be null"),
  NULL_OUTPUT_CONSUMER("Output consumer cannot be null"),
  NULL_SCORE_RENDERER("Score renderer cannot be null"),
  NULL_GAME("Game cannot be null"),
  NO_MORE_ELEMENTS("No more points available");

  private final String messageTemplate;

  TechnicalError(String messageTemplate) {
    this.messageTemplate = messageTemplate;
  }

  public TechnicalException toException() {
    return new TechnicalException(this, messageTemplate);
  }
}
